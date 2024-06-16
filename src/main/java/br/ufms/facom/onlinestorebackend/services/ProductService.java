package br.ufms.facom.onlinestorebackend.services;

import br.ufms.facom.onlinestorebackend.dtos.ProductResponseDTO;
import br.ufms.facom.onlinestorebackend.models.Product;
import br.ufms.facom.onlinestorebackend.repositories.ProductRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductResponseDTO> getProducts(int page, int limit, String sort, String order) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sort));
        return productRepository.findAll(pageable).map(this::fromProductToProductResponseDTO);
    }

    public ProductResponseDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::fromProductToProductResponseDTO)
                .orElseThrow(() -> new ObjectNotFoundException(id, "Product not found: " + id));
    }

    private ProductResponseDTO fromProductToProductResponseDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setSale(product.getSale());
        dto.setImageURL(product.getImageURL());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        return dto;
    }

    public List<ProductResponseDTO> listProductsPublic() {
        return productRepository.findAll(PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "createdAt")))
                .map(this::fromProductToProductResponseDTO).getContent();
    }

    public List<ProductResponseDTO> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query)
                .stream()
                .map(this::fromProductToProductResponseDTO)
                .toList();
    }
}
