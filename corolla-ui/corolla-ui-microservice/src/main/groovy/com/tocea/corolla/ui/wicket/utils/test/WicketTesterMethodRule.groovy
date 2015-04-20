
package com.tocea.corolla.ui.wicket.utils.test



import static org.mockito.Mockito.mock
import groovy.util.logging.Slf4j

import org.apache.wicket.spring.test.ApplicationContextMock
import org.apache.wicket.util.tester.WicketTester
import org.junit.Assert
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.springframework.security.authentication.AuthenticationManager

import com.tocea.corolla.ui.wicketapp.WicketWebApplication



/**
 * This class initializes the required objects to produce unit tests with
 * wicket. You will have to provide mock to enable it in your tests.
 *
 * @author sleroy
 */
@Slf4j
public class WicketTesterMethodRule implements TestRule {





	def static WicketWebApplication newWicketWebApplication(final ApplicationContextMock _mock) {


		final WicketWebApplication WicketWebApplication = new WicketWebApplication(_mock)
		return WicketWebApplication
	}



	def ApplicationContextMock applicationContextMock



	def WicketWebApplication      WicketWebApplicationTest



	/**
	 *
	 */
	public WicketTesterMethodRule() {


		this.applicationContextMock = new ApplicationContextMock()
		this.applicationContextMock.putBean("authenticationManager", mock(AuthenticationManager.class))

		this.applicationContextMock.putBean(mock(IWicketAdminService.class))
		this.wicketAdminService = this.applicationContextMock.getBean(IWicketAdminService.class)
	}


	@Override
	public Statement apply(final Statement _base, final Description _description) {


		return this.testerStatement(_base)
	}



	public WicketTester initContext() {


		final WicketWebApplication WicketWebApplication = newWicketWebApplication(this.applicationContextMock)
		final WicketTester tester = new WicketTester(WicketWebApplication)
		return tester
	}


	public WicketTester newWicketTester() {


		return new WicketTester(this.WicketWebApplicationTest)
	}





	/**
	 * @param _class
	 */
	public void testStart(final Class _class) {


		final WicketTester newWicketTester = this.newWicketTester()
		try {
			newWicketTester.startPage(_class)
			newWicketTester.assertNoErrorMessage()
		} finally {
			newWicketTester.destroy()
		}
	}


	private Statement testerStatement(final Statement base) {


		return new Statement() {


					public void evaluate() throws Throwable {


						this.before()
						base.evaluate()
						this.after()
					}


					private void after() {


						//
					}


					private void before() {


						try {

							this.renewWicketWebApplication()
						} catch (final Exception e) {
							log.error(e.getMessage(), e)
							Assert.fail(e.getMessage())
						}
					}


					private void renewWicketWebApplication() {


						WicketTesterMethodRule.this.WicketWebApplicationTest = newWicketWebApplication(WicketTesterMethodRule.this.applicationContextMock)
					}
				}
	}
}
