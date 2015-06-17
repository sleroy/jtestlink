package com.tocea.corolla.users.dto;

import com.tocea.corolla.users.domain.Role;

public class RoleDTO {
	
	private String id;
	private String name;
	private String note;
	
	public RoleDTO() {
		
	}
	
	public RoleDTO(Role role) {
		this.id = role.getId();
		this.name = role.getName();
		this.note = role.getNote();
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

}