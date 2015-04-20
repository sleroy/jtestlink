/**
 *
 */
package com.tocea.corolla.ui.views.admin.users

import groovy.util.logging.Slf4j

import org.apache.wicket.behavior.AttributeAppender
import org.apache.wicket.markup.html.form.DropDownChoice
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.form.PasswordTextField
import org.apache.wicket.markup.html.form.RequiredTextField
import org.apache.wicket.markup.html.form.TextField
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator
import org.apache.wicket.markup.html.link.StatelessLink
import org.apache.wicket.model.IModel
import org.apache.wicket.model.PropertyModel
import org.apache.wicket.validation.validator.EmailAddressValidator
import org.apache.wicket.validation.validator.StringValidator

import com.tocea.corolla.ui.rest.api.IRestAPI
import com.tocea.corolla.ui.widgets.listchoices.BooleanListChoice
import com.tocea.corolla.ui.widgets.validator.MaxSizeValidator
import com.tocea.corolla.users.domain.User


@Slf4j
class UserEditForm extends Form<User> {

	def IRestAPI restAPI

	public UserEditForm(String _wicketID, IRestAPI _restAPI , IModel<User> _userModel) {
		super(_wicketID, _userModel)
		restAPI = _restAPI
	}


	/* (non-Javadoc)
	 * @see org.apache.wicket.Component#isVersioned()
	 */
	@Override
	public boolean isVersioned() {

		return false
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.MarkupContainer#onInitialize()
	 */
	@Override
	protected void onInitialize() {
		super.onInitialize()

		def loginField = new TextField<>("login")
		loginField.add new StringValidator(1, 30)
		if (getDefaultModelObject().id == null) {
			loginField.add new LoginValidator(restAPI)
		} else {
			loginField.enabled  = false
			loginField.add new AttributeAppender("class", "disabled")
		}

		add loginField

		def fNameField = new TextField<>("firstName")
		fNameField.add new MaxSizeValidator(40)
		add fNameField

		def lNameField = new RequiredTextField<>("lastName")
		lNameField.add new MaxSizeValidator(40)
		add lNameField

		def emailField = new RequiredTextField<>("email")
		emailField.add EmailAddressValidator.getInstance()
		add emailField

		def passwordField = new PasswordTextField("password")
		add passwordField

		def passwordConfirmValidator  = new PasswordTextField("passwordConfirm", new PropertyModel<>(this, "confirmationPassword"))
		add passwordConfirmValidator

		add new EqualPasswordInputValidator(passwordField, passwordConfirmValidator)



		add new StatelessLink<String>("cancelButton") {
					void onClick() {
						setResponsePage(UserAdminPage.class)
					}
				}


		add new BooleanListChoice(
				"active",
				new PropertyModel<Boolean>(getDefaultModelObject(), "active")
				)

		add new DropDownChoice(
				"roleId"
				, new EntityListModel(restAPI.getRoles()), new RoleChoiceRender(restAPI)
				)

		add new DropDownChoice<String>("locale", [
			Locale.ENGLISH.toLanguageTag(),
			Locale.FRENCH.toLanguageTag()
		])

		//add new AjaxFormValidatingBehavior("onkeyup", Duration.ONE_SECOND)



		setOutputMarkupId true
	}

	protected void onSubmit() {
		try {
			def userObject = getDefaultModelObject()

			restAPI.saveUser userObject
			debug("User " + userObject.login + " saved !")
			setResponsePage(UserAdminPage.class)
		} catch(Exception e) {

			this.error(e.getMessage())
		}
	}

	private String confirmationPassword

	/* (non-Javadoc)
	 * @see org.apache.wicket.markup.html.form.Form#onError()
	 */
	@Override
	protected void onError() {
		getPage().get("feedback").setVisible true
	}
}
