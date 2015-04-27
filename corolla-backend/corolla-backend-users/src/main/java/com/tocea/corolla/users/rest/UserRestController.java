/**
 *
 */
package com.tocea.corolla.users.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tocea.corolla.users.dao.IRoleDAO;
import com.tocea.corolla.users.dao.IUserDAO;
import com.tocea.corolla.users.domain.Permission;
import com.tocea.corolla.users.domain.User;
import com.tocea.corolla.users.dto.UserDto;
import com.tocea.corolla.users.dto.UserWithRoleDto;
import com.tocea.corolla.utils.datatable.DataTableList;

/**
 * @author sleroy
 *
 */
@RestController()
@RequestMapping("/rest/users")
@Secured(Permission.REST)
public class UserRestController {

	@Autowired
	private IUserDAO	userDao;
	@Autowired
	private IRoleDAO	roleDao;

	@RequestMapping("/all")
	public DataTableList<UserDto> getAllUsers() {
		final DataTableList<UserDto> userTable = new DataTableList();
		for (final User user : this.userDao.findAll()) {
			userTable.add(new UserDto(user));
		}
		return userTable;
	}

	@RequestMapping("/allwithrole")
	public DataTableList<UserWithRoleDto> getAllUsersWithRole() {
		final DataTableList<UserWithRoleDto> userTable = new DataTableList();
		for (final User user : this.userDao.findAll()) {
			userTable.add(new UserWithRoleDto(user,
					this.roleDao.findOne(user.getRoleId())));
		}
		return userTable;
	}

}
