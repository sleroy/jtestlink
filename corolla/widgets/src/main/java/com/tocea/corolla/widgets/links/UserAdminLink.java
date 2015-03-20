/**
 *
 */
package com.tocea.corolla.widgets.links;

import org.apache.wicket.markup.html.link.StatelessLink;

import com.tocea.corolla.views.admin.users.UserAdminPage;

/**
 * @author sleroy
 *
 */
public final class UserAdminLink extends StatelessLink {
	/**
	 * @param _id
	 */
	public UserAdminLink(final String _id) {
		super(_id);
	}

	@Override
	public void onClick() {
		this.setResponsePage(UserAdminPage.class);

	}
}