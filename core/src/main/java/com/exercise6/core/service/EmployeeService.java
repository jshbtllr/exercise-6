package com.exercise6.core.service;
import com.exercise6.core.model.Roles;
import com.exercise6.core.model.Address;
import com.exercise6.core.model.ContactInfo;
import com.exercise6.core.model.Employee;
import com.exercise6.util.InputUtil;
import com.exercise6.core.dao.RoleDAO;
import com.exercise6.core.dao.EmployeeDAO;
import com.exercise6.core.service.EmployeeRoleService;
import com.exercise6.core.service.ContactInfoService;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import org.hibernate.SessionFactory;
import java.util.Collections;
import java.util.Comparator;

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
		String country;
		Date birthdate;
		Date hireDate = null;
		Float gradeWeightAverage;
		Boolean employed;
		Set <ContactInfo> contacts = new HashSet <ContactInfo>();
		Set <Roles> role = new HashSet <Roles>();
		Roles input = null;
		Long roleId = null;
		Integer option = null;

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
		System.out.print("Country: ");
		country = InputUtil.getRequiredInput();		
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

		Address address = new Address(streetNumber, barangay, city, country, zipcode);
		Employee employee = new Employee(lastName, firstName, middleName, suffix, title, address, birthdate, gradeWeightAverage, hireDate, employed, contacts, role);

		do{
			System.out.println("Employee Role Management");
			System.out.println("[1]    Add Roles");
			System.out.println("[2]    Exit");
			System.out.print("Choose an option: ");
			option = InputUtil.inputOptionCheck(2);
			if(option == 1) {
				role = EmployeeRoleService.addRoleSet(sessionFactory, role);
			}
		} while(option == 1);

		option = 0;

		do{
			System.out.println("Employee Contact Info Management");
			System.out.println("[1]    Add contacts");
			System.out.println("[2]    Exit");
			System.out.print("Input option: ");
			option = InputUtil.inputOptionCheck(2);		
			
			if(option!= 2){
				contacts = ContactInfoService.addContactSet(sessionFactory, contacts, employee);
				employee.setContactInfo(contacts);
			}
		} while(option!= 2);

		EmployeeDAO.addEmployee(sessionFactory, employee);
	}

	public static void listEmployees(SessionFactory sessionFactory, Integer sortFunction, Integer orderFunction) {
		List <Employee> list = EmployeeDAO.showEmployees(sessionFactory, sortFunction, orderFunction);
		if(!list.isEmpty()) {
			if(sortFunction == 2) {
				Collections.sort(list, new gwaComparator());

				if(orderFunction == 2) {
					Collections.sort(list, Collections.reverseOrder(new gwaComparator()));						
				}
			}

			for ( Employee employee : list ) {
				System.out.println("EmployeeID: " + employee.getId());
				System.out.println("FullName:   " + ((employee.getTitle().equals("") || employee.getTitle().equals(" ")) ? "" : employee.getTitle() + " ") 
									+ employee.getFirstName() + " " + employee.getMiddleName() + " " + employee.getLastName() + " " + employee.getSuffix());
				
				if(sortFunction != 4) {  	/*Sort Type 4 for employeeid and Fullname prints*/
					System.out.println("Address:    " + employee.getAddress().getStreetNumber() + " " + employee.getAddress().getBarangay() + " " 
										+ employee.getAddress().getCity() + " " + employee.getAddress().getCountry() + " " + employee.getAddress().getZipcode());
					System.out.println("Birthday:   " + employee.getBirthday());
					System.out.println("GWA:        " + employee.getGradeWeightAverage());
					if(employee.getEmployed().equals(true)) {
						System.out.println("Employed:   Yes");
						System.out.println("Hire Date:  " + employee.getHireDate());
					} else {
						System.out.println("Employed:   No");
						System.out.println("Hire Date:  " + "N/A");
					}
				}
				System.out.println("-------------------------------------------------------------------\n");
			}
		} else {
			System.out.println("No employees registered.");
		}	
	}	

	public static void deleteEmployee(SessionFactory sessionFactory) {
		System.out.println("Delete Employee");
		listEmployees(sessionFactory, 4, 1);
		System.out.print("Enter the Employee ID to be deleted: ");
		Long employeeId = InputUtil.inputOptionCheck().longValue();
		
		while (!(EmployeeDAO.employeeCheck(sessionFactory, employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			employeeId = InputUtil.inputOptionCheck().longValue();
		}

		EmployeeDAO.deleteEmployee(sessionFactory, employeeId);
	}

	public static void updateEmployee(SessionFactory sessionFactory) {
		String lastName = new String();
		String firstName = new String();
		String middleName = new String();
		String suffix = new String();
		String title = new String();
		String streetNumber = new String();
		String barangay = new String();
		String city = new String();
		String zipcode = new String();
		String country = new String();
		Date birthdate = null;
		Date hireDate = null;
		Float gradeWeightAverage = new Float(0.0f);
		Boolean employed = false;
		Set <ContactInfo> contacts = new HashSet <ContactInfo>();
		Set <Roles> role = new HashSet <Roles>();

		System.out.println("Update Employee");
		listEmployees(sessionFactory, 4, 0);
		System.out.print("Choose Employee ID to be updated: ");
		Long employeeId = InputUtil.inputOptionCheck().longValue();
		
		while (!(EmployeeDAO.employeeCheck(sessionFactory, employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a valid Employee ID: ");
			employeeId = InputUtil.inputOptionCheck().longValue();
		}	

		Employee employee = EmployeeDAO.getEmployee(sessionFactory, employeeId);

		System.out.println("Update Employee: ");
		System.out.println("[1]    Full Name");
		System.out.println("[2]    Address");
		System.out.println("[3]    Birthday");
		System.out.println("[4]    GWA");
		System.out.println("[5]    Employment Status");
		System.out.print("Choose information to edit: ");
		Integer option = InputUtil.inputOptionCheck(5);		

		if(option == 1) {
			System.out.print("Input New First Name: ");
			employee.setFirstName(InputUtil.getRequiredInput());
			System.out.print("Input New Last Name: ");
			employee.setLastName(InputUtil.getRequiredInput());
			System.out.print("Input New Middle Name: ");
			employee.setMiddleName(InputUtil.getRequiredInput());
			System.out.print("Input New Suffix Name (optional): ");
			employee.setSuffix(InputUtil.getOptionalInput());
			System.out.print("Input New Title Name (optional): ");
			employee.setTitle(InputUtil.getOptionalInput());
		} else if(option == 2) {
			System.out.print("Input New Street Number: ");
			streetNumber = InputUtil.getRequiredInput();
			System.out.print("Input New Barangay: ");
			barangay = InputUtil.getRequiredInput();
			System.out.print("Input New City: ");
			city = InputUtil.getRequiredInput();
			System.out.print("Input New Country: ");
			country = InputUtil.getRequiredInput();
			System.out.print("Input New Zipcode: ");
			zipcode = InputUtil.getRequiredInput();	
			employee.setAddress(new Address(streetNumber, barangay, city, country, zipcode));
		} else if (option == 3) {
			System.out.print("Input New Birthdate (dd/mm/yyyy): ");
			employee.setBirthday(InputUtil.getDate());
		} else if (option == 4) {
			System.out.print("Input new GWA: ");
			employee.setGradeWeightAverage(InputUtil.getGrade());
		} else {
			System.out.print("Input New Employee Status Y if person is employed, N if not: ");
			employed = InputUtil.getStatus();
			employee.setEmployed(employed);
			hireDate = null;
			if (employed == true) {
				System.out.print("Enter Employee Hire Date in format dd/mm/yyyy: ");
				employee.setHireDate(InputUtil.getDate());
			} else {
				try {
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					employee.setHireDate(format.parse("12/31/9999"));
				} catch(ParseException pe) {
					pe.printStackTrace();
				}
			}	
		}
		EmployeeDAO.updateEmployee(sessionFactory, employee);
	}
}

class gwaComparator implements Comparator <Employee> {
	public int compare(Employee a, Employee b) {
		return a.getGradeWeightAverage() < b.getGradeWeightAverage() ? -1 : a.getGradeWeightAverage() == b.getGradeWeightAverage() ? 0 : 1;
	}
}