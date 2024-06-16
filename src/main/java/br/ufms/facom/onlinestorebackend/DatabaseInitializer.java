package br.ufms.facom.onlinestorebackend;

import br.ufms.facom.onlinestorebackend.models.User;
import br.ufms.facom.onlinestorebackend.repositories.ProductRepository;
import br.ufms.facom.onlinestorebackend.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public DatabaseInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin") != null) {
            return;
        }

        userRepository.save(new User("admin", "admin", passwordEncoder.encode("admin")));
    }
}
