package com.traceability.service;

import com.traceability.dto.ClientDTO;
import com.traceability.exception.ResourceNotFoundException;
import com.traceability.model.Client;
import com.traceability.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client createClient(ClientDTO clientDTO) {
        Client client = new Client();
        updateClientFromDTO(client, clientDTO);
        return clientRepository.save(client);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client updateClient(String id, ClientDTO clientDTO) {
        Client client = clientRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        updateClientFromDTO(client, clientDTO);
        return clientRepository.save(client);
    }

    public void deleteClient(String id) {
        if (!clientRepository.existsById(Long.parseLong(id))) {
            throw new ResourceNotFoundException("Client not found");
        }
        clientRepository.deleteById(Long.parseLong(id));
    }

    private void updateClientFromDTO(Client client, ClientDTO dto) {
        client.setNit(dto.getNit());
        client.setNombre(dto.getNombre());
        client.setFloid(dto.getFloid());
        client.setDireccion(dto.getDireccion());
        client.setTelefono(dto.getTelefono());
        client.setEmail(dto.getEmail());
    }
}