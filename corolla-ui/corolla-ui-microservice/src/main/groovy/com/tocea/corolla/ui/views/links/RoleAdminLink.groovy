/**
 *
 */
package com.tocea.corolla.ui.views.links

import org.apache.wicket.markup.html.link.StatelessLink

import com.tocea.corolla.ui.views.admin.roles.RoleAdminPage

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