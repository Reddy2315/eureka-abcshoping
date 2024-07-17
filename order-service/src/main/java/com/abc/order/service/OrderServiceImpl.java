package com.abc.order.service;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.abc.order.dto.CustomerDTO;
import com.abc.order.dto.OrderDTO;
import com.abc.order.dto.OrderInvoiceDTO;
import com.abc.order.dto.OrderItemDTO;
import com.abc.order.dto.OrderItemsDetailsDTO;
import com.abc.order.dto.ProductDTO;
import com.abc.order.entity.Order;
import com.abc.order.entity.OrderItem;
import com.abc.order.exception.ResourceNotFoundException;
import com.abc.order.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public OrderDTO saveOrder(OrderDTO orderDTO) {

		Set<OrderItemDTO> orderItemDTOs = orderDTO.getOrderItems();

		double orderAmount = 0;

		for (OrderItemDTO orderItemDTO : orderItemDTOs) {

			int qty = orderItemDTO.getQuantity();
			long productId = orderItemDTO.getProductId();

			// get the product details
			ResponseEntity<ProductDTO> responseEntity = restTemplate
					.getForEntity("http://localhost:8082/product/" + productId, ProductDTO.class);

			ProductDTO productDTO = responseEntity.getBody();

			double itemTotal = qty * productDTO.getProductPrice();

			orderItemDTO.setItemTotal(itemTotal);// we need to set the item total (productPrice * qty)

			orderAmount = orderAmount + itemTotal;

		}

		orderDTO.setOrderAmount(orderAmount);
		orderDTO.setOrderDate(LocalDate.now());
		orderDTO.setOrderStatus("Success");

		//convert dto to entity
		Order order = new Order();
		order.setOrderAmount(orderDTO.getOrderAmount());
		order.setOrderDate(orderDTO.getOrderDate());
		order.setOrderStatus(orderDTO.getOrderStatus());
		order.setCustomerId(orderDTO.getCustomerId());

		Set<OrderItem> orderItems = new LinkedHashSet<>();

		for (OrderItemDTO itemDTO : orderItemDTOs) {
			OrderItem orderItem = new OrderItem();
			orderItem.setItemTotal(itemDTO.getItemTotal());
			orderItem.setProductId(itemDTO.getProductId());
			orderItem.setQuantity(itemDTO.getQuantity());
			
			orderItem.setOrder(order);

			orderItems.add(orderItem);
		}

		order.setOrderItems(orderItems);

		orderRepository.save(order);

		// convert entity to dto
		OrderDTO newOrder = modelMapper.map(order, OrderDTO.class);

		return newOrder;
	}

	@Override
	public OrderInvoiceDTO findOrderById(long orderId) {
	    Optional<Order> optionalOrder = orderRepository.findById(orderId);
	    if (optionalOrder.isEmpty()) {
	        throw new ResourceNotFoundException("Order not found");
	    }

	    Order order = optionalOrder.get();
	    OrderInvoiceDTO orderInvoiceDto = new OrderInvoiceDTO();
	    orderInvoiceDto.setId(order.getId());
	    orderInvoiceDto.setOrderAmount(order.getOrderAmount());
	    orderInvoiceDto.setOrderDate(order.getOrderDate());
	    orderInvoiceDto.setOrderStatus(order.getOrderStatus());

	    // get the customer details
	    ResponseEntity<CustomerDTO> customerResponse = restTemplate
	            .getForEntity("http://localhost:8081/customer/" + order.getCustomerId(), CustomerDTO.class);
	    CustomerDTO customerDTO = customerResponse.getBody();
	    orderInvoiceDto.setCustomerName(customerDTO.getCustomerName());
	    orderInvoiceDto.setEmail(customerDTO.getEmail());
	    orderInvoiceDto.setDob(customerDTO.getDob());
	    orderInvoiceDto.setMobile(customerDTO.getMobile());
	    orderInvoiceDto.setCity(customerDTO.getCity());
	    
	    // get the product details
	    Set<OrderItemsDetailsDTO> orderItemsDetailsDTOs = order.getOrderItems().stream().map(orderItem -> {
	        ResponseEntity<ProductDTO> responseEntity = restTemplate
	                .getForEntity("http://localhost:8082/product/" + orderItem.getProductId(), ProductDTO.class);
	        ProductDTO productDTO = responseEntity.getBody();

	        OrderItemsDetailsDTO orderItemDetailDTO = new OrderItemsDetailsDTO();
	        orderItemDetailDTO.setItemTotal(orderItem.getItemTotal());
	        orderItemDetailDTO.setQuantity(orderItem.getQuantity());
	        orderItemDetailDTO.setProductName(productDTO.getProductName());
	        orderItemDetailDTO.setProductPrice(productDTO.getProductPrice());
	        orderItemDetailDTO.setMfd(productDTO.getMfd());
	        orderItemDetailDTO.setCategory(productDTO.getCategory());

	        return orderItemDetailDTO;
	    }).collect(Collectors.toSet());

	    orderInvoiceDto.setOrderItems(orderItemsDetailsDTOs);

	    return orderInvoiceDto;
	}


	
//	@Override
//	public OrderDTO findOrderById(long orderId) {
//		
//		Optional<Order> optionalOrder = orderRepository.findById(orderId);
//		if(optionalOrder.isEmpty()) {
//			throw new ResourceNotFoundException("Order not found");
//		}
//		
//		Order order = optionalOrder.get();
//		System.out.println(order);
//		
//		return modelMapper.map(order, OrderDTO.class);
//	}

//	@Override
//	public OrderInvoiceDTO findOrderById(long orderId) {
//		
//		Optional<Order> optionalOrder = orderRepository.findById(orderId);
//		if(optionalOrder.isEmpty()) {
//			throw new ResourceNotFoundException("Order not found");
//		}
//		
//		Order order = optionalOrder.get();
//		OrderInvoiceDTO orderInvoiceDto=new OrderInvoiceDTO();
//		orderInvoiceDto.setId(order.getId());
//		orderInvoiceDto.setOrderAmount(order.getOrderAmount());
//		orderInvoiceDto.setOrderDate(order.getOrderDate());
//		orderInvoiceDto.setOrderStatus(order.getOrderStatus());
//		// get the customer details
//		ResponseEntity<CustomerDTO> customerResponse = restTemplate
//				.getForEntity("http://localhost:8081/customer/" + order.getCustomerId(), CustomerDTO.class);
//		CustomerDTO customerDTO=customerResponse.getBody();
//		System.out.println("customerDetails ->  "+customerDTO);
//		orderInvoiceDto.setCustomerName(customerDTO.getCustomerName());
//		orderInvoiceDto.setEmail(customerDTO.getEmail());
//		orderInvoiceDto.setDob(customerDTO.getDob());
//		orderInvoiceDto.setMobile(customerDTO.getMobile());
//		orderInvoiceDto.setCity(customerDTO.getCity());
//		
//		Set<OrderItem> orderItems = order.getOrderItems();
//		for(OrderItem orderItem : orderItems) {
//			// get the product details
//			ResponseEntity<ProductDTO> responseEntity = restTemplate
//					.getForEntity("http://localhost:8082/product/" + orderItem.getProductId(), ProductDTO.class);
//
//			ProductDTO productDTO = responseEntity.getBody();
//			System.out.println("productDetails ->  "+productDTO);
//			orderInvoiceDto.setItemTotal(orderItem.getItemTotal());
//			orderInvoiceDto.setQuantity(orderItem.getQuantity());
//			orderInvoiceDto.setProductName(productDTO.getProductName());
//			orderInvoiceDto.setProductPrice(productDTO.getProductPrice());
//			orderInvoiceDto.setMfd(productDTO.getMfd());
//			orderInvoiceDto.setCategory(productDTO.getCategory());
//		}
//
//		
////		OrderInvoiceDTO orderDto=modelMapper.map(order, OrderInvoiceDTO.class);
//		
//		return orderInvoiceDto;
//	}
	

	
	
	@Override
	public Set<OrderDTO> findAllOrdersByCustomer(long customerId) {
		
		List<Order> orders = orderRepository.findOrderByCustomerId(customerId);
		
		Set<OrderDTO> orderDTOs = orders.stream().map(order-> modelMapper.map(order, OrderDTO.class)).collect(Collectors.toSet());
		
		return orderDTOs;
	}

}
