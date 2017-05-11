package com.exercise6.core.service;
import com.exercise6.core.model.Roles;
import com.exercise6.core.model.Employee;
import com.exercise6.util.InputUtil;
import com.exercise6.core.dao.RoleDAO;
import org.hibernate.SessionFactory;
import java.util.List;

public class RoleService {
	public static void addRoles(SessionFactory sessionFactory, Roles role) {
		if (RoleDAO.checkDuplicateRole(sessionFactory, role, 1)) {
			System.out.println("RoleCode: " + role.getRoleCode() + " already exists in the database");
		} else {
			RoleDAO.addRole(sessionFactory, role);
			System.out.println("New Role has been added");
		}
	}

	public static void listRoles(SessionFactory sessionFactory, Integer sortRule, Integer orderRule) {
		List <Roles> list = RoleDAO.showRoles(sessionFactory, sortRule, orderRule);
		
		for (Roles role : list ) {
			System.out.println("RoleID:    " + role.getId());
			System.out.println("RoleCode:  " + role.getRoleCode());
			System.out.println("RoleName:  " + role.getRoleName());
			System.out.println("--------------------------------");
		}
	}	

	public static void updateRoles(SessionFactory sessionFactory) {
		String roleCode = new String();
		String roleName = new String();
		Roles role = new Roles();

		System.out.println("Update Roles");
		listRoles(sessionFactory, 1, 1);
		System.out.print("\nInput RoleID of the role to edit: ");
		Long roleId = InputUtil.inputOptionCheck().longValue();
		role.setId(roleId);

		while(!(RoleDAO.checkDuplicateRole(sessionFactory, role, 4))) {
			System.out.print("Specified RoleID does not exist. Input another: ");
			roleId = InputUtil.inputOptionCheck().longValue();
			role.setId(roleId);
		}		

		role = new Roles();

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
			if(RoleDAO.checkDuplicateRole(sessionFactory, role, 1)){
				System.out.println("Chosen RoleCode already exists. Exiting Role Update");
			} else {
				role = RoleDAO.getRoleDetails(sessionFactory, roleId);
				role.setRoleCode(roleCode);
				RoleDAO.updateRole(sessionFactory, role);
			}
		} else if(option == 2) {
			System.out.print("Input New RoleName: ");
			roleName = InputUtil.getRequiredInput();
			role = RoleDAO.getRoleDetails(sessionFactory, roleId);
			role.setRoleName(roleName);
			RoleDAO.updateRole(sessionFactory, role);
		} else if(option == 3) {
			System.out.print("Input New RoleCode: ");
			roleCode = InputUtil.getRequiredInput();
			System.out.print("Input New RoleName: ");
			roleName = InputUtil.getRequiredInput();

			role = new Roles(roleName, roleCode);
			if(RoleDAO.checkDuplicateRole(sessionFactory, role, 2)) {
				System.out.println("Chosen RoleCode already exists. Exiting Role Update");
			} else {
				role = RoleDAO.getRoleDetails(sessionFactory, roleId);
				role.setRoleCode(roleCode);
				role.setRoleName(roleName);
				RoleDAO.updateRole(sessionFactory, role);
			}
		} else {
			System.out.println("Invalid option chosen, exiting Update Role");
			return;
		}
	}		

	public static void removeRoles(SessionFactory sessionFactory) {
		listRoles(sessionFactory, 1, 1);
		System.out.print("\nInput RoleID of the role to be deleted: ");
		Long roleId = InputUtil.inputOptionCheck().longValue();
		Roles role = new Roles();
		role.setId(roleId);

		while(!(RoleDAO.checkDuplicateRole(sessionFactory, role, 4))) {
			System.out.print("Specified roleId does not exist. Input another: ");
			roleId = InputUtil.inputOptionCheck().longValue();
			role.setId(roleId);
		}

		if (!(RoleDAO.checkDuplicateRole(sessionFactory, role, 3))) {
			RoleDAO.deleteRole(sessionFactory, roleId);
			System.out.println("Role deleted");
		} else {
			System.out.println("Specified Role still belongs to an employee");
		}
	}
}	