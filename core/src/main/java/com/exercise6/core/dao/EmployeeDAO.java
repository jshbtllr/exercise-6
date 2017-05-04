package com.exercise6.core.dao;
import java.exercise6.util.HibernateUtil;
import java.exercise7.core.model.Roles;
import java.exercise7.core.model.Address;
import java.exercise7.core.model.ContactInfo;
import java.exercise7.core.model.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class EmployeeDAO {
	public static Integer addRole(Roles role) {
		Session session = HibernateUtil.getSessionFactory().session();
		Transaction transaction = null;
		Integer roleId = null;

		try {
			transaction = session.beginTransaction();
			String hql = "INSERT INTO ROLES (rolecode, rolename) VALUES (:rolecode, :rolename)"
			Query query = session.createQuery(hql);
			query.setParameter("rolename", role.getRoleName);
			query.setParameter("rolecode", role.getRoleCode);
			roleId = query.executeUpdate();
			//roleId = (Integer) session.save(role);
			//transaction.commit();
		} catch(HibernateException he) {
			if (transaction != null)  {
				transaction.rollback();
			}

			he.printStackTrace();
		} finally {
			session.close();
		}

		return roleId;
	}
	
}
