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
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import java.util.List;
import java.util.logging.Logger;

public class ContactDAO {
	public static void addContact(SessionFactory sessionFactory, ContactInfo contact, Integer employeeId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		String hql = new String();
		Query query = null;

		try {
			transaction = session.beginTransaction();
			hql = "INSERT INTO CONTACTINFO (ContactInfoType, ContactInformation, employeeId) VALUES (:infotype, :infodetail, :employeeid)";	
			query = session.createSQLQuery(hql);
			query.setParameter("infotype", contact.getInfoType());
			query.setParameter("infodetail", contact.getInfoDetail());
			query.setParameter("employeeid", employeeId);
			Integer rows = query.executeUpdate();
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

	public static void updateContact(SessionFactory sessionFactory, Integer contactId, Integer employeeId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		String hql = new String();
		Query query = null;
		Integer contactDelete = new Integer(0);

		try {
			transaction = session.beginTransaction();
			hql = "SELECT * FROM CONTACTINFO WHERE ContactID = :contactId AND EmployeeID = :employeeID";
			query = session.createSQLQuery(hql);
			query.setParameter("contactId", contactId);
			query.setParameter("employeeID", employeeId);

			if(!query.list().isEmpty()) {
				System.out.print("Input new Contact information: ");
				String newInfo = InputUtil.getRequiredInput();
				hql = "UPDATE CONTACTINFO SET ContactInformation = :newinfo WHERE ContactID = :contactId AND EmployeeID = :employeeID";
				query = session.createSQLQuery(hql);
				query.setParameter("newinfo", newInfo);
				query.setParameter("contactId", contactId);
				query.setParameter("employeeID", employeeId);
				contactDelete = query.executeUpdate();	
				transaction.commit();	
			} else {
				System.out.println("Contact ID chosen is invalid, no contacts were deleted.");
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

	public static void deleteContact(SessionFactory sessionFactory, Integer contactId, Integer employeeId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		String hql = new String();
		Query query = null;
		Integer contactDelete = new Integer(0);

		try {
			transaction = session.beginTransaction();
			hql = "SELECT * FROM CONTACTINFO WHERE ContactID = :contactId AND EmployeeID = :employeeID";
			query = session.createSQLQuery(hql);
			query.setParameter("contactId", contactId);
			query.setParameter("employeeID", employeeId);

			if(!query.list().isEmpty()) {
				hql = "DELETE FROM CONTACTINFO WHERE ContactID = :contactId AND EmployeeID = :employeeID";
				query = session.createSQLQuery(hql);			
				query.setParameter("contactId", contactId);
				query.setParameter("employeeID", employeeId);
				contactDelete = query.executeUpdate();	
				transaction.commit();	
				System.out.println("Contact Info deleted");
			} else {
				System.out.println("Contact ID chosen is invalid, no contacts were deleted.");
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

	public static Integer employeeContact(SessionFactory sessionFactory, Integer employeeId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		String hql = new String();
		Query query = null;
		Integer contactNumber = new Integer(0);

		try {
			transaction = session.beginTransaction();		
			hql = "SELECT Last_Name, First_Name FROM EMPLOYEE WHERE id = :employeeid";
			query = session.createSQLQuery(hql);
			query.setParameter("employeeid", employeeId);

			List <Object []>  list = query.list();
			for(Object [] names : list) {
				System.out.println("Employee: " + names[0] + ", " + names[1] + " has below Contact Info: ");
			}

			hql = "SELECT A.ContactID, A.ContactInfoType, A.ContactInformation FROM CONTACTINFO A, EMPLOYEE B WHERE A.EmployeeID = :employeeid AND A.EmployeeID = B.id";
			query = session.createSQLQuery(hql);
			query.setParameter("employeeid", employeeId);

			list = query.list();
			contactNumber = list.size();

			if (contactNumber > 0) {
				for(Object [] contactList : list) {
					System.out.println("ContactID: " + contactList[0] + "    ContactInfoType: " + contactList[1] + "    ContactInfo:" + contactList[2]);
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

		return contactNumber;
	}
}