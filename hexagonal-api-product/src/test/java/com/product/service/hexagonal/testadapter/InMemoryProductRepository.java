package com.product.service.hexagonal.testadapter;

import com.product.service.hexagonal.domain.entity.ProductEntity;
import com.product.service.hexagonal.persistence.entity.jpa.ProductPersistenceEntity;
import com.product.service.hexagonal.persistence.repository.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryProductRepository implements ProductRepository {
    private final Map<String, ProductPersistenceEntity> storage = new HashMap<>();

    @Override
    public void saveProduct(ProductPersistenceEntity product) {
        storage.put(product.getId(), product);
    }

    @Override
    public List<ProductEntity> findAll() {
        List<ProductEntity> entities = new ArrayList<>();
        for (ProductPersistenceEntity persistence : storage.values()) {
            entities.add(toDomain(persistence));
        }
        return entities;
    }

    @Override
    public ProductEntity findById(String id) {
        ProductPersistenceEntity persistence = storage.get(id);
        return persistence != null ? toDomain(persistence) : null;
    }

    @Override
    public ProductEntity updateProduct(String id, ProductPersistenceEntity product) {
        storage.put(id, product);
        return toDomain(product);
    }

    @Override
    public void deleteProduct(ProductEntity product) {
        storage.remove(product.getId());
    }

    // Conversão simples (simulando o papel do mapper)
    private ProductEntity toDomain(ProductPersistenceEntity persistence) {
        return new ProductEntity(
                persistence.getId(),
                persistence.getName(),
                persistence.getDescription(),
                persistence.getPrice(),
                persistence.getCreatedAt(),
                persistence.getUpdatedAt()
        );
    }
}
