package com.product.service.hexagonal.persistence.repository.jpa;

import com.product.service.hexagonal.persistence.entity.jpa.ProductPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ProductJpaRepository")
public interface ProductJpaRepository extends JpaRepository<ProductPersistenceEntity, String> {

}
