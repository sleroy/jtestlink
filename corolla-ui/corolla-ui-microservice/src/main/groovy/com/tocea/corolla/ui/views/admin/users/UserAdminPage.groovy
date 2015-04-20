package com.tocea.corolla.ui.views.admin.users


import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.markup.html.AjaxLink
import org.apache.wicket.markup.html.link.AbstractLink
import org.apache.wicket.markup.html.link.StatelessLink
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import org.apache.wicket.request.mapper.parameter.PageParameters

import com.googlecode.wicket.kendo.ui.markup.html.link.BookmarkablePageLink
import com.tocea.corolla.ui.views.admin.central.AdminPage
import com.tocea.corolla.ui.widgets.datatable.DataTableBuilder
import com.tocea.corolla.ui.widgets.datatable.columns.GravatarColumn
import com.tocea.corolla.ui.widgets.links.factory.ClosureLinkFactory
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

	def void onInitialize() {
		super.onInitialize()

		def editActionFactory = new ClosureLinkFactory<User>( { widgetId, model ->

			final PageParameters parameters = new PageParameters()
			parameters.add "user", model.getObject().id
			new BookmarkablePageLink<>(widgetId, UserEditPage.class, parameters)
		})



		def final deleteDialog = new DialogDeleteAction<String>(this, "dialog",
				"Confirmation before deletion",
				"Are you sure to delete this user ?"
				) {
					void deletion(String user) {
						restAPI.deleteUserByLogin user
					}
				}

		def deleteActionFactory = new ILinkFactory<User>() {

					public AbstractLink newLink(final String _widgetID, final IModel<User> _object) {
						def deletion = new AjaxLink(_widgetID) {
									void onClick(AjaxRequestTarget arg0) {
										deleteDialog.doAction(arg0, _object.getObject().getLogin())
									}
								}
						if (_object.getObject().login == UserAdminPage.this.obtainSecurityDetails().getUsername()) {

							deletion.visible = false
						}
						return deletion
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
			withDataClosure() {
				this.restAPI.getUsers()
			}
		}

		add dataTableBuilder.build()

		add new StatelessLink<String>("addButton") {
					void onClick() {
						setResponsePage(UserEditPage.class)
					}
				}



	}

}