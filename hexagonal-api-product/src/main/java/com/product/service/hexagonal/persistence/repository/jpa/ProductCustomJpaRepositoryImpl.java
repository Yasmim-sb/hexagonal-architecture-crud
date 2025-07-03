package com.product.service.hexagonal.persistence.repository.jpa;

import com.product.service.hexagonal.domain.entity.ProductEntity;
import com.product.service.hexagonal.domain.exception.NotFoundException;
import com.product.service.hexagonal.persistence.entity.jpa.ProductPersistenceEntity;
import com.product.service.hexagonal.persistence.mapper.ProductMapperPersistence;
import com.product.service.hexagonal.persistence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository("ProductCustomJpaRepositoryImpl")
@Validated
@Primary
public class ProductCustomJpaRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final ProductMapperPersistence productMapperPersistence;

    @Override
    public void saveProduct(ProductPersistenceEntity productPersistenceEntity) {
        log.info("Saving product: {}", productPersistenceEntity);

         this.productJpaRepository.save(productPersistenceEntity);

        log.info("Product saved successfully");
    }

    @Override
    public List<ProductEntity> findAll() {
        log.info("Finding all products");

        var productPersistenceEntities = this.productJpaRepository.findAll();
        log.info("Products found: {}", productPersistenceEntities);

        var productEntitiesDomain = productMapperPersistence.toProductDomainEntityList(productPersistenceEntities);

        log.info("Converted ProductPersistenceEntity list to ProductEntity list: {}", productEntitiesDomain);

        return productEntitiesDomain;
    }

    @Override
    public ProductEntity findById(String id) {
    log.info("Finding product by id: {}", id);

        var productPersistenceEntity = this.productJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
        log.info("Product found: {}", productPersistenceEntity);

        log.info("Converted ProductPersistenceEntity to ProductEntity");
        return productMapperPersistence.toProductDomainEntity(productPersistenceEntity);
    }

    @Override
    public ProductEntity updateProduct(String id, ProductPersistenceEntity productPersistence) {
        var productEntity = findById(id);

        log.info("Updating product with id: {}", id);

        if (productPersistence.getName() != null) {
            productEntity.setName(productPersistence.getName());
        }
        if (productPersistence.getPrice() != null) {
            productEntity.setPrice(productPersistence.getPrice());
        }
        if (productPersistence.getDescription() != null) {
            productEntity.setDescription(productPersistence.getDescription());
        }
        productEntity.setUpdatedAt(LocalDateTime.now());

        var updatedProductPersistence = productMapperPersistence.toProductPersistenceEntity(productEntity);
        log.info("Converted ProductEntity to ProductPersistenceEntity: {}", updatedProductPersistence);

        this.productJpaRepository.save(updatedProductPersistence);
        log.info("Product updated successfully");

        return productMapperPersistence.toProductDomainEntity(updatedProductPersistence);
    }

    @Override
    public void deleteProduct(ProductEntity productPersistence) {
        log.info("Deleting product: {}", productPersistence);

        var productPersistenceEntity = productMapperPersistence.toProductPersistenceEntity(productPersistence);
        this.productJpaRepository.delete(productPersistenceEntity);

        log.info("Product deleted successfully");
    }
}
