package com.exercise6.app;
import java.util.Scanner;
import com.exercise6.core.service.ServiceManagement;

public class Application {
	public static void main (String [] args) {
		Boolean exit = false;
		Boolean subFunctionFlag = false;
		String function = new String();

		while(!exit) {
			System.out.println("\nEmployee Database");
			System.out.println("Menu");
			System.out.println(" [1]   Create Employee");
			System.out.println(" [2]   Delete Employee");
			System.out.println(" [3]   Update Employee Details");
			System.out.println(" [4]   List all Employees");
			System.out.println(" [5]   Manage Employee Roles");
			System.out.println(" [6]   Manage Employee Contacts");
			System.out.println(" [7]   Role Management");
			System.out.println(" [8]   Exit Tool");
			System.out.print("\nChoose corresponding number to choose a function: ");

			Scanner input = new Scanner(System.in);
			function = input.nextLine();

			System.out.println("");

			switch (function) {
				case "1":
					ServiceManagement.createEmployee();
					break;
				case "2":
					ServiceManagement.deleteEmployee();
					break;
				case "3":
					ServiceManagement.updateEmployee();
					break;
				case "4":
					String sortFunction = new String();
					System.out.println("Employee List sorted by: ");

					while (!subFunctionFlag) {
						System.out.println("[1]  Last Name");
						System.out.println("[2]  General Weighted Average");
						System.out.println("[3]  Date Hired");
						System.out.print("Choose corresponding number to sort: ");

						sortFunction = input.nextLine();

						switch (sortFunction) {
							case "1":
								subFunctionFlag = true;
								ServiceManagement.listEmployeeByLastName();
								break;
							case "2":
								subFunctionFlag = true;
								ServiceManagement.listEmployeeByGWA();
								break;
							case "3":
								subFunctionFlag = true;
								ServiceManagement.listEmployeeByHireDate();
								break;
							default:
								System.out.println("\nInvalid Function, choose another function below:");
								break;
						}
					}

					break;
				case "5":
					String employeeRoleFunction = new String();
					System.out.println("Add or Remove Roles assigned to an Employee");

					while (!subFunctionFlag) {
						System.out.println("[1]  Add Roles to Employee");
						System.out.println("[2]  Remove Roles from Employee");
						System.out.println("[3]  Exit");
						System.out.print("Choose corresponding number to sort: ");

						employeeRoleFunction = input.nextLine();

						switch (employeeRoleFunction) {
							case "1":
								ServiceManagement.addEmployeeRoles();
								break;
							case "2":
								ServiceManagement.removeEmployeeRoles();
								break;
							case "3":
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
						System.out.println("[1]  Add Contact Information");
						System.out.println("[2]  Remove Contact Information");
						System.out.println("[3]  Update Contact Information");
						System.out.println("[4]  List Contact Information");
						System.out.println("[5]  Exit");
						System.out.print("Choose corresponding number select a function: ");

						contactInfoFunction = input.nextLine();

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

					}

					break;
				case "7":
					String roleFunction = new String();
					System.out.println("Role Management");

					while(!subFunctionFlag) {
						System.out.println("[1]  Add Role");
						System.out.println("[2]  Remove Roles");
						System.out.println("[3]  Update Roles");
						System.out.println("[4]  List Roles");
						System.out.println("[5]  Exit");

						roleFunction = input.nextLine();

						switch (roleFunction) {
							case "1":
								ServiceManagement.addRoles();
								break;
							case "2":
								ServiceManagement.removeRoles();
								break;
							case "3":
								ServiceManagement.updateRoles();
								break;
							case "4":
								ServiceManagement.listRoles();
								break;
							case "5":
								subFunctionFlag = true;
								System.out.println("Exit Role Management");
								break;
							default:
								System.out.println("\nInvalid Function, choose another function below:");
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