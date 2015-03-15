package com.tocea.corolla.views;

import org.apache.wicket.Page;
import org.apache.wicket.devutils.stateless.StatelessChecker;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.tocea.corolla.views.admin.central.AdminCentralPage;
import com.tocea.corolla.views.admin.users.UserAdminPage;

/**
 * The web application class also serves as spring boot starting point by using
 * spring boot's EnableAutoConfiguration annotation and providing the main
 * method.
 *
 * @author Stefan Kloe
 *
 */
@Component
public class WicketWebApplication extends WebApplication {

	private final static Logger logger = LoggerFactory
			.getLogger(WicketWebApplication.class);


	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * provides page for default request
	 */
	@Override
	public Class<? extends Page> getHomePage() {
		return Homepage.class;
	}

	@Override
	protected void init() {
		super.init();
		//https://github.com/sebfz1/wicket-jquery-ui
		//http://central.maven.org/maven2/com/googlecode/wicket-jquery-ui/
		this.getComponentInstantiationListeners().add(
		                                              new SpringComponentInjector(this, this.applicationContext));

		this.getComponentPreOnBeforeRenderListeners().add(new StatelessChecker());

		this.mountPage("/index.html", Homepage.class);
		this.mountPage("/admin", AdminCentralPage.class);
		this.mountPage("/admin/users", UserAdminPage.class);
		//this.mountPage("/admin/users", UserAdminPage.class);
		//this.mountPage("/mounted.html", MountedPage.class);
		// best place to do this is in Application#init()

	}

}
