/**
 *
 */
package com.tocea.corolla.views.links

import org.apache.wicket.markup.html.link.StatelessLink

import com.tocea.corolla.views.HomePage

/**
 * @author sleroy
 *
 */
class HomePageLink extends StatelessLink {
	/**
	 * @param _id
	 */
	HomePageLink(final String _id) {
		super(_id)
	}

	def void onClick() {
		this.setResponsePage(HomePage.class)
	}
}