package com.abc.order.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsDetailsDTO {

    private double itemTotal;
    private int quantity;
    private String productName;
    private double productPrice;
    private LocalDate mfd;
    private String category;
}

