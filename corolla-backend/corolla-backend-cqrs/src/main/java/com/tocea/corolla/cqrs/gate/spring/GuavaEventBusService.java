package com.tocea.corolla.cqrs.gate.spring;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.AsyncEventBus;
import com.tocea.corolla.cqrs.annotations.EventHandler;
import com.tocea.corolla.cqrs.gate.IEventBusService;
import com.tocea.corolla.cqrs.gate.conf.CorollaCqrsConfiguration;

@Service
public class GuavaEventBusService implements ApplicationContextAware, IEventBusService {
	
	private static final Logger		LOGGER	= LoggerFactory.getLogger(GuavaEventBusService.class);
	private AsyncEventBus			asyncEventBus;
	private ApplicationContext			applicationContext;
	private ThreadPoolTaskExecutor		threadPoolTaskExecutor;
	
	@Autowired
	private CorollaCqrsConfiguration	corollaCqrsConfiguation;
	
	@Autowired
	public void destroy() {
		asyncEventBus = null;
		threadPoolTaskExecutor.shutdown();
	}
	
	@Override
	public void dispatchEvent(final Object _event) {
		asyncEventBus.post(_event);
	}
	
	@Override
	public void setApplicationContext(final ApplicationContext _applicationContext) throws BeansException {
		applicationContext = _applicationContext;
		threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(corollaCqrsConfiguation.getCorePoolSize());
		threadPoolTaskExecutor.setMaxPoolSize(corollaCqrsConfiguation.getMaxPoolSize());
		threadPoolTaskExecutor.setQueueCapacity(corollaCqrsConfiguation.getQueueCapacity());
		threadPoolTaskExecutor.setKeepAliveSeconds(corollaCqrsConfiguation.getKeepAliveSeconds());
		threadPoolTaskExecutor.setThreadGroupName("corolla-event-bus");
		threadPoolTaskExecutor.setThreadNamePrefix("eventbus");
		threadPoolTaskExecutor.initialize();
		asyncEventBus = new AsyncEventBus(threadPoolTaskExecutor);
		
		final Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(EventHandler.class);
		for (final Entry<String, Object> beanEntry : beansWithAnnotation.entrySet()) {
			final String beanId = beanEntry.getKey();
			LOGGER.info("Registering an new event handler {}", beanId);
			final Object bean = beanEntry.getValue();
			asyncEventBus.register(bean);
			
		}

	}
	
}
