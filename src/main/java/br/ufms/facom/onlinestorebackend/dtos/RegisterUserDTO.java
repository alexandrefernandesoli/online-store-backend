package br.ufms.facom.onlinestorebackend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDTO {
    private String email;

    private String password;

    private String name;
}
