package com.product.service.hexagonal.api.rest.assembler;

import com.product.service.hexagonal.api.rest.dto.ProductResponseDTO;
import com.product.service.hexagonal.api.rest.dto.ResponseDTO;
import com.product.service.hexagonal.application.mapper.ProductApplicationMapper;
import com.product.service.hexagonal.domain.vo.ProductVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductAssembler {

    private final ProductApplicationMapper productApplicationMapper;

    public List<ProductResponseDTO> toCollectionModel(List<ProductVO> productVOList) {
        log.info("Converting ProductVO list to ProductResponseDTO list");

        var productResponseDTOList = productApplicationMapper.toProductResponseDTOList(productVOList);

        log.info("Converted ProductVO list: {}", productResponseDTOList);

        return productResponseDTOList;
    }

    public ProductResponseDTO toProductVO(ProductVO productVO) {
        log.info("Converting ProductVO to ProductResponseDTO");

        var productResponseDTO = productApplicationMapper.toProductResponseDTO(productVO);

        log.info("Converted ProductVO : {}", productResponseDTO);

        return productResponseDTO;
    }

    public ResponseDTO toModel(ProductResponseDTO productResponseDTO, String message) {
        return ResponseDTO.builder()
                .data(productResponseDTO)
                .message(message)
                .build();
    }

}
