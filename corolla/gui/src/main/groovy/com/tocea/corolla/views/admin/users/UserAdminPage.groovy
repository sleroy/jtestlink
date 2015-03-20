package com.tocea.corolla.views.admin.users

import org.apache.wicket.markup.head.IHeaderResponse
import org.apache.wicket.markup.html.link.AbstractLink
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.markup.html.link.StatelessLink
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import org.apache.wicket.request.mapper.parameter.PageParameters

import com.tocea.corolla.users.domain.User
import com.tocea.corolla.views.LayoutPage
import com.tocea.corolla.widget.links.factory.api.ILinkFactory
import com.tocea.corolla.widgets.datatable.DataTableBuilder
import com.tocea.corolla.widgets.datatable.columns.GravatarColumn
import com.tocea.corolla.widgets.panel.table.editdelete.EditDeleteColumn
import com.tocea.corolla.widgets.sidemenu.AdminSideMenu
import com.tocea.corolla.widgets.sidemenu.SideMenuPanel

/**
 * User admin page
 *
 * @author Sylvain Leroy
 *
 */
class UserAdminPage extends LayoutPage {


	def void renderHead(final IHeaderResponse response) {
		super.renderHead(response)
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.wicket.Page#onInitialize()
	 */
	def void onInitialize() {
		super.onInitialize()

		def editActionFactory = new ILinkFactory<User>() {

					public AbstractLink newLink(final String _widgetID, final IModel<User> _object) {

						final PageParameters parameters = new PageParameters()
						return new BookmarkablePageLink<>(_widgetID, UserEditPage.class, parameters)
					}
				}
		def deleteActionFactory = new ILinkFactory<User>() {

					public AbstractLink newLink(final String _widgetID, final IModel<User> _object) {

						return new StatelessLink(_widgetID) {


									@Override
									public void onClick() {

										viewAPI.deleteUser(_object)
									}


								}
					}
				}

		def dataTableBuilder = DataTableBuilder.newTable("userDataTable") //$NON-NLS-1$
		with dataTableBuilder {
			addColumn new GravatarColumn(Model.of(""))
			addColumn "First name", "firstName"
			addColumn "Last name", "lastName"
			addColumn "Login", "login"
			addColumn "Email", "email"
			addColumn "Created", "createdTime"
			addColumn new EditDeleteColumn<>("", editActionFactory, deleteActionFactory)
			displayRows 30
			withListData this.viewAPI.getUsers()
		}

		this.add(dataTableBuilder.build())



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