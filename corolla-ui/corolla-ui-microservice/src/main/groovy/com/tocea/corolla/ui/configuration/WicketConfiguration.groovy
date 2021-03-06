/**
 *
 */
package com.tocea.corolla.ui.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * This class defines the security configuration for Corolla;
 *
 * @author sleroy
 *
 */
@Component
@ConfigurationProperties(prefix = "corolla.wicket")
public class WicketConfiguration {

	def String mode = "development"


}
