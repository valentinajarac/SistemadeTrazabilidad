package com.fruitsales.mapper;

import com.fruitsales.dto.UserDTO;
import com.fruitsales.dto.UserResponseDTO;
import com.fruitsales.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    
    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setCedula(userDTO.getCedula());
        user.setNombreCompleto(userDTO.getNombreCompleto());
        user.setCodigoTrazabilidad(userDTO.getCodigoTrazabilidad());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    public UserResponseDTO toDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setCedula(user.getCedula());
        dto.setNombreCompleto(user.getNombreCompleto());
        dto.setCodigoTrazabilidad(user.getCodigoTrazabilidad());
        dto.setUsername(user.getUsername());
        return dto;
    }
}