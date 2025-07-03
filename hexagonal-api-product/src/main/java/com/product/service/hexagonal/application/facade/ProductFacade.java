package com.product.service.hexagonal.application.facade;

import com.product.service.hexagonal.api.rest.assembler.ProductAssembler;
import com.product.service.hexagonal.api.rest.assembler.ProductDisassembler;
import com.product.service.hexagonal.api.rest.dto.ProductRequest;
import com.product.service.hexagonal.api.rest.dto.ProductResponseDTO;
import com.product.service.hexagonal.api.rest.dto.ResponseDTO;
import com.product.service.hexagonal.application.mapper.ProductApplicationMapper;
import com.product.service.hexagonal.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductFacade {

    private final ProductService productService;
    private final ProductAssembler productAssembler;
    private final ProductDisassembler productDisassembler;
    private final ProductApplicationMapper productApplicationMapper;

    public void createProduct(ProductRequest productRequest) {
        log.info("Creating product with request: {}", productRequest);

        var productVO = this.productDisassembler.toDomainObject(productRequest);
        log.info("Converting ProductRequest to model: {}", productVO);

        this.productService.createProduct(productVO);
    }

    public List<ProductResponseDTO> listProducts() {
        log.info("Listing all products");

        var productVOList = this.productService.listProducts();
        log.info("Products found: {}", productVOList);

        var productResponseDTOList = this.productAssembler.toCollectionModel(productVOList);
        log.info("Converted ProductVO list to ProductResponseDTO list: {}", productResponseDTOList);

        return productResponseDTOList;
    }

    public ResponseDTO updateProduct(String id, ProductRequest productRequest) {
    log.info("Updating product with request: {}", productRequest);

        log.info("Updating product with request: {}", productRequest);

        var productVO = this.productDisassembler.toDomainObject(productRequest);
        log.info("Converting ProductRequest to model: {}", productVO);

        var resultVO = this.productService.updateProduct(id, productVO);

        log.info("Converted ProductRequest to model and updated product: {}", resultVO);
        var productResponseDTO = productApplicationMapper.toProductResponseDTO(resultVO);

        return productAssembler.toModel(productResponseDTO, "Produto atualizado com sucesso!");
    }

    public ProductResponseDTO getProductById(String id) {
    log.info("Fetching product with ID: {}", id);

        var productVO = this.productService.getProductById(id);
        log.info("Product found: {}", productVO);

        var productResponseDTO = this.productAssembler.toProductVO(productVO);
        log.info("Converted ProductVO to ProductResponseDTO: {}", productResponseDTO);

        return productResponseDTO;
    }

    public void deleteProduct(String id) {
    log.info("Deleting product with ID: {}", id);

        this.productService.deleteProduct(id);
        log.info("Product with ID {} deleted successfully", id);
    }
}
