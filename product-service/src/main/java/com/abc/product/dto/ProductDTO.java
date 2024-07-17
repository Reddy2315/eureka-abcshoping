package com.abc.product.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

	private long id;
	private String productName;
	private double productPrice;
	private LocalDate mfd;
	private String category;
}
