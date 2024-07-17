package com.abc.order.service;

import java.util.Set;

import com.abc.order.dto.OrderDTO;
import com.abc.order.dto.OrderInvoiceDTO;

public interface OrderService {

	OrderDTO saveOrder(OrderDTO orderDTO);
	OrderInvoiceDTO findOrderById(long orderId);
//	OrderDTO findOrderById(long orderId);

	Set<OrderDTO> findAllOrdersByCustomer(long customerId);
}
