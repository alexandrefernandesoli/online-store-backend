package br.ufms.facom.onlinestorebackend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    private String usernameOrEmail;
    private String password;
}