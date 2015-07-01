package com.tocea.corolla.users.dto;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import com.tocea.corolla.users.domain.Role;

public class RoleDTO {
	
	private String id;
	private String name;
	private String note;
	private List<String> permissions;
	
	public RoleDTO() {
		this.permissions = Lists.newArrayList();
	}
	
	public RoleDTO(Role role) {
		this.id = role.getId();
		this.name = role.getName();
		this.note = role.getNote();
		this.permissions = Lists.newArrayList();
		this.permissions.addAll(Arrays.asList(role.getPermissions().split(", ")));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}
	
}