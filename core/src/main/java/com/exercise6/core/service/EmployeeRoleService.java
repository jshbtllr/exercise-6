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

public class EmployeeRoleService {
	public static void addEmployeeRoles(SessionFactory sessionFactory) {
		Integer rows = EmployeeDAO.showEmployees(sessionFactory, 1);
		Integer employeeId;
		String roleCode;
		Integer roleExist;

		System.out.print("Add role to which EmployeeId: ");
		employeeId = InputUtil.inputOptionCheck();

		System.out.println("");
		Integer listRoles = RoleDAO.showRoles(sessionFactory, 1);


		while (!(EmployeeDAO.employeeCheck(sessionFactory, employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			employeeId = InputUtil.inputOptionCheck();
		}

		System.out.print("Input the Role Code to add: ");
		roleCode = InputUtil.getRequiredInput();

		Roles added = new Roles(" ", roleCode);
		
		roleExist = RoleDAO.checkOccurrence(sessionFactory, added,1).intValue();

		if(roleExist > 0) {
			EmployeeDAO.insertEmployeeRole(sessionFactory, added, employeeId);
		} else {
			System.out.println("Role does not exist. Role not added.");
		}

	} 

	public static void removeEmployeeRoles(SessionFactory sessionFactory) {
		Integer rows = EmployeeDAO.showEmployees(sessionFactory, 1);
		Integer employeeId;
		System.out.print("Delete the roles of which employee:");
		employeeId = InputUtil.inputOptionCheck();

		while (!(EmployeeDAO.employeeCheck(sessionFactory, employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			employeeId = InputUtil.inputOptionCheck();
		}

		Integer roleNumber = EmployeeDAO.showEmployeeRoles(sessionFactory, employeeId);

		System.out.print("Choose the RoleCode to Delete: ");
		String roleCode = InputUtil.getRequiredInput();
		Integer deleted = EmployeeDAO.deleteEmployeeRoles(sessionFactory, roleCode);
	} 

	public static void listEmployeeRoles(SessionFactory sessionFactory) {
		Integer rows = EmployeeDAO.showEmployees(sessionFactory, 1);
		Integer employeeId;
		System.out.print("Show the roles of which EmployeeId: ");
		employeeId = InputUtil.inputOptionCheck();

		while (!(EmployeeDAO.employeeCheck(sessionFactory, employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			employeeId = InputUtil.inputOptionCheck();
		}		

		Integer roleNumber = EmployeeDAO.showEmployeeRoles(sessionFactory, employeeId);

	}	
}