package com.tocea.corolla.views.admin.roles;

import org.apache.wicket.markup.head.IHeaderResponse;

import com.tocea.corolla.users.domain.Role;
import com.tocea.corolla.views.LayoutPage;
import com.tocea.corolla.widgets.datatable.DataTableBuilder;
import com.tocea.corolla.widgets.sidemenu.AdminSideMenu;
import com.tocea.corolla.widgets.sidemenu.SideMenuPanel;

/**
 * Role admin page
 *
 * @author Sylvain Leroy
 *
 */
public class RoleAdminPage extends LayoutPage {
	public RoleAdminPage() {
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
		final DataTableBuilder<Role,String> dataTableBuilder = DataTableBuilder.newTable("roleDataTable"); //$NON-NLS-1$
		dataTableBuilder.addColumn("Role", "name");
		dataTableBuilder.addColumn("Description", "note");
		dataTableBuilder.displayRows(30);
		dataTableBuilder.withListData(this.viewAPI.getRoles());
		this.add(dataTableBuilder.build());

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