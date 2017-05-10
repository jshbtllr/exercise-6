package com.exercise6.core.service;
import com.exercise6.core.model.Roles;
import com.exercise6.core.model.Employee;
import com.exercise6.util.InputUtil;
import com.exercise6.core.dao.RoleDAO;
import org.hibernate.SessionFactory;

public class RoleService {
	public static void addRoles(SessionFactory sessionFactory, Roles role) {
		if (RoleDAO.checkOccurrence(sessionFactory, role, 1)) {
			System.out.println("RoleCode already exists in the database");
		} else {
			Integer rows = RoleDAO.addRole(sessionFactory, role);
			System.out.println(rows + " row added to ROLES table");
		}
	}

	public static void removeRoles(SessionFactory sessionFactory) {
		Integer rows = RoleDAO.showRoles(sessionFactory, 1, 1);
		System.out.print("\nInput RoleID of the role to be deleted: ");
		Long roleId = InputUtil.inputOptionCheck().longValue();

		while(!(RoleDAO.checkId(sessionFactory, roleId))) {
			System.out.print("Specified roleId does not exist. Input another: ");
			roleId = InputUtil.inputOptionCheck().longValue();
		}

		Roles role = new Roles();		
		role.setId(roleId);

		if (!(RoleDAO.checkOccurrence(sessionFactory, role, 3))) {
			rows = RoleDAO.deleteRole(sessionFactory, roleId);
			System.out.println(rows + " row deleted from table");
		} else {
			System.out.println("Specified Role still belongs to an employee");
		}
	}

	public static Integer listRoles(SessionFactory sessionFactory, Integer sortRule, Integer orderRule) {
		Integer rows = RoleDAO.showRoles(sessionFactory, sortRule, orderRule);

		return rows;
	}

	public static void updateRoles(SessionFactory sessionFactory) {
		String roleCode = new String();
		String roleName = new String();
		Boolean occurrence = false;
		Roles role = null;

		System.out.println("Update Roles");
		Integer rows = RoleDAO.showRoles(sessionFactory, 1, 1);
		System.out.print("\nInput RoleID of the role to edit: ");
		Long roleId = InputUtil.inputOptionCheck().longValue();

		while(!(RoleDAO.checkId(sessionFactory, roleId))) {
			System.out.print("Specified RoleID does not exist. Input another: ");
			roleId = InputUtil.inputOptionCheck().longValue();
		}		

		System.out.println("\nEdit: ");
		System.out.println("[1]    RoleCode");
   		System.out.println("[2]    RoleName");
		System.out.println("[3]    Both");
		System.out.print("Choose Option above: ");
		Integer option = InputUtil.inputOptionCheck(3);		
		
		if(option == 1) {
			System.out.print("Input New RoleCode: ");
			roleCode = InputUtil.getRequiredInput();

			role = new Roles(" ", roleCode);
			occurrence = RoleDAO.checkOccurrence(sessionFactory, role, 1);
		} else if(option == 2) {
			System.out.print("Input New RoleName: ");
			roleName = InputUtil.getRequiredInput();

			role = new Roles(roleName, " ");
		} else if(option == 3) {
			System.out.print("Input New RoleCode: ");
			roleCode = InputUtil.getRequiredInput();
			System.out.print("Input New RoleName: ");
			roleName = InputUtil.getRequiredInput();

			role = new Roles(roleName, roleCode);
			occurrence = RoleDAO.checkOccurrence(sessionFactory, role, 2);
		} else {
			System.out.println("Invalid option chosen, exiting Update Role");
			return;
		}
		
		if(occurrence) {
			System.out.println("Chosen RoleCode already exists. Exiting Role Update");
		} else {
			RoleDAO.updateRole(sessionFactory, roleId, role, option);
		}		
	}
}	