package br.ufms.facom.onlinestorebackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequestDTO {
    private String name;
    private String username;
    private String password;
}
