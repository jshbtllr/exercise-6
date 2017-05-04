package com.exercise6.core.dao;

import com.exercise6.util.HibernateUtil;
import com.exercise6.core.model.Roles;
import com.exercise6.core.model.Address;
import com.exercise6.core.model.ContactInfo;
import com.exercise6.core.model.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import org.hibernate.HibernateException;

public class EmployeeDAO {
	public static Integer addRole(Roles role) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		Integer roleId = null;

		try {
			transaction = session.beginTransaction();
			String hql = "INSERT INTO ROLES (rolecode, rolename) VALUES (:rolecode, :rolename)";
			Query query = session.createQuery(hql);
			query.setParameter("rolename", role.getRoleName());
			query.setParameter("rolecode", role.getRoleCode());
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
