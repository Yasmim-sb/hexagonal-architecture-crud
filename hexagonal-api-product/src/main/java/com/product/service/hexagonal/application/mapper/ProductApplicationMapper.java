package com.product.service.hexagonal.application.mapper;

import com.product.service.hexagonal.api.rest.dto.ProductRequest;
import com.product.service.hexagonal.api.rest.dto.ProductResponseDTO;
import com.product.service.hexagonal.domain.vo.ProductVO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = false))
public interface ProductApplicationMapper {

    ProductVO toProductVO(ProductRequest productRequest);

    ProductResponseDTO toProductResponseDTO(ProductVO productVO);

    List<ProductResponseDTO> toProductResponseDTOList(List<ProductVO> productVOList);
}
