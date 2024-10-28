package com.fruitsales.repository;

import com.fruitsales.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByCedula(String cedula);
    boolean existsByUsername(String username);
    boolean existsByCodigoTrazabilidad(String codigoTrazabilidad);
    Optional<User> findByUsername(String username);
}