package com.exercise6.core.service;
import com.exercise6.core.model.Roles;
import com.exercise6.core.model.Address;
import com.exercise6.core.model.ContactInfo;
import com.exercise6.core.model.Employee;
import com.exercise6.util.InputUtil;
import com.exercise6.core.dao.EmployeeDAO;
import java.util.Scanner;
import java.util.List;

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

	public static void addRoles(Roles role) {
		Integer match = EmployeeDAO.checkOccurrence(role, 1).intValue();

		if (match > 0) {
			System.out.println("RoleCode already exists in the database");
		} else {
			Integer rows = EmployeeDAO.addRole(role);
			System.out.println(rows + " row added to ROLES table");
		}
	}

	public static void removeRoles(Roles role) {
		Integer match = EmployeeDAO.checkOccurrence(role, 2).intValue();

		if (match == 0) {
			System.out.println("Specified Role does not exist in table ROLES");
		} else {
			match = EmployeeDAO.checkOccurrence(role, 3).intValue();
			if (match == 0) {
				Integer rows = EmployeeDAO.deleteRole(role);
				System.out.println(rows + " row deleted from table");
			} else {
				System.out.println("Specified Role still belongs to an employee");
			}
		}


	}

	public static void listRoles(String rule) {
		Integer order = Integer.parseInt(rule);
		EmployeeDAO.showRoles(order);
	}

	public static void updateRoles() {
		System.out.println("Roles Employee");
	}
}