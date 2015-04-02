/**
 *
 */
package com.tocea.corolla.views.links

import org.apache.wicket.markup.html.link.StatelessLink

import com.tocea.corolla.views.admin.roles.RoleAdminPage

/**
 * @author sleroy
 *
 */
class RoleAdminLink extends StatelessLink {
	/**
	 * @param _id
	 */
	RoleAdminLink(final String _id) {
		super(_id)
	}

	def void onClick() {
		this.setResponsePage(RoleAdminPage.class)
	}
}