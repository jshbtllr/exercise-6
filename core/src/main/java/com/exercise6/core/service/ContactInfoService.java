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

public class ContactInfoService {
	public static void addRemoveContactInfo(SessionFactory sessionFactory, Integer option) {
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

		contacts = addContactSet(sessionFactory, contacts);
		employee.setContactInfo(contacts);
		EmployeeDAO.updateEmployee(sessionFactory, employee);
	}


	public static Set <ContactInfo> addContactSet(SessionFactory sessionFactory, Set <ContactInfo> contacts) {	
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
			//contactId = EmployeeDAO.addContactDetail(sessionFactory, addInfo);
			addInfo.setId(contactId);
			contacts.add(addInfo);
		} else {
			for(ContactInfo list : contacts) {
				if(list.getInfoDetail().equals(addInfo.getInfoDetail())) {
					exist = true;
					System.out.println("Contact Info already added to employee");
				}
			}
			if(!exist) {
				//contactId = EmployeeDAO.addContactDetail(sessionFactory, addInfo);
				addInfo.setId(contactId);
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

		System.out.print("Delete a contact info from which employee: ");
		employeeId = InputUtil.inputOptionCheck().longValue();

		while (!(EmployeeDAO.employeeCheck(sessionFactory, employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			employeeId = InputUtil.inputOptionCheck().longValue();
		}		

		Integer contacts = ContactDAO.employeeContact(sessionFactory, employeeId);
		System.out.print("Choose the ContactID to be deleted: ");
		Integer contactID = InputUtil.inputOptionCheck();

		ContactDAO.deleteContact(sessionFactory, contactID, employeeId);
	}

	public static void updateContactInfo(SessionFactory sessionFactory) {
		EmployeeService.listEmployees(sessionFactory, 1, 1);
		Long employeeId;

		System.out.print("Update contact info of which employee: ");
		employeeId = InputUtil.inputOptionCheck().longValue();	

		while (!(EmployeeDAO.employeeCheck(sessionFactory, employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			employeeId = InputUtil.inputOptionCheck().longValue();
		}		

		Integer contacts = ContactDAO.employeeContact(sessionFactory, employeeId);
		System.out.print("Choose the ContactID to be updated: ");
		Integer contactID = InputUtil.inputOptionCheck();	

		ContactDAO.updateContact(sessionFactory, contactID, employeeId);

	}

	public static void listContactInfo(SessionFactory sessionFactory) {
		EmployeeService.listEmployees(sessionFactory, 1, 1);
		Long employeeId;
		System.out.print("Show Contact Information of which EmployeeId: ");
		employeeId = InputUtil.inputOptionCheck().longValue();

		while (!(EmployeeDAO.employeeCheck(sessionFactory, employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			employeeId = InputUtil.inputOptionCheck().longValue();
		}				

		Integer roleNumber = ContactDAO.employeeContact(sessionFactory, employeeId);
	}
}