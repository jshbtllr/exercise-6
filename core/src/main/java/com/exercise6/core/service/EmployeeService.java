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
import org.hibernate.SessionFactory;

public class EmployeeService {
	public static void createEmployee(SessionFactory sessionFactory) {
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

		Integer rows = EmployeeDAO.addEmployee(sessionFactory, employee);
		System.out.println(rows + " Employee added");
	}	

	public static void deleteEmployee(SessionFactory sessionFactory) {
		System.out.println("Delete Employee");
		Integer rows = EmployeeDAO.showEmployees(sessionFactory, 1);
		System.out.print("Enter the Employee ID to be deleted: ");
		Integer employeeId = InputUtil.inputOptionCheck();
		
		while (!(EmployeeDAO.employeeCheck(sessionFactory, employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			employeeId = InputUtil.inputOptionCheck();
		}

		EmployeeDAO.employeeDelete(sessionFactory, employeeId);
	}

	public static void updateEmployee(SessionFactory sessionFactory) {
		System.out.println("Update Employee");
		Integer rows = EmployeeDAO.showEmployees(sessionFactory, 1);
		System.out.print("Choose Employee ID to be updated: ");
		Integer employeeId = InputUtil.inputOptionCheck();
		
		while (!(EmployeeDAO.employeeCheck(sessionFactory, employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			employeeId = InputUtil.inputOptionCheck();
		}	

		EmployeeDAO.employeeUpdate(sessionFactory, employeeId);
	}

	public static Integer listEmployees(SessionFactory sessionFactory, Integer ruleOrder) {
		Integer rows = EmployeeDAO.showEmployees(sessionFactory, ruleOrder);
		
		return rows;
	}
}