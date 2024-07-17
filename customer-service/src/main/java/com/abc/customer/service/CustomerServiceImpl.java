package com.abc.customer.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.customer.dto.CustomerDTO;
import com.abc.customer.entity.Customer;
import com.abc.customer.exception.ResourceNotFoundException;
import com.abc.customer.repository.CustomerRepository;


@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
		
		Customer customer = modelMapper.map(customerDTO, Customer.class);
		
		customerRepository.save(customer);
		
		customerDTO = modelMapper.map(customer, CustomerDTO.class);		
		return customerDTO;
	}

	@Override
	public CustomerDTO findCustomerById(long customerId) {

		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
		if (optionalCustomer.isEmpty()) {
			throw new ResourceNotFoundException("Customer not found with id: " + customerId);
		}
		Customer customer = optionalCustomer.get();
		
		CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
		return customerDTO;
	}

	@Override
	public Set<CustomerDTO> findAllCustomers() {
		
		List<Customer> customers = customerRepository.findAll();
		
		return customers.stream().map(customer -> modelMapper.map(customer, CustomerDTO.class)).collect(Collectors.toSet());
	}

}
