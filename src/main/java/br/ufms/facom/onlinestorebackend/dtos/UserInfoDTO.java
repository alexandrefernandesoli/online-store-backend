package br.ufms.facom.onlinestorebackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoDTO {
    private String name;
    private String username;
    private String role;
}
