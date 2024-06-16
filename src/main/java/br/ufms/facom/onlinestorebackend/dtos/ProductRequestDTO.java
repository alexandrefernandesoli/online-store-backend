package br.ufms.facom.onlinestorebackend.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {
    @NotBlank(message = "Name is mandatory")
    @Size(min=12, max = 100, message = "Name should not exceed 100 characters")
    private String name;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 3000, message = "Description should not exceed 3000 characters")
    private String description;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be positive")
    private String price;

    @NotNull(message = "Sale is mandatory")
    @Positive(message = "Sale must be positive")
    @Min(value = 0, message = "Sale must be at least 0")
    private String sale;

    @NotBlank(message = "Image URL is mandatory")
    @Size(max = 255, message = "Image URL should not exceed 255 characters")
    private String imageURL;
}
