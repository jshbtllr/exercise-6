package com.exercise6.core.dao;

import com.exercise6.util.HibernateUtil;
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
import java.util.List;
import java.util.logging.Logger;

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