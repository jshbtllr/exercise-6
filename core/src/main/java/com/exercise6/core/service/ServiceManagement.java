package com.exercise6.core.service;
import java.exercise6.core.model.Roles;
import java.exercise6.core.model.Address;
import java.exercise6.core.model.ContactInfo;
import java.exercise6.core.model.Employee;
import java.exercise6.util.InputUtil;
import java.util.Scanner;

public class ServiceManagement {
	public static void createEmployee() {
		System.out.println("Create Employee");

	}

	public static void deleteEmployee() {
		System.out.println("Delete Employee");
	}

	public static void updateEmployee() {
		System.out.println("update Employee");
	}

	public static void listEmployeeByLastName() {

	}

	public static void listEmployeeByGWA() {
		
	}	

	public static void listEmployeeByHireDate() {
		
	}	

	public static void addEmployeeRoles() {

	}

	public static void removeEmployeeRoles() {

	}

	public static void addContactInfo() {

	}

	public static void removeContactInfo() {

	}

	public static void updateContactInfo() {

	}

	public static void listContactInfo() {

	}

	public static void addRoles() {
		String roleName = new String();
		String roleCode = new String();
		System.out.println("Add Role Function");
		System.out.print("Provide RoleName: ");
		roleName = InputUtil.getUserInput();
		System.out.print("Provide RoleCode: ");
		roleCode = InputUtil.getUserInput();
		Roles role = new Roles(roleName, roleCode);
		Integer test = EmployeeDAO.addRole();
		System.out.println(test + " row inserted");
	}

	public static void removeRoles() {

	}

	public static void listRoles() {

	}

	public static void updateRoles() {
		System.out.println("Roles Employee");
	}
}