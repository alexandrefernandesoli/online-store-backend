package br.ufms.facom.onlinestorebackend.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Hidden
public class HomeController {
    @RequestMapping
    @ResponseBody
    public String home() {
        return "Hello World!";
    }
}
