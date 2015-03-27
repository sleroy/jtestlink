/**
 *
 */
package com.tocea.corolla.users.handlers

import static org.mockito.Mockito.*

import org.junit.Rule

import spock.lang.Specification

import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.users.commands.DeleteRoleCommand
import com.tocea.corolla.users.dao.IRoleDAO
import com.tocea.corolla.users.domain.Role
import com.tocea.corolla.utils.functests.FunctionalTestDoc

/**
 * @author sleroy
 *
 */
@FunctionalTestDoc(requirementName = "DELETE_ROLE")
class DeleteRoleCommandHandlerTest extends Specification{

	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()

	def IRoleDAO roleDao = Mock(IRoleDAO)

	def DeleteRoleCommandHandler	handler

	def Role validRole


	def setup() {
		handler = new DeleteRoleCommandHandler(
				roleDAO : roleDao
				)
		validRole = new Role(name:"admin", note:"Admin role", permissions:"",defaultRole:true )
	}


	/**
	 * Test method for
	 * {@link com.tocea.corolla.users.handlers.CreateRoleCommandHandler#handle(com.tocea.corolla.users.commands.CreateRoleCommand)}
	 * .
	 */
	def "test delete role existing role"() {
		given:
		roleDao.findRoleByName('admin') >> validRole
		when:
		final DeleteRoleCommand command = new DeleteRoleCommand(roleName : 'admin')

		this.handler.handle command

		then:
		1 * roleDao.delete(_)
	}

	/**
	 * Test method for
	 * {@link com.tocea.corolla.users.handlers.AddNewRoleCommandHandler#handle(com.tocea.corolla.users.commands.AddNewRoleCommand)}
	 * .
	 */
	def "test delete role with unknown role"() {
		given:
		roleDao.findRoleByName('admin') >> null
		when:
		final DeleteRoleCommand command = new DeleteRoleCommand(roleName : 'admin')

		this.handler.handle command

		then:
		0 * roleDao.delete(_)
	}
}
