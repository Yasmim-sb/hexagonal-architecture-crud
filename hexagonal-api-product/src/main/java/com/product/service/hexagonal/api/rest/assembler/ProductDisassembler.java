package com.product.service.hexagonal.api.rest.assembler;

import com.product.service.hexagonal.api.rest.dto.ProductRequest;
import com.product.service.hexagonal.application.mapper.ProductApplicationMapper;
import com.product.service.hexagonal.domain.vo.ProductVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductDisassembler {

    private final ProductApplicationMapper productApplicationMapper;

    public ProductVO toDomainObject(ProductRequest productRequest){
        log.info("Converting to model");

         var productVO = productApplicationMapper.toProductVO(productRequest);

         log.info("ProductVO: {}", productVO);

        return productVO;
    }
}
