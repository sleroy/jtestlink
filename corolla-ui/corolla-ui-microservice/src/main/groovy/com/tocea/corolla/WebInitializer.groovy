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

	private static final String	PARAM_APP_BEAN	= "applicationBean"

	private static final Logger	LOGGER			= LoggerFactory.getLogger(WebInitializer.class)

	@Bean
	public FilterRegistrationBean wicketFilter() {
		LOGGER.info(">---Web.xml Wicket configuration..")
		final WicketFilter wicketFilter = new WicketFilter()
		final FilterRegistrationBean filterConfig = new FilterRegistrationBean()
		filterConfig.with {
			setDispatcherTypes EnumSet.allOf(DispatcherType.class)
			setFilter wicketFilter

			addInitParameter PARAM_APP_BEAN, "wicketWebApplication"
			addInitParameter	WicketFilter.APP_FACT_PARAM, SpringWebApplicationFactory.class.getName()
			addInitParameter WicketFilter.FILTER_MAPPING_PARAM, "/*"

			LOGGER.info("<----Web.xml Wicket configuration..")
		}
		return filterConfig
	}
}
