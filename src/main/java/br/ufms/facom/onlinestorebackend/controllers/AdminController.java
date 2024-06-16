package br.ufms.facom.onlinestorebackend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin")
@RestController
public class AdminController {
    @GetMapping("/dashboard")
    public String dashboard() {
        return "Admin dashboard";
    }
}
