package com.traceability.repository;

import com.traceability.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsuario(String usuario);
    boolean existsByUsuario(String usuario);
    boolean existsByCedula(Integer cedula);
}