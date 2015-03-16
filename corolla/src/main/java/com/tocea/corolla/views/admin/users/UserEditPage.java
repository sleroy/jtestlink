package com.tocea.corolla.views.admin.users;

import org.apache.wicket.markup.head.IHeaderResponse;

import com.tocea.corolla.views.LayoutPage;
import com.tocea.corolla.widgets.sidemenu.AdminSideMenu;
import com.tocea.corolla.widgets.sidemenu.SideMenuPanel;

/**
 * User edit page
 *
 * @author Sylvain Leroy
 *
 */
public class UserEditPage extends LayoutPage {
	public UserEditPage() {
		super();

	}

	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.wicket.Page#onInitialize()
	 */
	@Override
	protected void onInitialize() {
		super.onInitialize();

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.tocea.corolla.views.LayoutPage#provideMenu()
	 */
	@Override
	protected SideMenuPanel useSideMenu() {

		return new AdminSideMenu();
	}
}