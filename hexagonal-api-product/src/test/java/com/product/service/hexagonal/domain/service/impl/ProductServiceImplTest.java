package com.product.service.hexagonal.domain.service.impl;

import com.product.service.hexagonal.domain.entity.ProductEntity;
import com.product.service.hexagonal.domain.mapper.ProductDomainMapper;
import com.product.service.hexagonal.domain.vo.ProductVO;
import com.product.service.hexagonal.dummy.ProductDummy;
import com.product.service.hexagonal.persistence.entity.jpa.ProductPersistenceEntity;
import com.product.service.hexagonal.persistence.mapper.ProductMapperPersistence;
import com.product.service.hexagonal.persistence.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductServiceImplTest {

    private ProductMapperPersistence productMapperPersistence;
    private ProductDomainMapper productDomainMapper;
    private ProductRepository productRepository;

    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productMapperPersistence = mock(ProductMapperPersistence.class);
        productDomainMapper = mock(ProductDomainMapper.class);
        productRepository = mock(ProductRepository.class);

        productService = new ProductServiceImpl(productDomainMapper, productMapperPersistence, productRepository);
    }

    @Test
    void shouldCreateProductWithSuccess() {
        var productVO = ProductDummy.productVO();

        ProductEntity productEntity = new ProductEntity();
        ProductPersistenceEntity persistenceEntity = new ProductPersistenceEntity();

        when(productDomainMapper.toProductEntity(productVO)).thenReturn(productEntity);
        when(productMapperPersistence.toProductPersistenceEntity(productEntity)).thenReturn(persistenceEntity);

        productService.createProduct(productVO);

        verify(productDomainMapper).toProductEntity(productVO);
        verify(productMapperPersistence).toProductPersistenceEntity(productEntity);
        verify(productRepository).saveProduct(persistenceEntity);
    }
    @Test
    void shouldListProductsWithSuccess() {
        List<ProductVO> productVOList = List.of(ProductDummy.productVO());
        List<ProductEntity> productEntityList = List.of(ProductDummy.productEntity());

        when(productRepository.findAll()).thenReturn(productEntityList);
        when(productDomainMapper.toProductVOList(productEntityList)).thenReturn(productVOList);

        var result = productService.listProducts();

        verify(productRepository).findAll();
        verify(productDomainMapper).toProductVOList(productEntityList);
        assertEquals(productVOList, result);
    }

    @Test
    void shouldUpdateProductWithSuccess() {
        var productVO = ProductDummy.productVO();
        var productEntity = ProductDummy.productEntity();
        var productPersistence = ProductDummy.productPersistenceEntity();

        when(productDomainMapper.toProductEntity(productVO)).thenReturn(productEntity);
        when(productMapperPersistence.toProductPersistenceEntity(productEntity)).thenReturn(productPersistence);
        when(productRepository.updateProduct(productVO.getId(), productPersistence)).thenReturn(productEntity);
        when(productDomainMapper.toProductVO(productEntity)).thenReturn(productVO);

        var result = productService.updateProduct(productVO.getId(), productVO);

        verify(productDomainMapper).toProductEntity(productVO);
        verify(productMapperPersistence).toProductPersistenceEntity(productEntity);
        verify(productRepository).updateProduct(productVO.getId(), productPersistence);
        verify(productDomainMapper).toProductVO(productEntity);
        assertEquals(productVO, result);
    }

    @Test
    void shouldGetProductByIdWithSuccess() {
        var id = "123";
        var productEntity = ProductDummy.productEntity();
        var productVO = ProductDummy.productVO();

        when(productRepository.findById(id)).thenReturn(productEntity);
        when(productDomainMapper.toProductVO(productEntity)).thenReturn(productVO);

        var result = productService.getProductById(id);

        verify(productRepository).findById(id);
        verify(productDomainMapper).toProductVO(productEntity);
        assertEquals(productVO, result);
    }

    @Test
    void shouldDeleteProductWithSuccess() {
        var id = "123";
        var productEntity = ProductDummy.productEntity();

        when(productRepository.findById(id)).thenReturn(productEntity);

        productService.deleteProduct(id);

        verify(productRepository).findById(id);
        verify(productRepository).deleteProduct(productEntity);
    }
}
