package com.tocea.corolla.ui.views.admin.users

import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.panel.FeedbackPanel
import org.apache.wicket.model.CompoundPropertyModel
import org.apache.wicket.model.Model
import org.apache.wicket.model.PropertyModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.util.string.StringValue

import com.tocea.corolla.ui.views.admin.central.AdminPage
import com.tocea.corolla.ui.widgets.gravatar.GravatarDefaultImage
import com.tocea.corolla.ui.widgets.gravatar.GravatarImage
import com.tocea.corolla.users.domain.User

/**
 * User edit page
 *
 * @author Sylvain Leroy
 *
 */
class UserEditPage extends AdminPage  {

	public UserEditPage(PageParameters parameters) {
		super(parameters)
		def StringValue param = parameters.get "user"
		if (!param.isEmpty()) {
			def User user = this.restAPI.findUser(param.toInteger())
			setDefaultModel Model.of(user)
		}
		if (this.getDefaultModelObject() == null) {
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


		def userModel = new CompoundPropertyModel<User>(getDefaultModelObject())

		def form = new UserEditForm<User>("formEdit", restAPI, userModel)



		add new GravatarImage("gravatar", new PropertyModel<>(getDefaultModelObject(), "email"), GravatarDefaultImage.IDENTICON, 128)

		add new Label("userName", getDefaultModelObject().firstName + " "  + getDefaultModelObject().lastName)


		add(form)

		def feedback = new FeedbackPanel("feedback")
		feedback.outputMarkupId  = true
		feedback.visible = false
		add feedback
	}
}