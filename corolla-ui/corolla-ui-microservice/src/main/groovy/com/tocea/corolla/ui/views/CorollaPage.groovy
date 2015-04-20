package com.tocea.corolla.ui.views

import groovy.transform.InheritConstructors

import com.tocea.corolla.ui.views.sidemenu.MainSideMenu
import com.tocea.corolla.ui.widgets.sidemenu.SideMenuPanel

/**
 * Layout page
 *
 * @author Sylvain Leroy
 *
 */
@InheritConstructors
//@AuthorizeInstantiation("GUI")
public abstract class CorollaPage extends LayoutPage {



	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.wicket.Page#onInitialize()
	 */
	@Override
	protected void onInitialize() {

		super.onInitialize()

	}

	/* (non-Javadoc)
	 * @see com.tocea.corolla.ui.views.LayoutPage#provideMenu()
	 */
	@Override
	protected SideMenuPanel useSideMenu() {
		return new MainSideMenu()

	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.Component#isVersioned()
	 */
	@Override
	public boolean isVersioned() {
		return false
	}
}
