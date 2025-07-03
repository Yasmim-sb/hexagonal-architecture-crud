package com.product.service.hexagonal.dummy;

import com.product.service.hexagonal.domain.entity.ProductEntity;
import com.product.service.hexagonal.domain.vo.ProductVO;
import com.product.service.hexagonal.persistence.entity.jpa.ProductPersistenceEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductDummy {

    public static ProductVO productVO(){
        return ProductVO.builder()
                .id("123")
                .name("Teclado")
                .description("Teclado mecânico")
                .price(new BigDecimal("199.99"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }


    public static ProductPersistenceEntity productPersistenceEntity(){
        return ProductPersistenceEntity.builder()
                .id("123")
                .name("Teclado")
                .description("Teclado mecânico")
                .price(new BigDecimal("199.99"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static ProductEntity productEntity(){
        return ProductEntity.builder()
                .id("123")
                .name("Teclado")
                .description("Teclado mecânico")
                .price(new BigDecimal("199.99"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
