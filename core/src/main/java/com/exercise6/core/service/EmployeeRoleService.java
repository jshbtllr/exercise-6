package com.exercise6.core.service;
import com.exercise6.core.model.Roles;
import com.exercise6.core.model.Employee;
import com.exercise6.util.InputUtil;
import com.exercise6.core.dao.RoleDAO;
import com.exercise6.core.dao.EmployeeDAO;
import com.exercise6.core.service.RoleService;
import java.util.Set;
import java.util.Iterator;
import org.hibernate.SessionFactory;

public class EmployeeRoleService {
	public static void addRemoveEmployeeRoles(SessionFactory sessionFactory, Integer option) {			/*Option 1 add, Option 2 remove*/
		Employee employee = null;
		Long employeeId = null;
		Set <Roles> employeeRoles;

		EmployeeService.listEmployees(sessionFactory, 4, 0);
		if(option == 1) {
			System.out.print("Add role to which EmployeeId: ");
		} else {
			System.out.print("Remove role to which EmployeeId: ");
		}

		employeeId = InputUtil.inputOptionCheck().longValue();

		while (!(EmployeeDAO.employeeCheck(sessionFactory, employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a valid Employee ID: ");
			employeeId = InputUtil.inputOptionCheck().longValue();
		}

		employee = EmployeeDAO.getEmployee(sessionFactory, employeeId);
		employeeRoles = employee.getRole();	

		if(option == 1) {
			employeeRoles = addRoleSet(sessionFactory, employeeRoles);	
		} else {
			if(employeeRoles.isEmpty()) {
				System.out.println("No Roles to remove");
				return;
			}
			employeeRoles = removeRoleSet(sessionFactory, employeeRoles);
		}

		employee.setRole(employeeRoles);
		EmployeeDAO.updateEmployee(sessionFactory, employee);
	}

	public static Set <Roles> addRoleSet(SessionFactory sessionFactory, Set <Roles> roles) {
		Roles newRole = new Roles(" ", " ");
		Long roleId = null;
		Boolean exist = false;

		RoleService.listRoles(sessionFactory, 1, 1);
		System.out.print("Input the Role Id to add: ");
		roleId = InputUtil.inputOptionCheck().longValue();
		newRole.setId(roleId);

		if(!(RoleDAO.checkDuplicateRole(sessionFactory, newRole, 4))) {
			System.out.println("Role ID is not a valid Role ID. Role not added");
			return roles;
		}

		newRole = RoleDAO.getRoleDetails(sessionFactory, roleId);
		
		if(roles.isEmpty()) {
			roles.add(newRole);
		} else {
			for(Roles list : roles) {
				if(newRole.getId().equals(list.getId())) {
					exist = true;
					System.out.println("Role is already added to employee");
				}
			}
			if(!exist) {
				roles.add(newRole);
			}
		}
		return roles;
	}

	public static Set <Roles> removeRoleSet(SessionFactory sessionFactory, Set <Roles> roles) {
		Roles deleteRole = new Roles(" ", " ");
		Long roleId = null;
		Boolean exist = false;
		Iterator <Roles> iterator = null;
			
		System.out.println("Available Roles for Employee: ");
		for (Roles list : roles) {
			System.out.println("Role ID: " + list.getId());	
			System.out.println("Role Code: " + list.getRoleCode());
			System.out.println("Role Name: " + list.getRoleName());
			System.out.println("---------------");
		}

		System.out.print("Input the Role Id to remove: ");
		roleId = InputUtil.inputOptionCheck().longValue();
		deleteRole.setId(roleId);

		if(!(RoleDAO.checkDuplicateRole(sessionFactory, deleteRole, 4))) {
			System.out.println("Role ID is not a valid Role ID. Role not added");
			return roles;
		}
		deleteRole = RoleDAO.getRoleDetails(sessionFactory, roleId);
		
		iterator = roles.iterator();
		while(iterator.hasNext()) {
			if(deleteRole.getId().equals(iterator.next().getId())) {
				exist = true;
				iterator.remove();
			}
		}		


		if(!exist) {
			System.out.println("Role not assigned to employee");
		}

		return roles;
	}	

	public static void listEmployeeRoles(SessionFactory sessionFactory) {
		EmployeeService.listEmployees(sessionFactory, 4, 0);
		Long employeeId;
		Employee employee = null;
		Set <Roles> roles = null;

		System.out.print("Show the roles of which EmployeeId: ");
		employeeId = InputUtil.inputOptionCheck().longValue();

		while (!(EmployeeDAO.employeeCheck(sessionFactory, employeeId))) {
			System.out.print("Employee ID chosen does not exist. Enter a new employee id to delete: ");
			employeeId = InputUtil.inputOptionCheck().longValue();
		}		

		employee = EmployeeDAO.getEmployee(sessionFactory, employeeId);
		roles = employee.getRole();

		System.out.println("Employee has: ");

		if(roles.isEmpty()) {
			System.out.println("No available roles");
		} else {
			System.out.println("the below rolecode and rolename as roles");
			for (Roles list : roles) {
				System.out.println("Role Code: " + list.getRoleCode());
				System.out.println("Role Name: " + list.getRoleName());
				System.out.println("---------------");
			}
		}
	}	
}