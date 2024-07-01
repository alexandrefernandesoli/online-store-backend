package br.ufms.facom.onlinestorebackend.controllers;

import br.ufms.facom.onlinestorebackend.dtos.ProductResponseDTO;
import br.ufms.facom.onlinestorebackend.services.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/public/products")
public class PublicProductsController {
    @Value("${file.upload-dir}")
    private String uploadDir;

    private final ProductService productService;

    public PublicProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> listProductsPublic() {
        List<ProductResponseDTO> products = productService.listProductsPublic();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> get(@PathVariable("id") Long id) {
        ProductResponseDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    // Search product by name or description
    @GetMapping("/search/{query}")
    public ResponseEntity<List<ProductResponseDTO>> search(@PathVariable("query") String query) {
        List<ProductResponseDTO> products = productService.searchProducts(query);
        return ResponseEntity.ok(products);
    }
}
