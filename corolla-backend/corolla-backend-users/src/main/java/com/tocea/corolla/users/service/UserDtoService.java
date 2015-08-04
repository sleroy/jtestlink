/**
 *
 */
package com.tocea.corolla.users.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tocea.corolla.users.dao.IRoleDAO;
import com.tocea.corolla.users.dao.IUserDAO;
import com.tocea.corolla.users.domain.User;
import com.tocea.corolla.users.dto.UserDto;
import com.tocea.corolla.users.dto.UserWithRoleDto;

/**
 * @author sleroy
 *
 */
@Service
public class UserDtoService implements IUserDtoService {

	@Autowired
	private IUserDAO	userDAO;

	@Autowired
	private IRoleDAO	roleDAO;

	public List<UserDto> getUsersDtoList() {
		final List<UserDto> userTable = new ArrayList<>();
		for (final User user : this.userDAO.findAll()) {
			userTable.add(new UserDto(user));
		}
		return userTable;
	}

	/**
	 * Return the user with role.
	 *
	 */
	public List<UserWithRoleDto> getUsersWithRoleList() {
		final List<UserWithRoleDto> userTable = new ArrayList<UserWithRoleDto>();
		for (final User user : this.userDAO.findAll()) {
			userTable.add(new UserWithRoleDto(user,
					this.roleDAO.findOne(user.getRoleId())));
		}
		return userTable;
	}
}
