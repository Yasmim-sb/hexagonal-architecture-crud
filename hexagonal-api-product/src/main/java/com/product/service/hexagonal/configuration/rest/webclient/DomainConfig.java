package com.product.service.hexagonal.configuration.rest.webclient;

import com.product.service.hexagonal.domain.mapper.ProductDomainMapper;
import com.product.service.hexagonal.domain.service.impl.ProductServiceImpl;
import com.product.service.hexagonal.persistence.mapper.ProductMapperPersistence;
import com.product.service.hexagonal.persistence.repository.ProductRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public ProductServiceImpl productServiceImpl(ProductDomainMapper productDomainMapper,
                                                 ProductMapperPersistence productMapperPersistence,
                                                 ProductRepository productRepository) {
        return new ProductServiceImpl(productDomainMapper, productMapperPersistence, productRepository);
    }

    @Bean
    public ProductDomainMapper productDomainMapper() {
        return Mappers.getMapper(ProductDomainMapper.class);
    }

}
