package com.abc.customer.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abc.customer.dto.CustomerDTO;
import com.abc.customer.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/")
	public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerDTO customerDTO) {
		
		customerDTO = customerService.saveCustomer(customerDTO);
		
		return new ResponseEntity<>(customerDTO,HttpStatus.CREATED);
	}
	
	@GetMapping("/{customerId}")
	public ResponseEntity<CustomerDTO> fetchCustomerDetails(@PathVariable long customerId) {
		
		CustomerDTO customerDTO = customerService.findCustomerById(customerId);
		
		return new ResponseEntity<>(customerDTO,HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<Set<CustomerDTO>> fetchAllCustomers() {
		
		Set<CustomerDTO> customers = customerService.findAllCustomers();
		
		return new ResponseEntity<>(customers,HttpStatus.OK);
	}
}
