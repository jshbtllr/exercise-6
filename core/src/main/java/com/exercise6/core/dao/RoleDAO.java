package com.exercise6.core.dao;

import com.exercise6.core.model.Roles;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import java.util.List;


public class RoleDAO {
	public static Integer addRole(SessionFactory sessionFactory, Roles role) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Integer rows = null;

		try {
			transaction = session.beginTransaction();
			String sql = "INSERT INTO ROLES (ROLECODE, ROLENAME) VALUES (:rolecode, :rolename)";
			Query query = session.createSQLQuery(sql);
			query.setParameter("rolename", role.getRoleName());
			query.setParameter("rolecode", role.getRoleCode());
			rows = query.executeUpdate();
			transaction.commit();
		} catch(HibernateException he) {
			if (transaction != null)  {
				transaction.rollback();
			}
			System.out.println("Error encountered adding role");
			he.printStackTrace();
		} finally {
			session.close();
		}
		return rows;
	}

	public static Integer deleteRole(SessionFactory sessionFactory, Long roleId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Integer rows = null;

		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("DELETE FROM Roles WHERE id = :roleid");			
			query.setParameter("roleid", roleId);
			rows = query.executeUpdate();
			transaction.commit();
		} catch(HibernateException he) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("Error encountered deleting role");
			he.printStackTrace();
		} finally {
			session.close();
		}

		return rows;
	}

	public static Integer updateRole(SessionFactory sessionFactory, Long roleId, Roles role, Integer option) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Query query;
		Integer rows = null;

		try {
			transaction = session.beginTransaction();

			if (option == 1) {	
				query = session.createQuery("UPDATE Roles SET roleCode = :rolecode WHERE id = :roleid");
				query.setParameter("rolecode", role.getRoleCode());
			} else if(option == 2) {
				query = session.createQuery("UPDATE Roles SET roleName = :rolename WHERE id = :roleid");
				query.setParameter("rolename", role.getRoleName());				
			} else {
				query = session.createQuery("UPDATE Roles SET roleCode = :rolecode, roleName = :rolename WHERE id = :roleid");
				query.setParameter("rolecode", role.getRoleCode());
				query.setParameter("rolename", role.getRoleName());				
			}

			query.setParameter("roleid", roleId);
			rows = query.executeUpdate();
			transaction.commit();
		} catch (HibernateException he) {
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}

		return rows;
	}

	public static Integer showRoles(SessionFactory sessionFactory, Integer sortRule, Integer orderRule) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Query query = null;
		Integer rows = new Integer(0);

		try {
			transaction = session.beginTransaction();

			if(sortRule == 1) {
				query = session.createQuery("FROM Roles ORDER BY id");
				
				if(orderRule == 2) {
					query = session.createQuery("FROM Roles ORDER BY id DESC");
				}
			} else if(sortRule == 2) {
				query = session.createQuery("FROM Roles ORDER BY roleCode");

				if(orderRule == 2) {
					query = session.createQuery("FROM Roles ORDER BY roleCode DESC");
				} 
			} else if(sortRule == 3) {
				query = session.createQuery("FROM Roles ORDER BY roleName");

				if(orderRule == 2) {
					query = session.createQuery("FROM Roles ORDER BY roleName desc");
				}
			}

			List <Roles> list = query.list();
			rows = list.size();
			for (Roles role : list ) {
				System.out.println("RoleID:    " + role.getId());
				System.out.println("RoleCode:  " + role.getRoleCode());
				System.out.println("RoleName:  " + role.getRoleName());
				System.out.println("--------------------------------");
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

	public static Boolean checkOccurrence(SessionFactory sessionFactory, Roles role, Integer option) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Boolean existing = false;
		Query query = null;

		try {
			transaction = session.beginTransaction();
			if (option == 1) {
				query = session.createQuery("SELECT id FROM Roles WHERE roleCode = :rolecode");
				query.setParameter("rolecode", role.getRoleCode());
			} else if (option == 2) {
				query = session.createQuery("SELECT id FROM Roles WHERE roleCode = :rolecode AND roleName = :rolename");
				query.setParameter("rolecode", role.getRoleCode());
				query.setParameter("rolename", role.getRoleName());
			} else if (option == 3) {
				query = session.createSQLQuery("SELECT B.EMPLOYEEID from EMPLOYEEROLE B WHERE B.ROLEID = :paramId");
				query.setParameter("paramId", role.getId());
			}

			existing = !(query.list().isEmpty());

		} catch(HibernateException he) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("Error occurred");
			he.printStackTrace();
		} finally {
			session.close();
		}

		return existing;
	}

	public static Roles getRole(SessionFactory sessionFactory, Long roleId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Criteria criteria = null;
		Roles output = null;

		try {
			transaction = session.beginTransaction();
			criteria = session.createCriteria(Roles.class);
			criteria.add(Restrictions.eq("id", roleId));
			output = (Roles) criteria.list().get(0);
		} catch(HibernateException he) {
			if(transaction != null) {
				transaction.rollback();
			}
			System.out.println("Error checking existence of role");
			he.printStackTrace();
		} finally {
			session.close();
		}

		return output;
	}

	public static Boolean checkId(SessionFactory sessionFactory, Long roleId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Query query = null;
		Boolean existing = false;

		try {
			transaction = session.beginTransaction();
			query = session.createQuery("SELECT id FROM Roles WHERE id = :roleid");
			query.setParameter("roleid", roleId);
			existing = !(query.list().isEmpty());
		} catch(HibernateException he) {
			if(transaction != null) {
				transaction.rollback();
			}
			System.out.println("Error checking existence of role");
			he.printStackTrace();
		} finally {
			session.close();
		}

		return existing;
	}
}