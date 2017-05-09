package com.exercise6.core.model;

public class Roles {
	
	private Long id;
	private String roleName;
	private String roleCode;

	public Roles() {}
	public Roles(String roleName, String roleCode) {
		this.roleName = roleName;
		this.roleCode = roleCode;
	}

	public Long getId() {
		return this.id;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setId(Long input) {
		this.id = input;
	}

	public void setRoleName(String input) {
		this.roleName = input;
	}

	public void setRoleCode(String input) {
		this.roleCode = input;
	}


}