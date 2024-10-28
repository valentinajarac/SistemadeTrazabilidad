package com.fruitsales.service;

import com.fruitsales.dto.UserDTO;
import com.fruitsales.dto.UserResponseDTO;
import com.fruitsales.exception.CustomException;
import com.fruitsales.mapper.UserMapper;
import com.fruitsales.model.User;
import com.fruitsales.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public UserResponseDTO createUser(UserDTO userDTO) {
        validateNewUser(userDTO);
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toDTO(userRepository.save(user));
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("Usuario no encontrado", HttpStatus.NOT_FOUND));
        return userMapper.toDTO(user);
    }

    public UserResponseDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("Usuario no encontrado", HttpStatus.NOT_FOUND));
        
        validateUpdateUser(id, userDTO, existingUser);
        
        User updatedUser = userMapper.toEntity(userDTO);
        updatedUser.setId(id);
        
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            updatedUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        } else {
            updatedUser.setPassword(existingUser.getPassword());
        }
        
        return userMapper.toDTO(userRepository.save(updatedUser));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new CustomException("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    private void validateNewUser(UserDTO userDTO) {
        if (userRepository.existsByCedula(userDTO.getCedula())) {
            throw new CustomException("Ya existe un usuario con esta cédula", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new CustomException("El nombre de usuario ya está en uso", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByCodigoTrazabilidad(userDTO.getCodigoTrazabilidad())) {
            throw new CustomException("El código de trazabilidad ya está en uso", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateUpdateUser(Long id, UserDTO userDTO, User existingUser) {
        if (!existingUser.getCedula().equals(userDTO.getCedula()) && 
            userRepository.existsByCedula(userDTO.getCedula())) {
            throw new CustomException("Ya existe un usuario con esta cédula", HttpStatus.BAD_REQUEST);
        }
        if (!existingUser.getUsername().equals(userDTO.getUsername()) && 
            userRepository.existsByUsername(userDTO.getUsername())) {
            throw new CustomException("El nombre de usuario ya está en uso", HttpStatus.BAD_REQUEST);
        }
        if (!existingUser.getCodigoTrazabilidad().equals(userDTO.getCodigoTrazabilidad()) && 
            userRepository.existsByCodigoTrazabilidad(userDTO.getCodigoTrazabilidad())) {
            throw new CustomException("El código de trazabilidad ya está en uso", HttpStatus.BAD_REQUEST);
        }
    }
}