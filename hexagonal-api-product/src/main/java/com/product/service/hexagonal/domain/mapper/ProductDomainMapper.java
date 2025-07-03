package com.product.service.hexagonal.domain.mapper;

import com.product.service.hexagonal.domain.entity.ProductEntity;
import com.product.service.hexagonal.domain.vo.ProductVO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = false))
public interface ProductDomainMapper {

    ProductDomainMapper INSTANCE = getMapper(ProductDomainMapper.class);

    ProductEntity toProductEntity(ProductVO productVO);

    ProductVO toProductVO(ProductEntity productEntity);

    List<ProductVO> toProductVOList(List<ProductEntity> productPersistenceList);
}
