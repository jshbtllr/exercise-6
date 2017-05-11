package com.exercise6.core.service;
import com.exercise6.core.model.Roles;
import com.exercise6.core.model.Address;
import com.exercise6.core.model.ContactInfo;
import com.exercise6.core.model.Employee;
import com.exercise6.util.InputUtil;
import com.exercise6.core.dao.RoleDAO;
import com.exercise6.core.dao.EmployeeDAO;
import java.util.Scanner;
import java.util.List;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import org.hibernate.SessionFactory;

public class ContactInfoService {
	public static void addContactInfo(SessionFactory sessionFactory) {
		String infoType = null;
		Employee employee = null;
		Set <ContactInfo> contacts;

		EmployeeService.listEmployees(sessionFactory, 4, 0);
		System.out.print("Add contact info to which employee: ");
		Long employeeId = InputUtil.inputOptionCheck().longValue();

		while (!(EmployeeDAO.employeeCheck(sessionFactory, employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			employeeId = InputUtil.inputOptionCheck().longValue();
		}	

		employee = EmployeeDAO.getEmployee(sessionFactory, employeeId);
		contacts = employee.getContactInfo();	

		contacts = addContactSet(sessionFactory, contacts, employee);
		employee.setContactInfo(contacts);
		EmployeeDAO.updateEmployee(sessionFactory, employee);
	}


	public static Set <ContactInfo> addContactSet(SessionFactory sessionFactory, Set <ContactInfo> contacts, Employee employee) {	
		Long contactId = null;
		Boolean exist = false;
		System.out.println("Add Contact Information: ");
		System.out.println("[1]    Add email");
		System.out.println("[2]    Add telephone");
		System.out.println("[3]    Add cellphone");
		System.out.print("Input option: ");
		
		Integer option = InputUtil.inputOptionCheck(3);		
		System.out.print("Input Information Details: ");
		String infoDetail = InputUtil.getRequiredInput();

		ContactInfo addInfo = checkInfo(infoDetail, option);

		if(contacts.isEmpty()) {
			addInfo.setId(contactId);
			addInfo.setParentEmployee(employee);
			contacts.add(addInfo); 			
		} else {
			for(ContactInfo list : contacts) {
				if(list.getInfoDetail().equals(addInfo.getInfoDetail())) {
					exist = true;
					System.out.println("Contact Info already added to employee");
				}
			}
			if(!exist) {
				addInfo.setId(contactId);
				addInfo.setParentEmployee(employee);
				contacts.add(addInfo);		
			}
		}

		return contacts;
	}

	public static ContactInfo checkInfo(String information, Integer option) {
		String infoType = null;
		if(option == 1) {
			infoType = "email";
			while((information.indexOf('@')) < 0) {
				System.out.print("Input is not a valid email. Enter a valid one: ");
				information = InputUtil.getRequiredInput();				
			}
		} else if(option == 2) {
			infoType = "telephone";
			while(!information.matches("^[1-9]{1}\\d{6}")) {
				System.out.print("Input is not a valid telephone. Enter a valid one: ");
				information = InputUtil.getRequiredInput();
			}
		} else {
			infoType = "cellphone";
			while(!information.matches("^09\\d{9}")) {
				System.out.print("Input is not a valid cellphone. Enter a valid one: ");
				information = InputUtil.getRequiredInput();
			}			
		}

		ContactInfo addInfo = new ContactInfo(infoType, information);	
		return addInfo;		
	}

	public static void removeContactInfo(SessionFactory sessionFactory) {
		EmployeeService.listEmployees(sessionFactory, 4, 0);
		Long employeeId;
		Long contactId;
		Employee employee;
		Boolean exist = false;
		ContactInfo deleteInfo = new ContactInfo(" ", " ");
		Set <ContactInfo> contacts = null;
		Iterator <ContactInfo> iterator = null;

		System.out.print("Delete a contact info from which employee: ");
		employeeId = InputUtil.inputOptionCheck().longValue();

		while (!(EmployeeDAO.employeeCheck(sessionFactory, employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			employeeId = InputUtil.inputOptionCheck().longValue();
		}

		employee = EmployeeDAO.getEmployee(sessionFactory, employeeId);
		contacts = employee.getContactInfo();

		System.out.print("Employee has ");

		if(!contacts.isEmpty()) {
			System.out.println("the below Contact Info: ");
			for(ContactInfo list : contacts) {
				System.out.println("Contact ID: " + list.getId());
				System.out.println("Contact Info Type: " + list.getInfoType());
				System.out.println("Contact Info: " + list.getInfoDetail());
				System.out.println("-------------------");
			}
			System.out.print("Input the Contact Id to remove: ");
			contactId = InputUtil.inputOptionCheck().longValue();
			deleteInfo.setId(contactId);

			iterator = contacts.iterator();
			while(iterator.hasNext()) {
				if(deleteInfo.getId().equals(iterator.next().getId())) {
					exist = true;
					EmployeeDAO.deleteContactInfo(sessionFactory, employeeId, contactId);	
					iterator.remove();
				}
			}

			if(!exist) {
				System.out.println("Contact ID not assigned to employee");
			}	
		} else {
			System.out.println("no Contact Info to delete");
		}

		employee.setContactInfo(contacts);
		EmployeeDAO.updateEmployee(sessionFactory, employee);
	}

	public static void updateContactInfo(SessionFactory sessionFactory) {
		EmployeeService.listEmployees(sessionFactory, 4, 0);
		Long employeeId;
		Long contactId;
		Employee employee;
		Boolean exist = false;
		ContactInfo updateInfo = new ContactInfo(" ", " ");
		ContactInfo newInfo = null;
		Set <ContactInfo> contacts = null;
		Iterator <ContactInfo> iterator = null;		

		EmployeeService.listEmployees(sessionFactory, 4, 0);
		System.out.print("Add contact info to which employee: ");
		employeeId = InputUtil.inputOptionCheck().longValue();

		while (!(EmployeeDAO.employeeCheck(sessionFactory, employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			employeeId = InputUtil.inputOptionCheck().longValue();
		}	

		employee = EmployeeDAO.getEmployee(sessionFactory, employeeId);
		contacts = employee.getContactInfo();

		System.out.print("Employee has ");

		if(!contacts.isEmpty()) {
			System.out.println("the below Contact Info: ");
			for(ContactInfo list : contacts) {
				System.out.println("Contact ID: " + list.getId());
				System.out.println("Contact Info Type: " + list.getInfoType());
				System.out.println("Contact Info: " + list.getInfoDetail());
				System.out.println("-------------------");
			}
			System.out.print("Input the Contact Id to update: ");
			contactId = InputUtil.inputOptionCheck().longValue();
			updateInfo.setId(contactId);

			for(ContactInfo list : contacts) {
				if(list.getId().equals(updateInfo.getId())) {
					Integer option = null;
					if (list.getInfoType().equals("email")) {
						option = 1;
					} else if (list.getInfoType().equals("telephone")) {
						option = 2;
					} else {
						option = 3;
					}

					exist = true;
					System.out.print("Input new contact detail: ");
					String infoDetail = InputUtil.getRequiredInput();
					newInfo = checkInfo(infoDetail, option);					
					list.setInfoDetail(newInfo.getInfoDetail());
				}
			}

			employee.setContactInfo(contacts);
			EmployeeDAO.updateEmployee(sessionFactory, employee);

			if(!exist) {
				System.out.println("Contact ID not assigned to employee");
			}

		} else {
			System.out.println("no contact information");
		}		
	}

	public static void listContactInfo(SessionFactory sessionFactory) {
		EmployeeService.listEmployees(sessionFactory, 4, 0);
		Long employeeId;
		Employee employee = null;
		Set <ContactInfo> contacts = null;
		System.out.print("Show Contact Information of which EmployeeId: ");
		employeeId = InputUtil.inputOptionCheck().longValue();

		while (!(EmployeeDAO.employeeCheck(sessionFactory, employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			employeeId = InputUtil.inputOptionCheck().longValue();
		}			

		employee = EmployeeDAO.getEmployee(sessionFactory, employeeId);	
		contacts = employee.getContactInfo();

		if(!contacts.isEmpty()) {
			System.out.println("Employee has below Contact Info: ");
			for(ContactInfo list : contacts) {
				System.out.println("Contact ID: " + list.getId());
				System.out.println("Contact Info Type: " + list.getInfoType());
				System.out.println("Contact Info: " + list.getInfoDetail());
				System.out.println("-------------------");
			}
		} else {
			System.out.println("Employee has no Contact Info");
		}
	}
}