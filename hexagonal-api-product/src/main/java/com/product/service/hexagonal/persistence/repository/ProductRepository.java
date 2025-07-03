package com.product.service.hexagonal.persistence.repository;

import com.product.service.hexagonal.domain.entity.ProductEntity;
import com.product.service.hexagonal.persistence.entity.jpa.ProductPersistenceEntity;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    void saveProduct(ProductPersistenceEntity productPersistenceEntity);

    List<ProductEntity> findAll();

    ProductEntity findById(String id);

    ProductEntity updateProduct(String id, ProductPersistenceEntity productPersistence);

    void deleteProduct(ProductEntity productPersistence);
}
