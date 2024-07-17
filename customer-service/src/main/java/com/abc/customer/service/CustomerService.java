package com.abc.customer.service;

import java.util.Set;

import com.abc.customer.dto.CustomerDTO;


public interface CustomerService {

	CustomerDTO saveCustomer(CustomerDTO customerDTO);
	
	CustomerDTO findCustomerById(long customerId);
	
	Set<CustomerDTO> findAllCustomers();
}
