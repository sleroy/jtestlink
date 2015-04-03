package com.tocea.corolla.views.admin.users

import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.form.PasswordTextField
import org.apache.wicket.markup.html.form.StatelessForm
import org.apache.wicket.markup.html.form.TextField
import org.apache.wicket.model.CompoundPropertyModel
import org.apache.wicket.model.Model
import org.apache.wicket.model.PropertyModel
import org.apache.wicket.request.mapper.parameter.PageParameters

import com.tocea.corolla.users.domain.User
import com.tocea.corolla.views.admin.central.AbstractAdminPage
import com.tocea.corolla.widgets.gravatar.GravatarDefaultImage
import com.tocea.corolla.widgets.gravatar.GravatarImage

/**
 * User edit page
 *
 * @author Sylvain Leroy
 *
 */
public class UserEditPage extends AbstractAdminPage  {
	public UserEditPage(PageParameters parameters) {
		super()
		def param = parameters.get "user"
		if (param != null) {
			setDefaultModel Model.of(this.viewAPI.findUser(param.toInteger()))
		}
		if (this.getDefaultModel() == null) {
			setDefaultModel Model.of(new User())
		}
	}

	public UserEditPage() {
		this(new PageParameters())
	}



	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.wicket.Page#onInitialize()
	 */
	@Override
	protected void onInitialize() {
		super.onInitialize()

		def form = new StatelessForm<User>("formEdit", new CompoundPropertyModel<User>(getDefaultModel()))

		["login","firstName","lastName","email"].each {
			form.add new TextField<>(it)
		}
		form.add new PasswordTextField("password")
		form.add new PasswordTextField("passwordConfirm", new PropertyModel<>(this, "confirmationPassword"))

		add new GravatarImage("gravatar", new PropertyModel<>(getDefaultModelObject(), "email"), GravatarDefaultImage.IDENTICON, 128)

		add new Label("userName", getDefaultModelObject().firstName + " "  + getDefaultModelObject().lastName)


		add(form)
	}

	private String confirmationPassword

}