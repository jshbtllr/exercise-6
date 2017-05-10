package com.exercise6.core.dao;

import com.exercise6.core.model.Roles;
import com.exercise6.core.model.Address;
import com.exercise6.core.model.ContactInfo;
import com.exercise6.core.model.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import java.util.Date;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class EmployeeDAO {
	public static void addEmployee (SessionFactory sessionFactory, Employee employee) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Integer rows = new Integer(0);
		Address address = employee.getAddress();

		try {
			transaction = session.beginTransaction();
			session.save(employee);
			transaction.commit();
		} catch(HibernateException he) {
			if (transaction != null)  {
				transaction.rollback();
			}
			System.out.println("Error encountered adding employee.");
			he.printStackTrace();
		} finally {
			session.close();
		}	
	}

	public static Employee getEmployee(SessionFactory sessionFactory, Long employeeId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Employee employee = null;
		Criteria criteria = null;
		try {
			transaction = session.beginTransaction();
			criteria = session.createCriteria(Employee.class);
			criteria.add(Restrictions.eq("id", employeeId));
			employee = (Employee) criteria.list().get(0);
		} catch(HibernateException he) {
			if(transaction != null) {
				transaction.rollback();
			}
			System.out.println("Error getting employee");
			he.printStackTrace();
		} finally {
			session.close();
		}
		return employee;
	}

	public static void updateEmployee(SessionFactory sessionFactory, Employee employee) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Integer rows = new Integer(0);
		String hql = new String();
		Query query = null;

		try {
			transaction = session.beginTransaction();
			session.update(employee);
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

	public static void deleteEmployee(SessionFactory sessionFactory, Long employeeId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Integer rows = new Integer(0);
		String hql = new String();
		Query query = null;

		try {
			transaction = session.beginTransaction();
			query = session.createSQLQuery("DELETE FROM CONTACTINFO WHERE EMPLOYEEID = :employeeid");
			query.setParameter("employeeid", employeeId);
			Integer deletedContacts = query.executeUpdate();
			query = session.createQuery("DELETE FROM Employee WHERE id = :employeeid");
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

	public static Integer showEmployees(SessionFactory sessionFactory, Integer sort, Integer order) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Query query = null;
		Integer rows = new Integer(0);
		
		try {
			transaction = session.beginTransaction();
			query = session.createSQLQuery("SELECT * FROM EMPLOYEE ORDER BY EMPLOYEEID");

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
					System.out.println("FullName:   " + ((employee[5].equals("") || employee[5].equals(" ")) ? "" : employee[5] + " ") + employee[1] + ", " + employee[2] + " " + employee[3] + " " + employee[4]);
					
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
					}
					System.out.println("-------------------------------------------------------------------\n");
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

			hql = "SELECT RoleID FROM EMPLOYEEROLE WHERE EmployeeID = :employeeid and RoleID = :roleid";
			query = session.createSQLQuery(hql);
			query.setParameter("roleid", roleId.intValue());
			query.setParameter("employeeid", employeeId);

			if (query.list().isEmpty()) {
				hql = "INSERT INTO EMPLOYEEROLE (EmployeeID, RoleID) VALUES (:employeeid, :roleid)";
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

			hql = "SELECT A.RoleID, B.RoleCode, B.RoleName FROM EMPLOYEEROLE A, ROLES B WHERE A.EmployeeID = :employeeid AND B.RoleId = A.RoleId";
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
			hql = "SELECT A.RoleID FROM EMPLOYEEROLE A, ROLES B WHERE B.RoleCode = :rolecode AND B.RoleId = A.RoleId";
			query = session.createSQLQuery(hql);
			query.setParameter("rolecode", roleCode);


			if(!query.list().isEmpty()) {
				occurred = (Number) query.list().get(0);
				hql = "DELETE FROM EMPLOYEEROLE WHERE RoleID = :roleid";
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
		Query query = null;
		Boolean present = false;

		try {
			transaction = session.beginTransaction();
			query = session.createQuery("SELECT id FROM Employee WHERE id = :employeeid");
			query.setParameter("employeeid", employeeId);

			present = !(query.list().isEmpty());
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