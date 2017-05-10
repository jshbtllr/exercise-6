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
import java.text.SimpleDateFormat;
import java.text.ParseException;
import org.hibernate.SessionFactory;

public class EmployeeRoleService {
	public static Set <Roles> addRoles(SessionFactory sessionFactory, Set <Roles> roles, Roles addRole) {
		Boolean exist = false;

		if(roles.isEmpty()) {
			roles.add(addRole);
		} else {
			for(Roles list : roles) {
				if(addRole.getId().equals(list.getId())) {
					exist = true;
				}
			}
			if(!exist) {
				roles.add(addRole);
			}
		}

		return roles;
	}

	public static void addEmployeeRoles(SessionFactory sessionFactory) {
		Integer rows = EmployeeDAO.showEmployees(sessionFactory, 4, 0);
		Employee employee = null;
		Set <Roles> roles = null;
		Roles input = null;
		Long employeeId;
		Long roleId;
		Boolean roleExist;

		System.out.print("Add role to which EmployeeId: ");
		employeeId = InputUtil.inputOptionCheck().longValue();

		while (!(EmployeeDAO.employeeCheck(sessionFactory, employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a valid Employee ID: ");
			employeeId = InputUtil.inputOptionCheck().longValue();
		}

		employee = EmployeeDAO.getEmployee(sessionFactory, employeeId);
		roles = employee.getRole();

		for(Roles list : roles) {
			System.out.println(list.getId() + list.getRoleName());
		}

		System.out.println("");
		Integer listRoles = RoleDAO.showRoles(sessionFactory, 1, 1);		

		System.out.print("Input the Role Id to add: ");
		roleId = InputUtil.inputOptionCheck().longValue();
		
		if(RoleDAO.checkId(sessionFactory, roleId)) {
			input = RoleDAO.getRole(sessionFactory, roleId);
			roles = addRoles(sessionFactory, roles, input);
			employee.setRole(roles);
			EmployeeDAO.updateEmployee(sessionFactory, employee);
			//EmployeeDAO.insertEmployeeRole(sessionFactory, roleId, employeeId);
		} else {
			System.out.println("Role does not exist. Role not added.");
		}

	} 

	public static void removeEmployeeRoles(SessionFactory sessionFactory) {
		Integer rows = EmployeeDAO.showEmployees(sessionFactory, 1, 1);
		Long employeeId;
		System.out.print("Delete the roles of which employee:");
		employeeId = InputUtil.inputOptionCheck().longValue();

		while (!(EmployeeDAO.employeeCheck(sessionFactory, employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			employeeId = InputUtil.inputOptionCheck().longValue();
		}

		Integer roleNumber = EmployeeDAO.showEmployeeRoles(sessionFactory, employeeId);

		System.out.print("Choose the RoleCode to Delete: ");
		String roleCode = InputUtil.getRequiredInput();
		Integer deleted = EmployeeDAO.deleteEmployeeRoles(sessionFactory, roleCode);
	} 

	public static void listEmployeeRoles(SessionFactory sessionFactory) {
		Integer rows = EmployeeDAO.showEmployees(sessionFactory, 1, 1);
		Long employeeId;
		System.out.print("Show the roles of which EmployeeId: ");
		employeeId = InputUtil.inputOptionCheck().longValue();

		while (!(EmployeeDAO.employeeCheck(sessionFactory, employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			employeeId = InputUtil.inputOptionCheck().longValue();
		}		

		Integer roleNumber = EmployeeDAO.showEmployeeRoles(sessionFactory, employeeId);

	}	
}