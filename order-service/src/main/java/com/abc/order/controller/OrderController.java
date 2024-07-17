package com.abc.order.controller;

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

import com.abc.order.dto.OrderDTO;
import com.abc.order.dto.OrderInvoiceDTO;
import com.abc.order.dto.OrderInvoiceDTO;
import com.abc.order.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping("/")
	public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
		
		orderService.saveOrder(orderDTO);
		
		return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderInvoiceDTO> fetchOrderInvoice(@PathVariable long orderId) {
		
		OrderInvoiceDTO orderInvoiceDTO = orderService.findOrderById(orderId);
		
		return new ResponseEntity<>(orderInvoiceDTO, HttpStatus.OK);
	}
	
//	@GetMapping("/{orderId}")
//	public ResponseEntity<OrderDTO> fetchOrderDetails(@PathVariable long orderId) {
//		
//		OrderDTO orderDTO = orderService.findOrderById(orderId);
//		
//		return new ResponseEntity<>(orderDTO, HttpStatus.OK);
//	}
	
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<Set<OrderDTO>> fetchCustomerOrders(@PathVariable long customerId) {
		
		Set<OrderDTO> orderDTOs = orderService.findAllOrdersByCustomer(customerId);
		
		return new ResponseEntity<>(orderDTOs, HttpStatus.OK);
	}
}
