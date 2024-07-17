package com.abc.product.service;

import java.util.Set;

import com.abc.product.dto.ProductDTO;

public interface ProductService {

	ProductDTO saveProduct(ProductDTO product);
	
	ProductDTO findProductById(long productId);
	
	Set<ProductDTO> findAllProducts();
	
}
