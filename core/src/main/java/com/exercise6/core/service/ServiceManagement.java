package com.exercise6.core.service;
import com.exercise6.core.model.Roles;
import com.exercise6.core.model.Address;
import com.exercise6.core.model.ContactInfo;
import com.exercise6.core.model.Employee;
import com.exercise6.util.InputUtil;
import com.exercise6.core.dao.RoleDAO;
import java.util.Scanner;
import java.util.List;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;

public class ServiceManagement {
	public static void createEmployee() {
		String lastName;
		String firstName;
		String middleName;
		String suffix;
		String title;
		String streetNumber;
		String barangay;
		String city;
		String zipcode;
		Date birthdate;
		Date hireDate;
		Float gradeWeightAverage;
		Boolean employed;
		Set <ContactInfo> contacts = new HashSet <ContactInfo>();
		Set <Roles> role = new HashSet <Roles>();

		System.out.print("Enter Employee Last Name: ");
		lastName = InputUtil.getRequiredInput();
		System.out.print("Enter Employee First Name: ");
		firstName = InputUtil.getRequiredInput();
		System.out.print("Enter Employee Middle Name: ");
		middleName = InputUtil.getRequiredInput();
		System.out.print("Enter Employee Suffix: (optional) ");
		suffix = InputUtil.getOptionalInput();
		System.out.print("Enter Employee Title: (optional) ");
		title = InputUtil.getOptionalInput();
		System.out.print("Enter Employee Street Number: ");
		streetNumber = InputUtil.getRequiredInput();
		System.out.print("Enter Employee Barangay: ");
		barangay = InputUtil.getRequiredInput();
		System.out.print("Enter Employee City: ");
		city = InputUtil.getRequiredInput();
		System.out.print("Enter Employee Zipcode: ");
		zipcode = InputUtil.getRequiredInput();
		System.out.print("Enter Employee Birthdate in forma dd/mm/yyyy: ");
		birthdate = InputUtil.getDate();
		System.out.print("Enter Employee Grade Weighted Average: ");
		gradeWeightAverage =  InputUtil.getGrade();
		System.out.print("Enter Employee Hire Date in forma dd/mm/yyyy: ");
		hireDate = InputUtil.getDate();
		System.out.print("Input Y if person is employed, N if not: ");
		employed = InputUtil.getStatus();

		Address address = new Address(streetNumber, barangay, city, zipcode);
		Employee employee = new Employee(lastName, firstName, middleName, suffix, title, address, birthdate, gradeWeightAverage, hireDate, employed, contacts, role);

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
		Integer match = RoleDAO.checkOccurrence(role, 1).intValue();

		if (match > 0) {
			System.out.println("RoleCode already exists in the database");
		} else {
			Integer rows = RoleDAO.addRole(role);
			System.out.println(rows + " row added to ROLES table");
		}
	}

	public static void removeRoles(Roles role) {
		Integer match = RoleDAO.checkOccurrence(role, 2).intValue();

		if (match == 0) {
			System.out.println("Specified Role does not exist in table ROLES");
		} else {
			match = RoleDAO.checkOccurrence(role, 3).intValue();
			if (match == 0) {
				Integer rows = RoleDAO.deleteRole(role);
				System.out.println(rows + " row deleted from table");
			} else {
				System.out.println("Specified Role still belongs to an employee");
			}
		}


	}

	public static Integer listRoles(Integer rule) {
		Integer rows = RoleDAO.showRoles(rule);

		return rows;
	}

	public static void updateRoles(Integer roleId, Integer option) {
		String roleCode = new String();
		String roleName = new String();
		Integer duplicate = null;
		Roles role;
		
		if(option == 1) {
			System.out.print("Input New RoleCode: ");
			roleCode = InputUtil.getRequiredInput();

			role = new Roles(roleCode, " ");
			duplicate = RoleDAO.checkOccurrence(role, 1).intValue();
		} else if(option == 2) {
			System.out.print("Input New RoleName: ");
			roleName = InputUtil.getRequiredInput();
		} else if(option == 3) {
			System.out.print("Input New RoleCode: ");
			roleCode = InputUtil.getRequiredInput();
			System.out.print("Input New RoleName: ");
			roleName = InputUtil.getRequiredInput();

			role = new Roles(roleCode, roleName);
			duplicate = RoleDAO.checkOccurrence(role, 2).intValue();
		} else {
			System.out.println("Invalid option chosen, exiting Update Role");
			return;
		}
		
		if(duplicate > 0) {
			System.out.println("Chosen RoleCode already exists. Exiting Role Update");
		} else {
			RoleDAO.updateRole(roleId, roleCode, roleName, option);
		}		
	}
}




