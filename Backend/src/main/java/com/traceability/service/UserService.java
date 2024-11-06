package com.traceability.service;

import com.traceability.dto.UserDTO;
import com.traceability.exception.ResourceNotFoundException;
import com.traceability.model.User;
import com.traceability.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(UserDTO userDTO) {
        User user = new User();
        updateUserFromDTO(user, userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(String id, UserDTO userDTO) {
        User existingUser = userRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // Update basic information
        existingUser.setCedula(userDTO.getCedula());
        existingUser.setNombreCompleto(userDTO.getNombreCompleto());
        existingUser.setCodigoTrazabilidad(userDTO.getCodigoTrazabilidad());
        existingUser.setMunicipio(userDTO.getMunicipio());
        existingUser.setTelefono(userDTO.getTelefono());
        existingUser.setUsuario(userDTO.getUsuario());
        existingUser.setRole(userDTO.getRole());

        // Only update password if it's provided and not empty
        if (userDTO.getPassword() != null && !userDTO.getPassword().trim().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    public void deleteUser(String id) {
        Long userId = Long.parseLong(id);
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    private void updateUserFromDTO(User user, UserDTO dto) {
        user.setCedula(dto.getCedula());
        user.setNombreCompleto(dto.getNombreCompleto());
        user.setCodigoTrazabilidad(dto.getCodigoTrazabilidad());
        user.setMunicipio(dto.getMunicipio());
        user.setTelefono(dto.getTelefono());
        user.setUsuario(dto.getUsuario());
        user.setRole(dto.getRole());
    }
}