package com.product.service.hexagonal.domain.service.impl;

import com.product.service.hexagonal.domain.entity.ProductEntity;
import com.product.service.hexagonal.domain.mapper.ProductDomainMapper;
import com.product.service.hexagonal.domain.vo.ProductVO;
import com.product.service.hexagonal.persistence.entity.jpa.ProductPersistenceEntity;
import com.product.service.hexagonal.persistence.mapper.ProductMapperPersistence;
import com.product.service.hexagonal.testadapter.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductServiceIntegrationTest {

    private ProductServiceImpl productService;
    private InMemoryProductRepository fakeRepository;
    private ProductDomainMapper productDomainMapper;

    private ProductMapperPersistence productMapperPersistence;

    @BeforeEach
    void setup() {
        fakeRepository = new InMemoryProductRepository();

        productDomainMapper = ProductDomainMapper.INSTANCE;

        productMapperPersistence = new ProductMapperPersistence() {
            @Override
            public ProductPersistenceEntity toProductPersistenceEntity(ProductEntity productEntity) {
                return new ProductPersistenceEntity(
                        productEntity.getId(),
                        productEntity.getName(),
                        productEntity.getDescription(),
                        productEntity.getPrice(),
                        productEntity.getCreatedAt(),
                        productEntity.getUpdatedAt()
                );
            }

            @Override
            public ProductEntity toProductDomainEntity(ProductPersistenceEntity persistenceEntity) {
                return new ProductEntity(
                        persistenceEntity.getId(),
                        persistenceEntity.getName(),
                        persistenceEntity.getDescription(),
                        persistenceEntity.getPrice(),
                        persistenceEntity.getCreatedAt(),
                        persistenceEntity.getUpdatedAt()
                );
            }

            @Override
            public List<ProductEntity> toProductDomainEntityList(List<ProductPersistenceEntity> persistenceEntities) {
                return persistenceEntities.stream()
                        .map(this::toProductDomainEntity)
                        .toList();
            }
        };
        productService = new ProductServiceImpl(productDomainMapper, productMapperPersistence, fakeRepository);
    }

    @Test
    void shouldCreateAndRetrieveProduct() {
        var productVO = ProductVO.builder()
                .id("1")
                .name("Mouse")
                .description("Mouse óptico")
                .price(new BigDecimal("49.90"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        productService.createProduct(productVO);

        var retrieved = productService.getProductById("1");

        assertNotNull(retrieved);
        assertEquals("Mouse", retrieved.getName());
        assertEquals("Mouse óptico", retrieved.getDescription());
    }

    @Test
    void shouldListAllProducts() {
        productService.createProduct(ProductVO.builder()
                .id("1")
                .name("Mouse")
                .description("Mouse óptico")
                .price(new BigDecimal("49.90"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());

        productService.createProduct(ProductVO.builder()
                .id("2")
                .name("Teclado")
                .description("Teclado mecânico")
                .price(new BigDecimal("199.99"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());

        List<ProductVO> allProducts = productService.listProducts();

        assertEquals(2, allProducts.size());
    }
}
