package com.product.service.hexagonal.domain.service;

import com.product.service.hexagonal.domain.vo.ProductVO;

import java.util.List;

public interface ProductService {
    void createProduct(ProductVO productVO);

    List<ProductVO> listProducts();

    ProductVO updateProduct(String id, ProductVO productVO);

    ProductVO getProductById(String id);

    void deleteProduct(String id);
}
