package com.exercise6.core.dao;

import com.exercise6.util.InputUtil;
import com.exercise6.core.model.Roles;
import com.exercise6.core.model.Address;
import com.exercise6.core.model.ContactInfo;
import com.exercise6.core.model.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import java.util.Date;
import java.util.List;
import java.util.Collections;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Comparator;

public class EmployeeDAO {
	public static Integer showEmployees(SessionFactory sessionFactory, Integer sort, Integer order) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Query query = null;
		Integer rows = new Integer(0);
		
		try {
			transaction = session.beginTransaction();

			if(sort == 1) {
				query = session.createSQLQuery("SELECT * FROM EMPLOYEE ORDER BY LASTNAME");	

				if(order == 2) {
					query = session.createSQLQuery("SELECT * FROM EMPLOYEE ORDER BY LASTNAME DESC");	
				}
			} else if(sort == 3) {
				query = session.createSQLQuery("SELECT * FROM EMPLOYEE ORDER BY HIREDATE");

				if(order == 2) {
					query = session.createSQLQuery("SELECT * FROM EMPLOYEE ORDER BY HIREDATE DESC");
				}
			} 

			if(!query.list().isEmpty()) {
				List <Object []> list = query.list();

				if(sort == 2) {
					Collections.sort(list, new gwaComparator());

					if(order == 2) {
						Collections.sort(list, Collections.reverseOrder(new gwaComparator()));						
					}
				}
				
				rows = list.size();
				
				for (Object [] employee : list ) {
					System.out.println("EmployeeID: " + employee[0]);
					System.out.println("FullName:   " + employee[5] + " " + employee[1] + ", " + employee[2] + " " + employee[3] + " " + employee[4]);
					
					if(sort != 4) {  	/*Sort Type 4 for employeeid and Fullname prints*/
						System.out.println("Address:    " + employee[6] + " " + employee[7] + " " + employee[8] + " " + employee[9]);
						System.out.println("Birthday:   " + employee[10]);
						System.out.println("GWA:        " + employee[11]);
						if(employee[13].equals(true)) {
							System.out.println("Employed:   Yes");
							System.out.println("Hire Date:  " + employee[12]);
						} else {
							System.out.println("Employed:   No");
							System.out.println("Hire Date:  " + "N/A");
						}
						System.out.println("-------------------------------------------------------------------\n");
					}
				}
			} else {
				System.out.println("No employees registered.");
			}			

		} catch(HibernateException he) {
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}

		return rows;				
	}



	public static Integer addEmployee (SessionFactory sessionFactory, Employee employee) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Integer rows = new Integer(0);
		String hql = new String();
		Address address = employee.getAddress();

		try {
			transaction = session.beginTransaction();
			hql = "INSERT INTO EMPLOYEE (lastname, firstname, middlename, Suffix, Title, Street, Barangay, City, Zipcode, Birthday, GWA, hiredate, Employed)" + 
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
		}

		return rows;		
	}

	public static void employeeUpdate(SessionFactory sessionFactory, Long employeeId, Employee employee, Integer option) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Integer rows = new Integer(0);
		String hql = new String();
		Query query = null;

		try {
			transaction = session.beginTransaction();

			if(option == 1) {
				hql = "UPDATE Employee SET lastName = :lastname, firstName = :firstname, middleName = :middlename, " +
						"suffix = :suffix, title = :title WHERE id = :employeeid";
				query = session.createQuery(hql);
				query.setParameter("lastname", employee.getLastName());
				query.setParameter("firstname", employee.getFirstName());
				query.setParameter("middlename", employee.getMiddleName());
				query.setParameter("suffix", employee.getSuffix());
				query.setParameter("title", employee.getTitle());
			} else if(option == 2) {
				hql = "UPDATE Employee E SET E.address.streetNumber = :street, E.address.barangay = :barangay, " +
						"E.address.city = :city, E.address.zipcode = :zipcode WHERE E.id = :employeeid";					
				query = session.createQuery(hql);
				query.setParameter("street", employee.getAddress().getStreetNumber());
				query.setParameter("barangay", employee.getAddress().getBarangay());
				query.setParameter("city", employee.getAddress().getCity());
				query.setParameter("zipcode", employee.getAddress().getZipcode());				
			} else if (option == 3) {
				hql = "UPDATE Employee SET birthday = :date WHERE id = :employeeid";
				query = session.createQuery(hql);
				query.setParameter("date", employee.getBirthday());
			} else if (option == 4) {
				hql = "UPDATE Employee SET gradeWeightAverage = :gwa WHERE id = :employeeid";
				query = session.createQuery(hql);
				query.setParameter("gwa", employee.getGradeWeightAverage());					
			} else {
				hql = "UPDATE Employee SET hireDate = :date, employed = :emp WHERE id = :employeeid";
				query = session.createQuery(hql);
				query.setParameter("date", employee.getHireDate());	
				query.setParameter("emp", employee.getEmployed());	
			}
			query.setParameter("employeeid", employeeId);
			Integer updated = query.executeUpdate();
			transaction.commit();

		} catch(HibernateException he) {
			if (transaction != null)  {
				transaction.rollback();
			}
			System.out.println("Error occurred");
			he.printStackTrace();
		} finally {
			session.close();
		}
	}

	public static void employeeDelete(SessionFactory sessionFactory, Long employeeId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Integer rows = new Integer(0);
		String hql = new String();
		Query query = null;

		try {
			transaction = session.beginTransaction();

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

			hql = "DELETE FROM EMPLOYEE WHERE employeeid = :employeeid";
			query = session.createSQLQuery(hql);
			query.setParameter("employeeid", employeeId);
			Integer deletedEmployee = query.executeUpdate();	
			transaction.commit();			
			System.out.println(deletedEmployee + " Employee Deleted.");

		} catch(HibernateException he) {
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}			

	}

	public static void insertEmployeeRole(SessionFactory sessionFactory, Roles role, Long employeeId) {
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

			hql = "SELECT RoleID FROM EMPROLE WHERE EmployeeID = :employeeid and RoleID = :roleid";
			query = session.createSQLQuery(hql);
			query.setParameter("roleid", roleId.intValue());
			query.setParameter("employeeid", employeeId);

			if (query.list().isEmpty()) {
				hql = "INSERT INTO EMPROLE (EmployeeID, RoleID) VALUES (:employeeid, :roleid)";
				query = session.createSQLQuery(hql);
				query.setParameter("employeeid", employeeId);
				query.setParameter("roleid", roleId.intValue());
				Integer rows = query.executeUpdate();	
				transaction.commit();
				System.out.println(rows + " role added to employee")			;
			} else {
				System.out.println("Role Already assigned to employee"); 
			}


		} catch(HibernateException he) {
			if (transaction != null)  {
				transaction.rollback();
			}
			System.out.println("Error occurred");
			he.printStackTrace();
		} finally {
			session.close();
		}			
	}

	public static Integer showEmployeeRoles(SessionFactory sessionFactory, Long employeeId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		String hql = new String();
		Query query = null;
		Integer roleNumber = new Integer(0);

		try {
			transaction = session.beginTransaction();
			hql = "SELECT Last_Name, First_Name FROM EMPLOYEE WHERE employeeid = :employeeid";
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
					System.out.println("RoleCode: " + roles[1]);
					System.out.println("RoleName: " + roles[2]);
					System.out.println("------------------------------");
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
		}

		return roleNumber;
	}

	public static Integer deleteEmployeeRoles(SessionFactory sessionFactory, String roleCode) {
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
				System.out.println("Role has been deleted");
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
		}

		return roleDelete;
	}

	public static Boolean employeeCheck(SessionFactory sessionFactory, Long employeeId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		String hql = new String();
		Query query = null;
		Boolean present = false;

		try {
			transaction = session.beginTransaction();
			hql = "SELECT A.employeeid FROM EMPLOYEE A WHERE employeeid = :employeeid";
			query = session.createSQLQuery(hql);
			query.setParameter("employeeid", employeeId);

			if(query.list().isEmpty()) {
				present = false;
			} else {
				present = true;
			}
		} catch(HibernateException he) {
			if (transaction != null)  {
				transaction.rollback();
			}
			System.out.println("Error occurred");
			he.printStackTrace();
		} finally {
			session.close();
		}

		return present;
	}

}

class gwaComparator implements Comparator <Object []> {
	public int compare(Object [] a, Object [] b) {
		return (Float) a[11] < (Float) b[11] ? -1 : a[11] == b[11] ? 0 : 1;
	}
}

