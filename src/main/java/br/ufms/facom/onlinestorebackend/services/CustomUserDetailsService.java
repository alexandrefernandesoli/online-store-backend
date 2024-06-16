package br.ufms.facom.onlinestorebackend.services;

import br.ufms.facom.onlinestorebackend.models.Client;
import br.ufms.facom.onlinestorebackend.repositories.ClientRepository;
import br.ufms.facom.onlinestorebackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import br.ufms.facom.onlinestorebackend.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    public CustomUserDetailsService(ClientRepository clientRepository, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Client client = clientRepository.findByEmail(usernameOrEmail);
        if (client != null) {
            return new org.springframework.security.core.userdetails.User(client.getEmail(), client.getPassword(), getAuthority("CLIENT"));
        }

        User admin = userRepository.findByUsername(usernameOrEmail);
        if (admin != null) {
            return new org.springframework.security.core.userdetails.User(admin.getUsername(), admin.getPassword(), getAuthority("ADMIN"));
        }

        throw new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail);
    }

    private List<GrantedAuthority> getAuthority(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }
}
