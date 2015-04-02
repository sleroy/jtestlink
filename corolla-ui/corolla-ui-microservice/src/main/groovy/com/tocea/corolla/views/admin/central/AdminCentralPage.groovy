package com.tocea.corolla.views.admin.central

import org.apache.wicket.markup.head.IHeaderResponse

import com.tocea.corolla.views.LayoutPage
import com.tocea.corolla.views.links.RoleAdminLink
import com.tocea.corolla.views.links.UserAdminLink
import com.tocea.corolla.views.sidemenu.AdminSideMenu
import com.tocea.corolla.widgets.sidemenu.SideMenuPanel

/**
 * Administration central page
 *
 * @author Sylvain Leroy
 *
 */
public class AdminCentralPage extends LayoutPage {
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

	/*
	 * (non-Javadoc)
	 *
	 * @see com.tocea.corolla.views.LayoutPage#provideMenu()
	 */
	@Override
	protected SideMenuPanel useSideMenu() {

		return new AdminSideMenu()
	}
}