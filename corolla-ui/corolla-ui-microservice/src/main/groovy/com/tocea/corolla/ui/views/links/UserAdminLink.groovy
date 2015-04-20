/**
 *
 */
package com.tocea.corolla.ui.views.links

import groovy.transform.InheritConstructors

import org.apache.wicket.markup.html.link.StatelessLink

import com.tocea.corolla.ui.views.admin.users.UserAdminPage

/**
 * @author sleroy
 *
 */
@InheritConstructors
public final class UserAdminLink extends StatelessLink {


	def void onClick() {
		this.setResponsePage(UserAdminPage.class)

	}
}