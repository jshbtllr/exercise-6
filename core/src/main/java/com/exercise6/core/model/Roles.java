package com.exercise6.model;

public class Roles {
	
	private Integer roleId;
	private String roleName;
	private String roleCode;

	public Roles() {}
	public Roles(String roleName, String roleCode) {
		this.roleName = roleName;
		this.roleCode = roleCode;
	}

	public Integer roleId() {
		return this.roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleId(Integer input) {
		this.roleId = input;
	}

	public void setRoleName(String input) {
		this.roleName = input;
	}

	public void setRoleCode(String input) {
		this.roleCode = input;
	}


}