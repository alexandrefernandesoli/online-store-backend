package br.ufms.facom.onlinestorebackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
