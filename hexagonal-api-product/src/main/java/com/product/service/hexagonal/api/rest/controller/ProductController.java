package com.product.service.hexagonal.api.rest.controller;

import com.product.service.hexagonal.api.rest.dto.ProductRequest;
import com.product.service.hexagonal.api.rest.dto.ProductResponseDTO;
import com.product.service.hexagonal.api.rest.dto.ResponseDTO;
import com.product.service.hexagonal.application.facade.ProductFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.product.service.hexagonal.api.rest.UrlConstant.URL_BASE;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(URL_BASE)
public class ProductController {

    private final ProductFacade productFacade;

    @PostMapping
    public ResponseEntity<Map<String, String>> createProduct(@RequestBody ProductRequest productRequest) {
        log.info("Creating product with request: {}", productRequest);

        this.productFacade.createProduct(productRequest);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Produto criado com sucesso!");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> listProducts(){
        log.info("Listing all products");

        List<ProductResponseDTO> productResponseDTOList = this.productFacade.listProducts();

        log.info("Products found: {}", productResponseDTOList);
        return ResponseEntity.ok(productResponseDTOList);
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getProductById(@PathVariable String id) {
        log.info("Fetching product with ID: {}", id);

        return this.productFacade.getProductById(id);
    }

    @PostMapping("/{id}")
    public ResponseDTO updateProduct(@PathVariable String id,
                                     @RequestBody ProductRequest productRequest) {
        log.info("Updating product with request: {}", productRequest);

       return this.productFacade.updateProduct(id, productRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable String id) {
        log.info("Deleting product with ID: {}", id);

        this.productFacade.deleteProduct(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Produto deletado com sucesso!");

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
