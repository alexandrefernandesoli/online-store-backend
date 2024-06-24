package br.ufms.facom.onlinestorebackend.controllers;

import br.ufms.facom.onlinestorebackend.dtos.JWTAuthenticationResponseDTO;
import br.ufms.facom.onlinestorebackend.dtos.LoginRequestDTO;
import br.ufms.facom.onlinestorebackend.dtos.UserInfoDTO;
import br.ufms.facom.onlinestorebackend.models.Client;
import br.ufms.facom.onlinestorebackend.models.User;
import br.ufms.facom.onlinestorebackend.services.ClientService;
import br.ufms.facom.onlinestorebackend.services.JWTService;
import br.ufms.facom.onlinestorebackend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RequestMapping("/auth")
@RestController
public class AuthController {
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final ClientService clientService;

    public AuthController(JWTService jwtService, AuthenticationManager authenticationManager, UserService userService, ClientService clientService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.clientService = clientService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthenticationResponseDTO(jwt));
    }

    @GetMapping("/info")
    public ResponseEntity<?> getAuthInfo(Principal principal) {
        String username = principal.getName();
        Optional<User> maybeUser = userService.getUserByUsername(username);
        Optional<Client> client = Optional.empty();
        if (maybeUser.isEmpty()) {
            client = clientService.getClientByEmail(username);
        }
        
        if (maybeUser.isEmpty() && client.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        if (client.isPresent()) {
            Client c = client.get();
            UserInfoDTO userInfo = new UserInfoDTO(c.getFirstName() + " " + c.getLastName(), c.getEmail(), "CLIENT");
            return ResponseEntity.ok(userInfo);
        }

        User user = maybeUser.get();

        UserInfoDTO userInfo = new UserInfoDTO(user.getName(), user.getUsername(), user.getRole().toString());

        return ResponseEntity.ok(userInfo);
    }
}