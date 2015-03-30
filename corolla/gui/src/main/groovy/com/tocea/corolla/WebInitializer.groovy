package com.tocea.corolla

import javax.servlet.DispatcherType

import org.apache.wicket.protocol.http.WicketFilter
import org.apache.wicket.spring.SpringWebApplicationFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.embedded.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order

/**
 * This class is the replacement of the web.xml. It registers the wicket filter
 * in the spring aware configuration style.
 *
 * @author Stefan Kloe
 *
 */
@Configuration
@Order(2)
class WebInitializer {

	private static final String PARAM_APP_BEAN = "applicationBean"

	private static final Logger LOGGER = LoggerFactory.getLogger(WebInitializer)

	@Bean
	public FilterRegistrationBean wicketFilter() {
		LOGGER.info ">---Web.xml Wicket configuration.."
		final FilterRegistrationBean filterConfig = new FilterRegistrationBean()
		filterConfig.setDispatcherTypes(EnumSet.allOf(DispatcherType.class))
		def wicketFilter = new WicketFilter()
		filterConfig.filter = wicketFilter
		def springWebFactory = new SpringWebApplicationFactory()
		wicketFilter.applicationFactory = springWebFactory
		filterConfig.addInitParameter PARAM_APP_BEAN, "wicketWebApplication"
		filterConfig.addInitParameter WicketFilter.APP_FACT_PARAM, SpringWebApplicationFactory.class.getName()
		filterConfig.addInitParameter WicketFilter.FILTER_MAPPING_PARAM, "/v/*"


		LOGGER.info "<----Web.xml Wicket configuration.."
		return filterConfig
	}
}
