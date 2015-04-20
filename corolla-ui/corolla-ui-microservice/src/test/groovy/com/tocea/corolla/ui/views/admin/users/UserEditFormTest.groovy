/**
 *
 */
package com.tocea.corolla.ui.views.admin.users

import static org.junit.Assert.*

import org.apache.wicket.markup.html.form.DropDownChoice
import org.apache.wicket.mock.MockApplication
import org.apache.wicket.util.tester.WicketTester
import org.junit.Test
import org.mockito.Mockito
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer

import com.tocea.corolla.ui.rest.api.IRestAPI
import com.tocea.corolla.users.domain.Permission
import com.tocea.corolla.users.domain.Role

/**
 * @author sleroy
 *
 */
class UserEditFormTest {
	@Test
	public void testDropdown() {

		final List<Role> roles =  [
			newRole("a", "a", [Permission.GUI], false),
			newRole("b", "b", [Permission.GUI], false)
		]

		WicketTester tester = new WicketTester(new MockApplication())
		def page = new UserEditPage()
		page.restAPI = Mockito.mock(IRestAPI.class)

		Mockito.when(page.restAPI.getDefaultRole()).thenReturn roles[0]
		Mockito.when(page.restAPI.getRoles()).thenReturn roles
		Mockito.when(page.restAPI.getRole(Mockito.anyInt())).then(new Answer() {

					def answer(InvocationOnMock arg0) throws Throwable {
						return roles[arg0.getArguments()[0]]
					}
				})

		tester.startPage(page)
		//tester.debugComponentTrees()
		DropDownChoice<Integer> component = page.get("formEdit:roleChoice")
		assertNotNull(component)
		println component.getDefaultModelObject()


	}

	/**
	 * Creates a new role.
	 *
	 * @param _roleName
	 *            the role name
	 * @param _roleNote
	 *            the role note
	 * @return the new role
	 */
	public Role newRole(final String _roleName, final String _roleNote, List<String> _roles, boolean _defaultRole=false) {
		final Role role = new Role()
		role.with {
			id = ids++
			name = _roleName
			note = _roleNote
			permissions = _roles.join(", ")
			defaultRole = _defaultRole
		}
		return role
	}

	public static int ids = 0
}
