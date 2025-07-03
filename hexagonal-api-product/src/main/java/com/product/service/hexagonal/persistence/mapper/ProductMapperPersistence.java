package com.product.service.hexagonal.persistence.mapper;

import com.product.service.hexagonal.domain.entity.ProductEntity;
import com.product.service.hexagonal.persistence.entity.jpa.ProductPersistenceEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = false))
public interface ProductMapperPersistence {

    ProductMapperPersistence INSTANCE = Mappers.getMapper(ProductMapperPersistence.class);

    ProductPersistenceEntity toProductPersistenceEntity(ProductEntity productEntity);

    ProductEntity toProductDomainEntity(ProductPersistenceEntity productPersistenceEntities);

    List<ProductEntity> toProductDomainEntityList(List<ProductPersistenceEntity> productPersistenceEntities);
}
