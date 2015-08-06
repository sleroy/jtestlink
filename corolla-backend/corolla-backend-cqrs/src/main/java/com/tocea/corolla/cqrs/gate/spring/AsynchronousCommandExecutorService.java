package com.tocea.corolla.cqrs.gate.spring;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.tocea.corolla.cqrs.annotations.CommandOptions;
import com.tocea.corolla.cqrs.gate.conf.CorollaCqrsConfiguration;

@Service
public class AsynchronousCommandExecutorService {

	private final class AsyncCommandCallable<R> implements Callable<R> {
		private final Object command;

		private AsyncCommandCallable(final Object _command) {
			command = _command;
		}

		@Override
		public R call() throws Exception {
			return sequentialCommandExecutorService.run(command);

		}
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(AsynchronousCommandExecutorService.class);

	@Autowired
	private CorollaCqrsConfiguration configuration;

	private ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private SequentialCommandExecutorService sequentialCommandExecutorService;

	public AsynchronousCommandExecutorService() {
		super();
	}

	public AsynchronousCommandExecutorService(final CorollaCqrsConfiguration _configuration) {
		configuration = _configuration;

	}

	@PostConstruct
	public void initAsyncEngine() {
		LOGGER.info("Initializing Asynchronous Command Engine");
		LOGGER.debug("Configuration is {}", configuration);
		taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(configuration.getCorePoolSize());
		taskExecutor.setMaxPoolSize(configuration.getMaxPoolSize());
		taskExecutor.setQueueCapacity(configuration.getQueueCapacity());
		taskExecutor.initialize();
	}

	@PreDestroy
	public void postDestroy() {
		taskExecutor.shutdown();
	}

	public <R> Future<R> submitCommand(final Object _command) {
		final AsyncCommandCallable<R> task = new AsyncCommandCallable<R>(_command);
		return taskExecutor.submit(task);

	}

	private CommandOptions getOptions(final Object _command) {
		if (!_command.getClass().isAnnotationPresent(CommandOptions.class)) {
			return null;
		}

		return _command.getClass().getAnnotation(CommandOptions.class);
	}

}
