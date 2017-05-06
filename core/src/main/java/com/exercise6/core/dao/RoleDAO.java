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


public class RoleDAO {
	public static Integer addRole(Roles role) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Integer rows = new Integer(0);
		String hql = new String();

		try {
			transaction = session.beginTransaction();
			hql = "INSERT INTO ROLES (rolecode, rolename) VALUES (:rolecode, :rolename)";
			Query query = session.createSQLQuery(hql);
			query.setParameter("rolename", role.getRoleName());
			query.setParameter("rolecode", role.getRoleCode());
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

	public static Integer deleteRole(Roles role) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Integer rows = new Integer(0);
		String hql = new String();

		try {
			transaction = session.beginTransaction();
			hql = "DELETE FROM ROLES WHERE RoleCode = :rolecode AND RoleName = :rolename";
			Query query = session.createSQLQuery(hql);			
			query.setParameter("rolename", role.getRoleName());
			query.setParameter("rolecode", role.getRoleCode());
			rows = query.executeUpdate();
			transaction.commit();
		} catch(HibernateException he) {
			if (transaction != null) {
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

	public static Integer updateRole(Integer roleId, String roleCode, String roleName, Integer option) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Integer rows = new Integer(0);
		Query query;

		try {
			transaction = session.beginTransaction();

			if (option == 1) {	
				query = session.createSQLQuery("UPDATE ROLES SET roleCode = :rolecode WHERE roleId = :roleid");
				query.setParameter("rolecode", roleCode);
				query.setParameter("roleid", roleId);
			} else if(option == 2) {
				query = session.createSQLQuery("UPDATE ROLES SET roleName = :rolename WHERE roleId = :roleid");
				query.setParameter("rolename", roleName);
				query.setParameter("roleid", roleId);				
			} else {
				query = session.createSQLQuery("UPDATE ROLES SET roleCode = :rolecode, roleName = :rolename WHERE roleId = :roleid");
				query.setParameter("rolecode", roleCode);
				query.setParameter("rolename", roleName);
				query.setParameter("roleid", roleId);				
			}

			rows = query.executeUpdate();
			transaction.commit();
		} catch (HibernateException he) {
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			HibernateUtil.shutdown(sessionFactory);
		}

		return rows;
	}

	public static Integer showRoles(Integer order) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Query query;
		Integer rows = new Integer(0);

		try {
			transaction = session.beginTransaction();

			if(order == 1) {
				query = session.createSQLQuery("SELECT * FROM ROLES ORDER BY RoleID");	
			} else if(order == 2) {
				query = session.createSQLQuery("SELECT * FROM ROLES ORDER BY RoleCode");
			} else if(order == 3) {
				query = session.createSQLQuery("SELECT * FROM ROLES ORDER BY RoleName");
			} else {
				System.out.println("Invalid order rule, order will be set to defaul order by RoleID");
				query = session.createSQLQuery("FROM ROLES ORDER BY RoleID");	
			}

			List <Object []>  list = query.list();
			rows = list.size();
			for (Object [] roles : list ) {
				System.out.println("RoleID: " + roles[0] + "    RoleCode: " + roles[1] + "    RoleName: " + roles[2]);
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

	public static Number checkOccurrence(Roles role, Integer option) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Number duplicate = null;
		String hql = new String();
		Query query;

		try {
			transaction = session.beginTransaction();
			if (option == 1) {
				hql = "SELECT COUNT(A.RoleCode) FROM ROLES A WHERE A.RoleCode = :rolecode";
				query = session.createSQLQuery(hql);
				query.setParameter("rolecode", role.getRoleCode());
				duplicate = (Number) query.list().get(0);
			} else if (option == 2) {
				hql = "SELECT COUNT(A.RoleCode) FROM ROLES A WHERE A.RoleCode = :rolecode AND A.RoleName = :rolename";
				query = session.createSQLQuery(hql);
				query.setParameter("rolecode", role.getRoleCode());
				query.setParameter("rolename", role.getRoleName());
				duplicate = (Number) query.list().get(0);
			} else if (option == 3) {
				hql = "SELECT A.RoleID FROM ROLES A WHERE A.RoleName = :rolename AND A.RoleCode = :rolecode";
				query = session.createSQLQuery(hql);
				query.setParameter("rolecode", role.getRoleCode());
				query.setParameter("rolename", role.getRoleName());
				Integer roleID = (Integer) query.list().get(0);

				hql = "SELECT COUNT(B.EmployeeID) from EMPROLE B WHERE B.RoleID = :paramId";
				query = session.createSQLQuery(hql);
				query.setParameter("paramId", roleID);
				duplicate = (Number) query.list().get(0);
			}

		} catch(HibernateException he) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("Error occurred");
			he.printStackTrace();
		} finally {
			HibernateUtil.shutdown(sessionFactory);
		}

		return duplicate;
	}
	
}
