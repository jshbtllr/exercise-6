package com.exercise6.core.dao;

import com.exercise6.util.HibernateUtil;
import com.exercise6.util.InputUtil;
import com.exercise6.core.model.Roles;
import com.exercise6.core.model.Address;
import com.exercise6.core.model.ContactInfo;
import com.exercise6.core.model.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class EmployeeDAO {
	public static Integer addEmployee (Employee employee) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Integer rows = new Integer(0);
		String hql = new String();
		Address address = employee.getAddress();

		try {
			transaction = session.beginTransaction();
			hql = "INSERT INTO EMPLOYEE (Last_Name, First_Name, Middle_Name, Suffix, Title, Street, Barangay, City, Zipcode, Birthday, GWA, Hire_Date, Employed)" + 
				" VALUES (:lastname, :firstname, :middlename, :suffix, :title, :street, :barangay, :city, :zipcode, :birthday, :gwa, :hiredate, :employed)";
			Query query = session.createSQLQuery(hql);
			query.setParameter("lastname", employee.getLastName());
			query.setParameter("firstname", employee.getFirstName());
			query.setParameter("middlename", employee.getMiddleName());
			query.setParameter("suffix", employee.getSuffix());
			query.setParameter("title", employee.getTitle());
			query.setParameter("street", address.getStreetNumber());
			query.setParameter("barangay", address.getBarangay());
			query.setParameter("city", address.getCity());
			query.setParameter("zipcode", address.getZipcode());
			query.setParameter("birthday", employee.getBirthday());
			query.setParameter("gwa", employee.getGradeWeightAverage());
			query.setParameter("hiredate", employee.getHireDate());
			query.setParameter("employed", employee.getEmployed());
			rows = query.executeUpdate();
			transaction.commit();
		} catch(HibernateException he) {
			if (transaction != null)  {
				transaction.rollback();
			}
			System.out.println("Error occurred");
			he.printStackTrace();
		} finally {
			session.close();
			HibernateUtil.shutdown(sessionFactory);
		}

		return rows;		
	}

	public static void employeeUpdate(Integer employeeId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Integer rows = new Integer(0);
		String hql = new String();
		Query query = null;

		try {
			transaction = session.beginTransaction();
			hql = "SELECT * FROM EMPLOYEE WHERE Id = :employeeid";
			query = session.createSQLQuery(hql);
			query.setParameter("employeeid", employeeId);

			if(!query.list().isEmpty()) {
				System.out.println("[1]    Full Name");
				System.out.println("[2]    Address");
				System.out.println("[3]    Birthday");
				System.out.println("[4]    GWA");
				System.out.println("[5]    Employment Status");
				System.out.print("Choose function above: ");
				Integer option = InputUtil.inputOptionCheck(5);

				if(option == 1) {
					System.out.print("Input New First Name: ");
					String firstName = InputUtil.getRequiredInput();
					System.out.print("Input New Last Name: ");
					String lastName = InputUtil.getRequiredInput();
					System.out.print("Input New Middle Name: ");
					String middleName = InputUtil.getRequiredInput();
					System.out.print("Input New Suffix Name: ");
					String suffix = InputUtil.getRequiredInput();
					System.out.print("Input New Title Name: ");
					String title = InputUtil.getRequiredInput();
					hql = "UPDATE EMPLOYEE SET last_name = :lastname, first_name = :firstname, middle_name = :middlename, " +
							"suffix = :suffix, title = :title WHERE id = :employeeid";
					query = session.createSQLQuery(hql);
					query.setParameter("lastname", lastName);
					query.setParameter("firstname", firstName);
					query.setParameter("middlename", middleName);
					query.setParameter("suffix", suffix);
					query.setParameter("title", title);
				} else if(option == 2) {
					System.out.print("Input New Street Number: ");
					String streetNumber = InputUtil.getRequiredInput();
					System.out.print("Input New Barangay: ");
					String barangay = InputUtil.getRequiredInput();
					System.out.print("Input New City: ");
					String city = InputUtil.getRequiredInput();
					System.out.print("Input New Zipcode: ");
					String zipcode = InputUtil.getRequiredInput();	
					hql = "UPDATE EMPLOYEE SET street = :street, barangay = :barangay, " +
							"city = :city, zipcode = :zipcode WHERE id = :employeeid";					
					query = session.createSQLQuery(hql);
					query.setParameter("street", streetNumber);
					query.setParameter("barangay", barangay);
					query.setParameter("city", city);
					query.setParameter("zipcode", zipcode);				
				} else if (option == 3) {
					System.out.print("Input New Birthdate (dd/mm/yyyy): ");
					Date birthday = InputUtil.getDate();
					hql = "UPDATE EMPLOYEE SET birthday =: date WHERE id = :employeeid";
					query = session.createSQLQuery(hql);
					query.setParameter("date", birthday);
				} else if (option == 4) {
					System.out.print("Input new GWA: ");
					Float gwa = InputUtil.getGrade();
					hql = "UPDATE EMPLOYEE SET gwa = :gwa WHERE id = :employeeid";
					query = session.createSQLQuery(hql);
					query.setParameter("gwa", gwa);					
				} else {
					System.out.print("Input New Employee Status Y if person is employed, N if not: ");
					Boolean employed = InputUtil.getStatus();
					Date hireDate = null;
					if (employed == true) {
						System.out.print("Enter Employee Hire Date in format dd/mm/yyyy: ");
						hireDate = InputUtil.getDate();
					} else {
						try {
							SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
							hireDate = format.parse("01/01/0001");
						} catch(ParseException pe) {
							pe.printStackTrace();
						}
					}
					hql = "UPDATE EMPLOYEE SET hire_date = :date, employed = :emp WHERE id = :employeeid";
					query = session.createSQLQuery(hql);
					query.setParameter("date", hireDate);	
					query.setParameter("emp", employed);	
				}

				query.setParameter("employeeid", employeeId);
				Integer updated = query.executeUpdate();
				transaction.commit();

			} else {
				System.out.println("Employee does not exist.");
			}

		} catch(HibernateException he) {
			if (transaction != null)  {
				transaction.rollback();
			}
			System.out.println("Error occurred");
			he.printStackTrace();
		} finally {
			session.close();
			HibernateUtil.shutdown(sessionFactory);
		}
	}

	public static void employeeDelete(Integer employeeId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Integer rows = new Integer(0);
		String hql = new String();
		Query query = null;

		try {
			transaction = session.beginTransaction();
			hql = "SELECT * FROM EMPLOYEE WHERE Id = :employeeid";
			query = session.createSQLQuery(hql);
			query.setParameter("employeeid", employeeId);

			if(!query.list().isEmpty()) {
				hql = "SELECT * FROM EMPROLE WHERE EmployeeID = :employeeid";
				query = session.createSQLQuery(hql);
				query.setParameter("employeeid", employeeId);

				if(!query.list().isEmpty()) {
					hql = "DELETE FROM EMPROLE WHERE EmployeeID = :employeeid";
					query = session.createSQLQuery(hql);
					query.setParameter("employeeid", employeeId);
					Integer deletedRows = query.executeUpdate();
					System.out.println(deletedRows + " employee's roles were deleted");
				} else {
					System.out.println("No roles assigned to employee and none deleted.");
				}

				hql = "SELECT * FROM CONTACTINFO WHERE EmployeeID = :employeeid";
				query = session.createSQLQuery(hql);
				query.setParameter("employeeid", employeeId);				

				if(!query.list().isEmpty()) {
					hql = "DELETE FROM CONTACTINFO WHERE EmployeeID = :employeeid";
					query = session.createSQLQuery(hql);
					query.setParameter("employeeid", employeeId);
					Integer deletedContacts = query.executeUpdate();
					System.out.println(deletedContacts + " employee's contacts were deleted");
				} else {
					System.out.println("No contact info assigned to employee and none deleted.");	
				}

				hql = "DELETE FROM EMPLOYEE WHERE id = :employeeid";
				query = session.createSQLQuery(hql);
				query.setParameter("employeeid", employeeId);
				Integer deletedEmployee = query.executeUpdate();				
				System.out.println(deletedEmployee + " Employee Deleted.");

			} else {
				System.out.println("Employee does not exist. No employee deleted");
			}		
		} catch(HibernateException he) {
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			HibernateUtil.shutdown(sessionFactory);
		}			

	}

	public static Integer showEmployees(Integer order) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Query query;
		Integer rows = new Integer(0);
		
		try {
			transaction = session.beginTransaction();

			if(order == 1) {
				query = session.createSQLQuery("SELECT * FROM EMPLOYEE ORDER BY Last_Name");	
			} else if(order == 2) {
				query = session.createSQLQuery("SELECT * FROM EMPLOYEE ORDER BY Last_Name");
			} else if(order == 3) {
				query = session.createSQLQuery("SELECT * FROM EMPLOYEE ORDER BY hire_date");
			} else {
				System.out.println("Invalid order rule, order will be set to defaul order by RoleID");
				query = session.createSQLQuery("FROM ROLES ORDER BY RoleID");	
			}

			List <Object []>  list = query.list();
			rows = list.size();
			for (Object [] employee : list ) {
				System.out.print("EmployeeID: " + employee[0] + "    FullName: " + employee[5] + " " + employee[1] + ", " + 
									employee[2] + " " + employee[3] + " " + employee[4] + "    Address: " + employee[6] + " " + 
									employee[7] + " " + employee[8] + " " + employee[9] + "    Birthday: " + employee[10] +
									"    Birthday: " + employee[11]);

				if(employee[13].equals(true)) {
					System.out.print("    Employed: Yes");
					System.out.println("  Hire Date: " + employee[12]);
				} else {
					System.out.println("    Employed: No");
					System.out.println("  Hire Date: " + "N/A");
				}
			}

		} catch(HibernateException he) {
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			HibernateUtil.shutdown(sessionFactory);
		}

		return rows;				
	}

	public static void insertEmployeeRole(Roles role, Integer employeeId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		String hql = new String();
		Query query = null;

		try {
			transaction = session.beginTransaction();
			hql = "SELECT RoleID FROM ROLES WHERE RoleCode = :rolecode";
			query = session.createSQLQuery(hql);
			query.setParameter("rolecode", role.getRoleCode());
			Number roleId = (Number) query.list().get(0);

			hql = "SELECT RoleID FROM EMPROLE WHERE EmployeeID = :employeeid";
			query = session.createSQLQuery(hql);
			query.setParameter("employeeid", employeeId);

			Number occurred = (Number) query.list().get(0);

			if (occurred.intValue() == 0) {
				hql = "INSERT INTO EMPROLE (EmployeeID, RoleID) VALUES (:employeeid, :roleid)";
				query = session.createSQLQuery(hql);
				query.setParameter("employeeid", employeeId);
				query.setParameter("roleid", roleId.intValue());
				Integer rows = query.executeUpdate();					
			} else {
				System.out.println("Role Already assigned to employee"); 
			}

			transaction.commit();

		} catch(HibernateException he) {
			if (transaction != null)  {
				transaction.rollback();
			}
			System.out.println("Error occurred");
			he.printStackTrace();
		} finally {
			session.close();
			HibernateUtil.shutdown(sessionFactory);
		}			
	}

	public static Integer showEmployeeRoles(Integer employeeId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		String hql = new String();
		Query query = null;
		Integer roleNumber = new Integer(0);

		try {
			transaction = session.beginTransaction();
			hql = "SELECT Last_Name, First_Name FROM EMPLOYEE WHERE id = :employeeid";
			query = session.createSQLQuery(hql);
			query.setParameter("employeeid", employeeId);

			List <Object []>  list = query.list();
			for(Object [] names : list) {
				System.out.println("Employee: " + names[0] + ", " + names[1] + " has below roles: ");
			}

			hql = "SELECT A.RoleID, B.RoleCode, B.RoleName FROM EMPROLE A, ROLES B WHERE A.EmployeeID = :employeeid AND B.RoleId = A.RoleId";
			query = session.createSQLQuery(hql);
			query.setParameter("employeeid", employeeId);

			list = query.list();
			roleNumber = list.size();

			if (roleNumber > 0) {
				for(Object [] roles : list) {
					System.out.println("RoleCode: " + roles[1] + "    RoleName:" + roles[2]);
				}
			} else {
				System.out.println("None");
			}
		} catch(HibernateException he) {
			if (transaction != null)  {
				transaction.rollback();
			}
			System.out.println("Error occurred");
			he.printStackTrace();
		} finally {
			session.close();
			HibernateUtil.shutdown(sessionFactory);
		}

		return roleNumber;
	}

	public static Integer deleteEmployeeRoles(String roleCode) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		String hql = new String();
		Query query = null;
		Integer roleDelete = new Integer(0);
		Number occurred = roleDelete;

		try {
			transaction = session.beginTransaction();
			hql = "SELECT A.RoleID FROM EMPROLE A, ROLES B WHERE B.RoleCode = :rolecode AND B.RoleId = A.RoleId";
			query = session.createSQLQuery(hql);
			query.setParameter("rolecode", roleCode);


			if(!query.list().isEmpty()) {
				occurred = (Number) query.list().get(0);
				hql = "DELETE FROM EMPROLE WHERE RoleID = :roleid";
				query = session.createSQLQuery(hql);			
				query.setParameter("roleid", occurred.intValue());
				roleDelete = query.executeUpdate();
				transaction.commit();				
			} else {
				System.out.println("Role can't be deleted since it is not assigned to employee");
			}

		} catch(HibernateException he) {
			if (transaction != null)  {
				transaction.rollback();
			}
			System.out.println("Error occurred");
			he.printStackTrace();
		} finally {
			session.close();
			HibernateUtil.shutdown(sessionFactory);
		}

		return roleDelete;
	}

}