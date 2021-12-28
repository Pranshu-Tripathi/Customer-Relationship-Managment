package com.pranshu.crm.springdemo.service;

import java.util.List;

import com.pranshu.crm.springdemo.entity.Customer;

public interface CustomerService {

	public List<Customer> getCustomers(int sortId);
	
	public void saveCustomer(Customer theCustomer);
	
	public Customer getCustomer(int theId);
	
	public void deleteCustomer(int theId);

	public List<Customer> searchCustomers(String theSearchName);
	
}
