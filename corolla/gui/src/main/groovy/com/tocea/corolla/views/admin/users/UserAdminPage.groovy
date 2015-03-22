package com.tocea.corolla.views.admin.users

import org.apache.wicket.markup.head.IHeaderResponse
import org.apache.wicket.markup.html.link.AbstractLink
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.markup.html.link.StatelessLink
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import org.apache.wicket.request.mapper.parameter.PageParameters

import com.tocea.corolla.users.domain.User
import com.tocea.corolla.views.admin.central.AbstractAdminPage
import com.tocea.corolla.widget.links.factory.api.ILinkFactory
import com.tocea.corolla.widgets.datatable.DataTableBuilder
import com.tocea.corolla.widgets.datatable.columns.GravatarColumn
import com.tocea.corolla.widgets.panel.table.editdelete.EditDeleteColumn

/**
 * User admin page
 *
 * @author Sylvain Leroy
 *
 */
class UserAdminPage extends AbstractAdminPage {



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

										viewAPI.deleteUser(_object)
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
			withListData this.viewAPI.getUsers()
		}

		this.add(dataTableBuilder.build())



	}

}