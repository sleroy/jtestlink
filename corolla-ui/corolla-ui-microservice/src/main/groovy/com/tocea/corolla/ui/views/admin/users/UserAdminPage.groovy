package com.tocea.corolla.ui.views.admin.users

import org.apache.wicket.markup.html.link.AbstractLink
import org.apache.wicket.markup.html.link.StatelessLink
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import org.apache.wicket.request.mapper.parameter.PageParameters

import com.googlecode.wicket.kendo.ui.markup.html.link.BookmarkablePageLink
import com.tocea.corolla.ui.views.admin.central.AdminPage
import com.tocea.corolla.ui.widgets.datatable.DataTableBuilder
import com.tocea.corolla.ui.widgets.datatable.columns.GravatarColumn
import com.tocea.corolla.ui.widgets.links.factory.api.ILinkFactory
import com.tocea.corolla.ui.widgets.panel.table.editdelete.EditDeleteColumn
import com.tocea.corolla.users.domain.User

/**
 * User admin page
 *
 * @author Sylvain Leroy
 *
 */
class UserAdminPage extends AdminPage {



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
						parameters.add "user", _object.object.id
						return new BookmarkablePageLink<>(_widgetID, UserEditPage.class, parameters)
					}
				}


		def deleteActionFactory = new ILinkFactory<User>() {

					public AbstractLink newLink(final String _widgetID, final IModel<User> _object) {

						return new StatelessLink(_widgetID) {


									@Override
									public void onClick() {

										restAPI.deleteUser(_object)
									}
								}
					}
				}




		def dataTableBuilder = DataTableBuilder.newTable("userDataTable") //$NON-NLS-1$
		dataTableBuilder.with {
			addColumn new GravatarColumn(Model.of(""))
			addColumn "First name", "firstName"
			addColumn "Last name", "lastName"
			addColumn "Login", "login"
			addColumn "Email", "email"
			addColumn "Created", "createdTime"
			addColumn new EditDeleteColumn<>("", editActionFactory, deleteActionFactory)
			displayRows 30
			withListData this.restAPI.getUsers()
		}

		add dataTableBuilder.build()

		add new StatelessLink<String>("addButton") {
					void onClick() {
						setResponsePage(UserEditPage.class)
					}
				}



	}

}