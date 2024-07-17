package com.abc.order.dto;

import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderInvoiceDTO {

	private long id;
	private double orderAmount;
	private LocalDate orderDate;
	private String orderStatus;
	private String customerName;
	private String email;
	private LocalDate dob;
	private String mobile;
	private String city;
	private Set<OrderItemsDetailsDTO> orderItems;

}
