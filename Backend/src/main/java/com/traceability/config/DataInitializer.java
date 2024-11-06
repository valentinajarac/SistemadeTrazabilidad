package com.traceability.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.traceability.model.Role;
import com.traceability.model.User;
import com.traceability.repository.UserRepository;

@Slf4j
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            try {
                // Check if admin user exists
                if (!userRepository.existsByUsuario("admin")) {
                    log.info("Initializing database with admin user...");
                    User admin = new User();
                    admin.setCedula(1234567890);
                    admin.setNombreCompleto("Administrador");
                    admin.setCodigoTrazabilidad(1000);
                    admin.setMunicipio("Bogotá");
                    admin.setTelefono("3001234567");
                    admin.setUsuario("admin");
                    admin.setPassword(passwordEncoder.encode("admin123"));
                    admin.setRole(Role.ADMIN);
                    userRepository.save(admin);
                    log.info("Admin user created successfully with username: admin");
                } else {
                    log.info("Admin user already exists, skipping creation");
                }
            } catch (Exception e) {
                log.error("Error initializing database: ", e);
                throw e;
            }
        };
    }
}