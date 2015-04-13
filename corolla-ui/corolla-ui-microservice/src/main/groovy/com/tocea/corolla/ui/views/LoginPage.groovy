package com.tocea.corolla.ui.views

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
public class LoginPage extends LayoutPage {
	public LoginPage() {
		final LoginForm form = new LoginForm("loginForm")
		add(form)

	}

	private static class LoginForm extends StatelessForm {

		private static final long serialVersionUID = -6826853507535977683L

		private String username
		private String password

		public LoginForm(final String id) {
			super(id)
			this.setModel(new CompoundPropertyModel(this))
			this.add(new Label("usernameLabel", "Username"))
			this.add(new RequiredTextField("username"))
			this.add(new Label("passwordLabel", "Password"))
			this.add(new PasswordTextField("password"))
			this.add(new FeedbackPanel("feedback"))

		}

		@Override
		protected void onSubmit() {
			final AuthenticatedWebSession session = AuthenticatedWebSession.get()
			if (session.signIn(this.username, this.password)) {
				this.setDefaultResponsePageIfNecessary()
			} else {
				this.error("Bad")
			}
		}

		private void setDefaultResponsePageIfNecessary() {
			if (!this.continueToOriginalDestination()) {
				this.setResponsePage(this.getApplication().getHomePage())
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
