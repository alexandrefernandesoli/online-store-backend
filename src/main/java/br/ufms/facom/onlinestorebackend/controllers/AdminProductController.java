package br.ufms.facom.onlinestorebackend.controllers;

import br.ufms.facom.onlinestorebackend.dtos.ProductResponseDTO;
import br.ufms.facom.onlinestorebackend.models.Product;
import br.ufms.facom.onlinestorebackend.dtos.ProductRequestDTO;
import br.ufms.facom.onlinestorebackend.repositories.ProductRepository;
import br.ufms.facom.onlinestorebackend.dtos.PaginatedResponseDTO;
import br.ufms.facom.onlinestorebackend.services.ProductService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

// change doc title on swaggerui
@Tag(name = "Admin Products")
@RestController
@RequestMapping("/admin/products")
public class AdminProductController {
    @Value("${file.upload-dir}")
    private String uploadDir;

    private final ProductService productService;
    private final ProductRepository repository;

    // Define allowed image magic numbers
    private static final List<byte[]> IMAGE_MAGIC_NUMBERS = Arrays.asList(
            // JPEG magic numbers
            new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE0},
            new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE1},
            // PNG magic number
            new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47}
    );

    public AdminProductController(ProductRepository repository, ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    @GetMapping
    public PaginatedResponseDTO<ProductResponseDTO> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String order) {
        Page<ProductResponseDTO> productPage = productService.getProducts(page, limit, sort, order);
        PaginatedResponseDTO<ProductResponseDTO> response = new PaginatedResponseDTO<>();
        response.setTotal(productPage.getTotalElements());
        response.setPage(page);
        response.setResults(productPage.getContent());
        response.setPages(productPage.getTotalPages());
        response.setLimit(limit);

        return response;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ProductRequestDTO productRequestDTO, BindingResult bindingResult) {
        ResponseEntity<?> errors = getResponseEntity(bindingResult);
        if (errors != null) return errors;

        Product product = new Product().fromDTO(productRequestDTO);

        Product returnProduct = repository.save(product);
        return ResponseEntity.ok(returnProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid ProductRequestDTO productRequestDTO, BindingResult bindingResult) {
        ResponseEntity<?> errors = getResponseEntity(bindingResult);
        if (errors != null) return errors;

        Product product = repository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        product.fromDTO(productRequestDTO);

        Product returnProduct = repository.save(product);
        return ResponseEntity.ok(returnProduct);
    }

    private ResponseEntity<?> getResponseEntity(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            System.out.println("Errors: " + errors);

            return ResponseEntity.badRequest().body(errors);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("Product deleted successfully.");
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        Map<String, String> result = new HashMap<>();

        if (file.isEmpty()) {
            result.put("error", "Please select a file to upload.");
            return ResponseEntity.badRequest().body(result);
        }

        if (!isImageFile(file)) {
            result.put("error", "Only JPG, JPEG, and PNG image files are allowed.");
            return ResponseEntity.badRequest().body(result);
        }

        String originalFileName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "somefile.jpg";
        String fileName = UUID.randomUUID() + originalFileName.substring(originalFileName.lastIndexOf("."));

        File targetFile = new File(uploadDir + File.separator + fileName);
        file.transferTo(targetFile);

        result.put("url", fileName);
        return ResponseEntity.ok(result);
    }

    private boolean isImageFile(MultipartFile file) {
        try {
            // Read the first bytes of the file
            byte[] fileBytes = file.getBytes();
            // Check if the magic number matches any of the image magic numbers
            for (byte[] magicNumber : IMAGE_MAGIC_NUMBERS) {
                if (startsWith(fileBytes, magicNumber)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean startsWith(byte[] data, byte[] prefix) {
        if (data.length < prefix.length) {
            return false;
        }
        for (int i = 0; i < prefix.length; i++) {
            if (data[i] != prefix[i]) {
                return false;
            }
        }
        return true;
    }
}
