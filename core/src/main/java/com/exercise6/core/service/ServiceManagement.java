package com.exercise6.core.service;
import com.exercise6.core.model.Roles;
import com.exercise6.core.model.Address;
import com.exercise6.core.model.ContactInfo;
import com.exercise6.core.model.Employee;
import com.exercise6.util.InputUtil;
import com.exercise6.core.dao.RoleDAO;
import com.exercise6.core.dao.EmployeeDAO;
import com.exercise6.core.dao.ContactDAO;
import java.util.Scanner;
import java.util.List;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.text.SimpleDateFormat;
import java.text.ParseException;

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
		Date hireDate = null;
		Float gradeWeightAverage;
		Boolean employed;
		Set <ContactInfo> contacts = new HashSet <ContactInfo>();
		Set <Roles> role = new HashSet <Roles>();

		System.out.println("Creating a new Employee in database");
		System.out.print("Employee's Full Name Details:\nLast Name: ");
		lastName = InputUtil.getRequiredInput();
		System.out.print("First Name: ");
		firstName = InputUtil.getRequiredInput();
		System.out.print("Middle Name: ");
		middleName = InputUtil.getRequiredInput();
		System.out.print("Suffix: (optional) ");
		suffix = InputUtil.getOptionalInput();
		System.out.print("Title: (optional) ");
		title = InputUtil.getOptionalInput();
		System.out.print("Employee's Address:\nStreet Number: ");
		streetNumber = InputUtil.getRequiredInput();
		System.out.print("Barangay: ");
		barangay = InputUtil.getRequiredInput();
		System.out.print("City: ");
		city = InputUtil.getRequiredInput();
		System.out.print("Zipcode: ");
		zipcode = InputUtil.getRequiredInput();
		System.out.print("Employee's Birthdate (dd/mm/yyyy): ");
		birthdate = InputUtil.getDate();
		System.out.print("Employee's Grade Weighted Average: ");
		gradeWeightAverage =  InputUtil.getGrade();
		System.out.print("Employee Details: Y if employed, N if not: ");
		employed = InputUtil.getStatus();

		if (employed == true) {
			System.out.print("Enter Employee Hire Date in format dd/mm/yyyy: ");
			hireDate = InputUtil.getDate();
		} else {
			try {
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				hireDate = format.parse("12/31/9999");
			} catch(ParseException pe) {
				pe.printStackTrace();
			}
		}

		Address address = new Address(streetNumber, barangay, city, zipcode);
		Employee employee = new Employee(lastName, firstName, middleName, suffix, title, address, birthdate, gradeWeightAverage, hireDate, employed, contacts, role);

		Integer rows = EmployeeDAO.addEmployee(employee);
		System.out.println(rows + " Employee added");
	}

	public static void deleteEmployee() {
		System.out.println("Delete Employee");
		Integer rows = EmployeeDAO.showEmployees(1);
		System.out.print("Enter the Employee ID to be deleted: ");
		Integer employeeId = InputUtil.inputOptionCheck();
		
		while (!(EmployeeDAO.employeeCheck(employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			employeeId = InputUtil.inputOptionCheck();
		}

		EmployeeDAO.employeeDelete(employeeId);
	}

	public static void updateEmployee() {
		System.out.println("Update Employee");
		Integer rows = EmployeeDAO.showEmployees(1);
		System.out.print("Choose Employee ID to be updated: ");
		Integer employeeId = InputUtil.inputOptionCheck();
		
		while (!(EmployeeDAO.employeeCheck(employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			employeeId = InputUtil.inputOptionCheck();
		}	

		EmployeeDAO.employeeUpdate(employeeId);
	}

	public static Integer listEmployees(Integer ruleOrder) {
		Integer rows = EmployeeDAO.showEmployees(ruleOrder);
		
		return rows;
	}

	public static void addEmployeeRoles() {
		Integer rows = EmployeeDAO.showEmployees(1);
		Integer employeeId;
		String roleCode;
		Integer roleExist;

		System.out.print("Add role to which EmployeeId: ");
		employeeId = InputUtil.inputOptionCheck();

		while (!(EmployeeDAO.employeeCheck(employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			employeeId = InputUtil.inputOptionCheck();
		}

		System.out.print("Input the Role Code to add: ");
		roleCode = InputUtil.getRequiredInput();

		Roles added = new Roles(" ", roleCode);
		
		roleExist = RoleDAO.checkOccurrence(added,1).intValue();

		if(roleExist > 0) {
			EmployeeDAO.insertEmployeeRole(added, employeeId);
		} else {
			System.out.println("Role does not exist. Role not added.");
		}

	} 

	public static void removeEmployeeRoles() {
		Integer rows = EmployeeDAO.showEmployees(1);
		Integer employeeId;
		System.out.print("Delete the roles of which employee:");
		employeeId = InputUtil.inputOptionCheck();

		while (!(EmployeeDAO.employeeCheck(employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			employeeId = InputUtil.inputOptionCheck();
		}

		Integer roleNumber = EmployeeDAO.showEmployeeRoles(employeeId);

		System.out.print("Choose the RoleCode to Delete: ");
		String roleCode = InputUtil.getRequiredInput();
		Integer deleted = EmployeeDAO.deleteEmployeeRoles(roleCode);
	} 

	public static void listEmployeeRoles() {
		Integer rows = EmployeeDAO.showEmployees(1);
		Integer employeeId;
		System.out.print("Show the roles of which EmployeeId: ");
		employeeId = InputUtil.inputOptionCheck();

		while (!(EmployeeDAO.employeeCheck(employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			employeeId = InputUtil.inputOptionCheck();
		}		

		Integer roleNumber = EmployeeDAO.showEmployeeRoles(employeeId);

	}

	public static void addContactInfo() {
		Integer rows = EmployeeDAO.showEmployees(1);
		String infoType;

		System.out.print("Add a contact info to which employee: ");
		Integer emplId = InputUtil.inputOptionCheck();

		while (!(EmployeeDAO.employeeCheck(emplId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			emplId = InputUtil.inputOptionCheck();
		}

		System.out.println("Add Contact Information: ");
		System.out.println("[1]    Add email");
		System.out.println("[2]    Add phone");
		System.out.print("Input option: ");
		Integer option = InputUtil.inputOptionCheck(2);		

		System.out.print("Input Information Details: ");
		String infoDetail = InputUtil.getRequiredInput();

		if(option == 1) {
			infoType = "email";
		} else {
			infoType = "phone";
		}

		ContactInfo addInfo = new ContactInfo(infoType, infoDetail);		

		ContactDAO.addContact(addInfo, emplId);
	}

	public static void removeContactInfo() {
		Integer rows = EmployeeDAO.showEmployees(1);
		Integer employeeId;

		System.out.print("Delete a contact info from which employee: ");
		employeeId = InputUtil.inputOptionCheck();

		while (!(EmployeeDAO.employeeCheck(employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			employeeId = InputUtil.inputOptionCheck();
		}		

		Integer contacts = ContactDAO.employeeContact(employeeId);
		System.out.print("Choose the ContactID to be deleted: ");
		Integer contactID = InputUtil.inputOptionCheck();

		ContactDAO.deleteContact(contactID, employeeId);
	}

	public static void updateContactInfo() {
		Integer rows = EmployeeDAO.showEmployees(1);
		Integer employeeId;

		System.out.print("Update contact info of which employee: ");
		employeeId = InputUtil.inputOptionCheck();	

		while (!(EmployeeDAO.employeeCheck(employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			employeeId = InputUtil.inputOptionCheck();
		}		

		Integer contacts = ContactDAO.employeeContact(employeeId);
		System.out.print("Choose the ContactID to be updated: ");
		Integer contactID = InputUtil.inputOptionCheck();	

		ContactDAO.updateContact(contactID, employeeId);

	}

	public static void listContactInfo() {
		Integer rows = EmployeeDAO.showEmployees(1);
		Integer employeeId;
		System.out.print("Show Contact Information of which EmployeeId: ");
		employeeId = InputUtil.inputOptionCheck();

		while (!(EmployeeDAO.employeeCheck(employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			employeeId = InputUtil.inputOptionCheck();
		}				

		Integer roleNumber = ContactDAO.employeeContact(employeeId);
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

			role = new Roles(" ", roleCode);
			duplicate = RoleDAO.checkOccurrence(role, 1).intValue();
		} else if(option == 2) {
			System.out.print("Input New RoleName: ");
			roleName = InputUtil.getRequiredInput();
		} else if(option == 3) {
			System.out.print("Input New RoleCode: ");
			roleCode = InputUtil.getRequiredInput();
			System.out.print("Input New RoleName: ");
			roleName = InputUtil.getRequiredInput();

			role = new Roles(roleName, roleCode);
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