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
	public static void addContactInfo(SessionFactory sessionFactory) {
		Integer rows = EmployeeDAO.showEmployees(sessionFactory, 1, 1);
		String infoType;

		System.out.print("Add a contact info to which employee: ");
		Long emplId = InputUtil.inputOptionCheck().longValue();

		while (!(EmployeeDAO.employeeCheck(sessionFactory, emplId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			emplId = InputUtil.inputOptionCheck().longValue();
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

		ContactDAO.addContact(sessionFactory, addInfo, emplId);
	}

	public static void removeContactInfo(SessionFactory sessionFactory) {
		Integer rows = EmployeeDAO.showEmployees(sessionFactory, 1, 1);
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
		Integer rows = EmployeeDAO.showEmployees(sessionFactory, 1, 1);
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
		Integer rows = EmployeeDAO.showEmployees(sessionFactory, 1, 1);
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