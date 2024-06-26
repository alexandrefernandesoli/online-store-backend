package br.ufms.facom.onlinestorebackend.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name = "Client Registration Request DTO", description = "Client registration request data transfer object")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientRegistrationRequestDTO {
    private String firstName;
    private String lastName;

    private String email;
    private String password;
}
