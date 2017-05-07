package com.exercise6.app;
import java.util.Scanner;
import com.exercise6.util.InputUtil;
import com.exercise6.core.service.ServiceManagement;
import com.exercise6.core.model.Roles;

public class Application {
	public static void main (String [] args) {
		Boolean exit = false;
		Boolean subFunctionFlag = false;
		String function = new String();

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

			System.out.println("");

			switch (function) {
				case "1":
					System.out.println("Create a new employee");
					ServiceManagement.createEmployee();
					break;
				case "2":
					ServiceManagement.deleteEmployee();
					break;
				case "3":
					ServiceManagement.updateEmployee();
					break;
				case "4":
					Integer sortFunction;
					System.out.println("Employee List sorted by: ");

					while (!subFunctionFlag) {
						System.out.println("[1]    Last Name");
						System.out.println("[2]    General Weighted Average");
						System.out.println("[3]    Date Hired");
						System.out.println("[4]    Exit");
						System.out.print("Choose corresponding number to sort: ");

						sortFunction = InputUtil.inputOptionCheck(4);
						if (sortFunction == 4) {
							break;
						}
						Integer rows = ServiceManagement.listEmployees(sortFunction);

						System.out.println(rows + " Employees retrieved");
					}

					break;
				case "5":
					String employeeRoleFunction = new String();
					System.out.println("Add or Remove Roles assigned to an Employee");

					while (!subFunctionFlag) {
						System.out.println("[1]    Add Roles to Employee");
						System.out.println("[2]    Remove Roles from Employee");
						System.out.println("[3]    List All Roles from Employee");						
						System.out.println("[4]    Exit");
						System.out.print("Choose Function: ");

						employeeRoleFunction = InputUtil.getRequiredInput();

						switch (employeeRoleFunction) {
							case "1":
								ServiceManagement.addEmployeeRoles();
								break;
							case "2":
								ServiceManagement.removeEmployeeRoles();
								break;
							case "3":
								ServiceManagement.listEmployeeRoles();
								break;
							case "4":
								subFunctionFlag = true;
								System.out.println("Exit Add or Remove Roles assigned");
								break;
							default:
								System.out.println("\nInvalid Function, choose another function below:");
								break;
						}
					}
					break;
				case "6":
					String contactInfoFunction = new String();
					System.out.println("Contact Information Management");

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
								ServiceManagement.addContactInfo();
								break;
							case "2":
								ServiceManagement.removeContactInfo();
								break;
							case "3":
								ServiceManagement.updateContactInfo();
								break;
							case "4":
								ServiceManagement.listContactInfo();
								break;
							case "5":
								subFunctionFlag = true;
								System.out.println("Exit Contact Information Management");
								break;
							default:
								System.out.println("\nInvalid Function, choose another function below:");
								break;
						}
					System.out.println("\nSelect Contact Info Management Option above:");

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
														
								ServiceManagement.addRoles(addedRole);
								break;
							case "2":
								System.out.println("Deleting Role with the below information:");
								System.out.print("Provide RoleCode: ");
								roleCode = InputUtil.getRequiredInput();
								System.out.print("Provide RoleName: ");
								roleName = InputUtil.getRequiredInput();
								Roles deletedRole = new Roles(roleName, roleCode);

								ServiceManagement.removeRoles(deletedRole);
								break;
							case "3":
								System.out.println("Update Roles");
								Integer rows = ServiceManagement.listRoles(1);
								System.out.print("\nChoose the role id of the role to edit: ");
								Integer roleId = InputUtil.inputOptionCheck(rows);

								System.out.println("\nEdit: ");
								System.out.println("[1]    RoleCode");
								System.out.println("[2]    RoleName");
								System.out.println("[3]    Both");
								System.out.print("Choose Option above: ");
								Integer option = InputUtil.inputOptionCheck(3);
								ServiceManagement.updateRoles(roleId, option);

								break;
							case "4":
								System.out.println("List all available Roles");
								System.out.println("[1]    by RoleID");
								System.out.println("[2]    by RoleCode");
								System.out.println("[3]    by RoleName");
								System.out.print("Choose order rule from above: ");
								Integer orderRule = InputUtil.inputOptionCheck(3);
								Integer out = ServiceManagement.listRoles(orderRule);
								System.out.println(out + " rows printed");
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
					break;
				default:
					System.out.println(function + " is not a valid function. Choose another function");
					break;	
			}
			subFunctionFlag = false;
		}
	}
}