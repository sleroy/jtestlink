package com.tocea.corolla.users.service;

import java.util.List;

import com.tocea.corolla.users.dto.UserDto;
import com.tocea.corolla.users.dto.UserWithRoleDto;

public interface IUserDtoService {

	public List<UserDto> getUsersDtoList();
	
	public List<UserWithRoleDto> getUsersWithRoleList();
	
}
