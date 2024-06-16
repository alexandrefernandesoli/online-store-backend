package br.ufms.facom.onlinestorebackend.controllers;

import br.ufms.facom.onlinestorebackend.dtos.UserRegistrationRequestDTO;
import br.ufms.facom.onlinestorebackend.models.User;
import br.ufms.facom.onlinestorebackend.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {
    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody UserRegistrationRequestDTO request) {
        userService.registerUser(request);
        return ResponseEntity.ok("{ \"message\": \"User registered successfully\" }");
    }

    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(userService.getUsers(page, size));
    }
}
