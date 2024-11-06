package com.traceability.service;

import com.traceability.dto.RemissionDTO;
import com.traceability.exception.ResourceNotFoundException;
import com.traceability.exception.UnauthorizedException;
import com.traceability.model.*;
import com.traceability.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemissionService {

    private final RemissionRepository remissionRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final FarmRepository farmRepository;
    private final CropRepository cropRepository;

    @Transactional
    public Remission createRemission(RemissionDTO remissionDTO, UserDetails userDetails) {
        log.debug("Creating remission for user: {}", userDetails.getUsername());

        User user = getUserFromUserDetails(userDetails);
        Crop crop = cropRepository.findById(remissionDTO.getCultivoId())
                .orElseThrow(() -> new ResourceNotFoundException("Cultivo no encontrado"));

        validateRemissionAccess(user, crop);

        Remission remission = new Remission();
        updateRemissionFromDTO(remission, remissionDTO, user);
        remission.setProducto(crop.getProducto());
        remission.calculateTotalKilos();

        Remission savedRemission = remissionRepository.save(remission);
        log.debug("Remission created with ID: {}", savedRemission.getId());
        return savedRemission;
    }

    public List<Remission> getRemissions(UserDetails userDetails) {
        log.debug("Getting remissions for user: {}", userDetails.getUsername());
        User user = getUserFromUserDetails(userDetails);
        List<Remission> remissions;

        if (user.getRole() == Role.ADMIN) {
            remissions = remissionRepository.findAll();
            log.debug("Admin retrieved all remissions. Count: {}", remissions.size());
        } else {
            remissions = remissionRepository.findByProductorId(user.getId());
            log.debug("Producer retrieved their remissions. Count: {}", remissions.size());
        }
        return remissions;
    }

    @Transactional
    public Remission updateRemission(Long id, RemissionDTO remissionDTO, UserDetails userDetails) {
        log.debug("Updating remission with ID: {}", id);

        Remission remission = remissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Remisión no encontrada"));

        User user = getUserFromUserDetails(userDetails);
        if (user.getRole() != Role.ADMIN && !remission.getProductor().getId().equals(user.getId())) {
            throw new UnauthorizedException("No autorizado para actualizar esta remisión");
        }

        updateRemissionFromDTO(remission, remissionDTO, user);
        remission.calculateTotalKilos();

        Remission updatedRemission = remissionRepository.save(remission);
        log.debug("Remission updated successfully");
        return updatedRemission;
    }

    @Transactional
    public void deleteRemission(Long id, UserDetails userDetails) {
        log.debug("Deleting remission with ID: {}", id);

        Remission remission = remissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Remisión no encontrada"));

        User user = getUserFromUserDetails(userDetails);
        if (user.getRole() != Role.ADMIN && !remission.getProductor().getId().equals(user.getId())) {
            throw new UnauthorizedException("No autorizado para eliminar esta remisión");
        }

        remissionRepository.delete(remission);
        log.debug("Remission deleted successfully");
    }

    private User getUserFromUserDetails(UserDetails userDetails) {
        return userRepository.findByUsuario(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    private void validateRemissionAccess(User user, Crop crop) {
        if (user.getRole() != Role.ADMIN && !crop.getProductor().getId().equals(user.getId())) {
            throw new UnauthorizedException("No autorizado para crear remisión para este cultivo");
        }
    }

    private void updateRemissionFromDTO(Remission remission, RemissionDTO dto, User user) {
        remission.setFechaDespacho(dto.getFechaDespacho());
        remission.setProductor(user);
        remission.setCliente(clientRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado")));
        remission.setFinca(farmRepository.findById(dto.getFincaId())
                .orElseThrow(() -> new ResourceNotFoundException("Finca no encontrada")));
        remission.setCultivo(cropRepository.findById(dto.getCultivoId())
                .orElseThrow(() -> new ResourceNotFoundException("Cultivo no encontrado")));
        remission.setCanastillasEnviadas(dto.getCanastillasEnviadas());
        remission.setKilosPromedioPorCanastilla(dto.getKilosPromedioPorCanastilla());
    }
}
