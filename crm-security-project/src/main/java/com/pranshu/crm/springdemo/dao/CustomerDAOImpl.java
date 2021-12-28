package com.pranshu.crm.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pranshu.crm.springdemo.entity.Customer;
import com.pranshu.crm.springdemo.utils.SortUtils;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// need to inject the session factory
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Customer> getCustomers(int sortId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		String sortType = null;
		if(sortId == SortUtils.FIRST_NAME) {
			sortType = "firstName";
		}else if(sortId == SortUtils.LAST_NAME) {
			sortType = "lastName";
		}else {
			sortType = "email";
		}
		
		// create a query ... sort by required filter
		Query<Customer> theQuery = 
				currentSession.createQuery("from Customer order by "+ sortType, Customer.class);
		
		// execute the query
		List<Customer> customers = theQuery.getResultList();
		
		// return the results
		return customers;
		
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// save/update the customer
		currentSession.saveOrUpdate(theCustomer);
	}

	@Override
	public Customer getCustomer(int theId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		// now read from database using the primary key
		Customer theCustomer = currentSession.get(Customer.class, theId);
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// delete the object with primary key
		
		Query<?> theQuery = 
				currentSession.createQuery("delete from Customer where id=:customerId");
		theQuery.setParameter("customerId", theId);
		theQuery.executeUpdate();
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		// write a query to search in the database
		Query<Customer> theQuery = null;
		
		// check if theSearchName is null
		
		if(theSearchName == null || theSearchName.trim().length() == 0) {
			// invalid search -- > return complete list
			theQuery = currentSession.createQuery("from Customer order by firstName",Customer.class);
		}
		else {
			// valid search name and must be calculated
			theQuery = currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName",Customer.class);
			theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%" );
		}
		
		List<Customer> theCustomers = theQuery.getResultList();
		
		return theCustomers;
	
	}

}
