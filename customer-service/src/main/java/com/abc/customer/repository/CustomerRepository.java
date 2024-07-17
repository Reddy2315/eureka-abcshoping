package com.abc.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abc.customer.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long>{

}
