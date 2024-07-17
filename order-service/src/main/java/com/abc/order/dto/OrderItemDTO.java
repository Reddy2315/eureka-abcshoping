package com.abc.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

	private long id;
	private double itemTotal;
	private int quantity;
	private long productId;
	
}
