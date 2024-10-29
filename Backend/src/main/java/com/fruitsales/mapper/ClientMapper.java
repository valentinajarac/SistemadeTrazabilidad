package com.fruitsales.mapper;

import com.fruitsales.dto.ClientDTO;
import com.fruitsales.model.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    
    public ClientDTO toDTO(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setAddress(client.getAddress());
        dto.setPhone(client.getPhone());
        dto.setEmail(client.getEmail());
        return dto;
    }

    public Client toEntity(ClientDTO dto) {
        Client client = new Client();
        client.setId(dto.getId());
        client.setName(dto.getName());
        client.setAddress(dto.getAddress());
        client.setPhone(dto.getPhone());
        client.setEmail(dto.getEmail());
        return client;
    }
}