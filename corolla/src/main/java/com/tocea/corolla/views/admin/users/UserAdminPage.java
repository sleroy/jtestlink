package com.tocea.corolla.views.admin.users;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.model.Model;

import com.tocea.corolla.users.domain.User;
import com.tocea.corolla.views.LayoutPage;
import com.tocea.corolla.views.menu.AdminSideMenu;
import com.tocea.corolla.widgets.datatable.DataTableBuilder;
import com.tocea.corolla.widgets.datatable.columns.GravatarColumn;
import com.tocea.corolla.widgets.sidemenu.SideMenuPanel;

/**
 * User admin page
 *
 * @author Sylvain Leroy
 *
 */
public class UserAdminPage extends LayoutPage {
	public UserAdminPage() {
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
		final DataTableBuilder<User,String> dataTableBuilder = DataTableBuilder.newTable("userDataTable"); //$NON-NLS-1$
		dataTableBuilder.addColumn(new GravatarColumn(Model.of("")));
		dataTableBuilder.addColumn("First name", "firstName");
		dataTableBuilder.addColumn("Last name", "lastName");
		dataTableBuilder.addColumn("Login", "login");
		dataTableBuilder.addColumn("Email", "email");
		dataTableBuilder.addColumn("Created", "createdTime");
		dataTableBuilder.displayRows(30);
		dataTableBuilder.withListData(this.viewAPI.getUsers());
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