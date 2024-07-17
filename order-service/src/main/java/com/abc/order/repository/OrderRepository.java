package com.abc.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abc.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Long>{

	@Query("select o from Order o where o.customerId = :cid")
	List<Order> findOrderByCustomerId(@Param("cid") long customerId);
}
