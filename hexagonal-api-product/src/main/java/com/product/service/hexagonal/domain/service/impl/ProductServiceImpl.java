package com.product.service.hexagonal.domain.service.impl;

import com.product.service.hexagonal.domain.mapper.ProductDomainMapper;
import com.product.service.hexagonal.domain.service.ProductService;
import com.product.service.hexagonal.domain.vo.ProductVO;
import com.product.service.hexagonal.persistence.mapper.ProductMapperPersistence;
import com.product.service.hexagonal.persistence.repository.ProductRepository;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductMapperPersistence productMapperPersistence;
    private final ProductDomainMapper productDomainMapper;
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductDomainMapper productDomainMapper,
                              ProductMapperPersistence productMapperPersistence,
                              ProductRepository productRepository) {
        this.productDomainMapper = productDomainMapper;
        this.productMapperPersistence = productMapperPersistence;
        this.productRepository = productRepository;
    }

    @Override
    public void createProduct(ProductVO productVO) {

        var productEntity = this.productDomainMapper.toProductEntity(productVO);

        var productPersistence = this.productMapperPersistence.toProductPersistenceEntity(productEntity);

        this.productRepository.saveProduct(productPersistence);
    }

    @Override
    public List<ProductVO> listProducts() {
        var productPersistenceList = this.productRepository.findAll();

        return this.productDomainMapper.toProductVOList(productPersistenceList);
    }

    @Override
    public ProductVO updateProduct(String id, ProductVO productVO) {

        var productEntity = this.productDomainMapper.toProductEntity(productVO);

        var productPersistence = this.productMapperPersistence.toProductPersistenceEntity(productEntity);

        var product = this.productRepository.updateProduct(id, productPersistence);

        return this.productDomainMapper.toProductVO(product);
    }

    @Override
    public ProductVO getProductById(String id) {

        var productPersistence = this.productRepository.findById(id);

        return this.productDomainMapper.toProductVO(productPersistence);
    }

    @Override
    public void deleteProduct(String id) {
        var productPersistence = this.productRepository.findById(id);

        this.productRepository.deleteProduct(productPersistence);
    }
}
