/**
 *
 */
package com.tocea.corolla.widgets.links;

import org.apache.wicket.markup.html.link.StatelessLink;

import com.tocea.corolla.views.admin.roles.RoleAdminPage;

/**
 * @author sleroy
 *
 */
public final class RoleAdminLink extends StatelessLink {
	/**
	 * @param _id
	 */
	public RoleAdminLink(final String _id) {
		super(_id);
	}

	@Override
	public void onClick() {
		this.setResponsePage(RoleAdminPage.class);

	}
}