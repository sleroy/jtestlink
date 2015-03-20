package com.tocea.corolla.views;

import org.apache.wicket.markup.head.IHeaderResponse;

import com.tocea.corolla.widgets.sidemenu.MainSideMenu;
import com.tocea.corolla.widgets.sidemenu.SideMenuPanel;

/**
 * Home page
 *
 * @author Sylvain Leroy
 *
 */
public class Homepage extends LayoutPage {
	public Homepage() {
		super();

	}

	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response);
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.Page#onInitialize()
	 */
	@Override
	protected void onInitialize() {
		super.onInitialize();


	}

	/* (non-Javadoc)
	 * @see com.tocea.corolla.views.LayoutPage#provideMenu()
	 */
	@Override
	protected SideMenuPanel useSideMenu() {
		return new MainSideMenu();
	}
}