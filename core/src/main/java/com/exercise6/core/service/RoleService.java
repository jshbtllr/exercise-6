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

public class RoleService {
	public static void addRoles(SessionFactory sessionFactory, Roles role) {
		Integer match = RoleDAO.checkOccurrence(sessionFactory, role, 1).intValue();

		if (match > 0) {
			System.out.println("RoleCode already exists in the database");
		} else {
			Integer rows = RoleDAO.addRole(sessionFactory, role);
			System.out.println(rows + " row added to ROLES table");
		}
	}

	public static void removeRoles(SessionFactory sessionFactory, Roles role) {
		Integer match = RoleDAO.checkOccurrence(sessionFactory, role, 2).intValue();

		if (match == 0) {
			System.out.println("Specified Role does not exist in table ROLES");
		} else {
			match = RoleDAO.checkOccurrence(sessionFactory, role, 3).intValue();
			if (match == 0) {
				Integer rows = RoleDAO.deleteRole(sessionFactory, role);
				System.out.println(rows + " row deleted from table");
			} else {
				System.out.println("Specified Role still belongs to an employee");
			}
		}
	}

	public static Integer listRoles(SessionFactory sessionFactory, Integer rule) {
		Integer rows = RoleDAO.showRoles(sessionFactory, rule);

		return rows;
	}

	public static void updateRoles(SessionFactory sessionFactory, Integer roleId, Integer option) {
		String roleCode = new String();
		String roleName = new String();
		Integer duplicate = null;
		Roles role;
		
		if(option == 1) {
			System.out.print("Input New RoleCode: ");
			roleCode = InputUtil.getRequiredInput();

			role = new Roles(" ", roleCode);
			duplicate = RoleDAO.checkOccurrence(sessionFactory, role, 1).intValue();
		} else if(option == 2) {
			System.out.print("Input New RoleName: ");
			roleName = InputUtil.getRequiredInput();
		} else if(option == 3) {
			System.out.print("Input New RoleCode: ");
			roleCode = InputUtil.getRequiredInput();
			System.out.print("Input New RoleName: ");
			roleName = InputUtil.getRequiredInput();

			role = new Roles(roleName, roleCode);
			duplicate = RoleDAO.checkOccurrence(sessionFactory, role, 2).intValue();
		} else {
			System.out.println("Invalid option chosen, exiting Update Role");
			return;
		}
		
		if(duplicate > 0) {
			System.out.println("Chosen RoleCode already exists. Exiting Role Update");
		} else {
			RoleDAO.updateRole(sessionFactory, roleId, roleCode, roleName, option);
		}		
	}
}	