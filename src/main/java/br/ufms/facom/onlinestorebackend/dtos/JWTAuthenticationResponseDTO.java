package br.ufms.facom.onlinestorebackend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTAuthenticationResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer";

    public JWTAuthenticationResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}