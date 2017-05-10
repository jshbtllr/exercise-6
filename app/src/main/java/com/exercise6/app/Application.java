package com.exercise6.app;
import java.util.Scanner;
import com.exercise6.util.InputUtil;
import com.exercise6.core.service.EmployeeService;
import com.exercise6.core.service.RoleService;
import com.exercise6.core.service.ContactInfoService;
import com.exercise6.core.service.EmployeeRoleService;
import com.exercise6.core.model.Roles;
import com.exercise6.util.HibernateUtil;
import org.hibernate.SessionFactory;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Application {
	public static void main (String [] args) {
		Boolean exit = false;
		Boolean subFunctionFlag = false;
		String function = new String();
		Integer rows;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		while(!exit) {
			System.out.println("\nEmployee Database");
			System.out.println("Menu");
			System.out.println(" [1]    Create Employee");
			System.out.println(" [2]    Delete Employee");
			System.out.println(" [3]    Update Employee Details");
			System.out.println(" [4]    List all Employees");
			System.out.println(" [5]    Manage Employee Roles");
			System.out.println(" [6]    Manage Employee Contacts");
			System.out.println(" [7]    Role Management");
			System.out.println(" [8]    Exit Tool");
			System.out.print("\nChoose corresponding number to choose a function: ");

			function = InputUtil.getRequiredInput();

			switch (function) {
				case "1":
					EmployeeService.createEmployee(sessionFactory);
					break;
				case "2":
					EmployeeService.deleteEmployee(sessionFactory);
					break;
				case "3":
					EmployeeService.updateEmployee(sessionFactory);
					break;
				case "4":
					Integer sortFunction;
					Integer orderFunction;

					System.out.println("\nEmployee List sorted by: ");
					System.out.println("[1]    Last Name");
					System.out.println("[2]    General Weighted Average");
					System.out.println("[3]    Date Hired");
					System.out.print("Input Sort Type: ");

					sortFunction = InputUtil.inputOptionCheck(3);
					System.out.println("\nOrder: ");
					System.out.println("[1]    Ascending");
					System.out.println("[2]    Descending");	
					System.out.print("Input Sort order: ");	
					orderFunction = InputUtil.inputOptionCheck(2);

					rows = EmployeeService.listEmployees(sessionFactory, sortFunction, orderFunction);
					System.out.println(rows + " Employees retrieved");

					break;
				case "5":
					String employeeRoleFunction = new String();
					System.out.println("Manage Employee's Roles");

					while (!subFunctionFlag) {
						System.out.println("[1]    Add Roles to Employee");
						System.out.println("[2]    Remove Roles from Employee");
						System.out.println("[3]    List All Roles from Employee");						
						System.out.println("[4]    Exit");
						System.out.print("Choose Function: ");

						employeeRoleFunction = InputUtil.getRequiredInput();

						switch (employeeRoleFunction) {
							case "1":
								EmployeeRoleService.addEmployeeRoles(sessionFactory);
								break;
							case "2":
								EmployeeRoleService.removeEmployeeRoles(sessionFactory);
								break;
							case "3":
								EmployeeRoleService.listEmployeeRoles(sessionFactory);
								break;
							case "4":
								subFunctionFlag = true;
								System.out.println("Exit");
								break;
							default:
								System.out.println("\nInvalid Function, choose another function below:");
								break;
						}
					}
					break;
				case "6":
					String contactInfoFunction = new String();
					System.out.println("Manage Employee's Contact Information");

					while(!subFunctionFlag) {
						System.out.println("[1]    Add Contact Information");
						System.out.println("[2]    Remove Contact Information");
						System.out.println("[3]    Update Contact Information");
						System.out.println("[4]    List Contact Information");
						System.out.println("[5]    Exit");
						System.out.print("Choose corresponding number select a function: ");

						contactInfoFunction = InputUtil.getRequiredInput();

						switch (contactInfoFunction) {
							case "1":
								rows = EmployeeService.listEmployees(sessionFactory, 4, 0);
								System.out.print("Add a contact info to which employee: ");
								Long employeeId = InputUtil.inputOptionCheck().longValue();
								ContactInfoService.addContactInfo(sessionFactory, employeeId);
								break;
							case "2":
								ContactInfoService.removeContactInfo(sessionFactory);
								break;
							case "3":
								ContactInfoService.updateContactInfo(sessionFactory);
								break;
							case "4":
								ContactInfoService.listContactInfo(sessionFactory);
								break;
							case "5":
								subFunctionFlag = true;
								System.out.println("Exit");
								break;
							default:
								System.out.println("\nInvalid Function, choose another function below:");
								break;
						}
					}

					break;
				case "7":
					String roleFunction = new String();
					System.out.println("Role Management");
					String roleName = new String();
					String roleCode = new String();				

					while(!subFunctionFlag) {
						System.out.println("[1]    Add Role");
						System.out.println("[2]    Remove Roles");
						System.out.println("[3]    Update Roles");
						System.out.println("[4]    List Roles");
						System.out.println("[5]    Exit");
						System.out.print("\nChoose an option above: ");

						roleFunction = InputUtil.getRequiredInput();

						switch (roleFunction) {
							case "1":
								System.out.println("Adding Role with the below information:");
								System.out.print("Provide RoleCode: ");
								roleCode = InputUtil.getRequiredInput();
								System.out.print("Provide RoleName: ");
								roleName = InputUtil.getRequiredInput();
								Roles addedRole = new Roles(roleName, roleCode);		
														
								RoleService.addRoles(sessionFactory, addedRole);
								break;
							case "2":
								RoleService.removeRoles(sessionFactory);
								break;
							case "3":
								RoleService.updateRoles(sessionFactory);
								break;
							case "4":
								System.out.println("Sort Roles:");
								System.out.println("[1]    by RoleID");
								System.out.println("[2]    by RoleCode");
								System.out.println("[3]    by RoleName");
								System.out.print("Choose sort rule: ");
								Integer sortRule = InputUtil.inputOptionCheck(3);
								
								System.out.println("[1]    Ascending");
								System.out.println("[2]    Descending");
								System.out.print("Choose order rule: ");
								Integer orderRule = InputUtil.inputOptionCheck(2);

								Integer out = RoleService.listRoles(sessionFactory, sortRule, orderRule);
								break;
							case "5":
								subFunctionFlag = true;
								System.out.println("Exit Role Management");
								break;
							default:
								System.out.println("\nInvalid Function.");
								break;
						}
					}
					break;
				case "8":
					exit = true;
					HibernateUtil.shutdown(sessionFactory);
					break;
				default:
					System.out.println(function + " is not a valid function. Choose another function");
					break;	
			}
			subFunctionFlag = false;
		}
	}
}