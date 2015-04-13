package com.tocea.corolla.ui.views.admin.central

import org.apache.wicket.markup.head.IHeaderResponse

import com.tocea.corolla.ui.views.links.RoleAdminLink
import com.tocea.corolla.ui.views.links.UserAdminLink

/**
 * Administration central page
 *
 * @author Sylvain Leroy
 *
 */
public class AdminCentralPage extends AdminPage {
	public AdminCentralPage() {
		super()

	}

	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response)

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.wicket.Page#onInitialize()
	 */
	@Override
	protected void onInitialize() {
		super.onInitialize()
		this.add(new UserAdminLink("userAdminLink"))
		this.add(new RoleAdminLink("roleAdminLink"))

	}

}