package com.tocea.corolla.views

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession
import org.apache.wicket.markup.head.IHeaderResponse
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.form.PasswordTextField
import org.apache.wicket.markup.html.form.RequiredTextField
import org.apache.wicket.markup.html.form.StatelessForm
import org.apache.wicket.markup.html.panel.FeedbackPanel
import org.apache.wicket.model.CompoundPropertyModel

/**
 * Bootstrap page
 *
 * @author Sylvain Leroy
 *
 */
public class LoginPage extends BootstrapPage {
	public LoginPage() {
		final LoginForm form = new LoginForm("loginForm")
		add(form)

	}

	private static class LoginForm extends StatelessForm {

		private static final long serialVersionUID = -6826853507535977683L

		private String username
		private String password

		public LoginForm(String id) {
			super(id)
			setModel(new CompoundPropertyModel(this))
			add(new Label("usernameLabel", "Username"))
			add(new RequiredTextField("username"))
			add(new Label("passwordLabel", "Password"))
			add(new PasswordTextField("password"))
			add(new FeedbackPanel("feedback"))

		}

		@Override
		protected void onSubmit() {
			AuthenticatedWebSession session = AuthenticatedWebSession.get()
			if (session.signIn(username, password)) {
				setDefaultResponsePageIfNecessary()
			} else {
				error("Bad")
			}
		}

		private void setDefaultResponsePageIfNecessary() {
			if (!continueToOriginalDestination()) {
				setResponsePage(getApplication().getHomePage())
			}
		}
	}
	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response)
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.Page#onInitialize()
	 */
	@Override
	protected void onInitialize() {
		super.onInitialize()


	}

}