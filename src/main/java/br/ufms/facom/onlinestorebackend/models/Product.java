package br.ufms.facom.onlinestorebackend.models;

import br.ufms.facom.onlinestorebackend.dtos.ProductRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "products")
@Entity(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class Product implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Digits(integer = 14, fraction = 2)
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal price;

    @Digits(integer = 14, fraction = 2)
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal sale;

    @Column(name = "image_url")
    private String imageURL;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt = new Date();

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderProduct> orderProducts = new HashSet<>();

    public Product(String name, String description, BigDecimal price, BigDecimal sale, String imageURL) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.sale = sale;
        this.imageURL = imageURL;
    }

    public Product fromDTO(ProductRequestDTO dto) {
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.price = new BigDecimal(dto.getPrice());
        this.sale = new BigDecimal(dto.getSale());
        this.imageURL = dto.getImageURL();

        return this;
    }
}
