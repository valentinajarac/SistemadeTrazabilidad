package com.traceability.service;

import com.traceability.dto.FarmDTO;
import com.traceability.exception.ResourceNotFoundException;
import com.traceability.exception.UnauthorizedException;
import com.traceability.model.Farm;
import com.traceability.model.Role;
import com.traceability.model.User;
import com.traceability.repository.FarmRepository;
import com.traceability.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FarmService {

    private final FarmRepository farmRepository;
    private final UserRepository userRepository;

    @Transactional
    public Farm createFarm(FarmDTO farmDTO, UserDetails userDetails) {
        log.debug("Creating farm for user: {}", userDetails.getUsername());
        User user = getUserFromUserDetails(userDetails);

        Farm farm = new Farm();
        farm.setNombre(farmDTO.getNombre());
        farm.setHectareas(farmDTO.getHectareas());
        farm.setMunicipio(farmDTO.getMunicipio());
        farm.setProductor(user);

        Farm savedFarm = farmRepository.save(farm);
        log.debug("Farm created with ID: {}", savedFarm.getId());
        return savedFarm;
    }

    public List<Farm> getFarms(UserDetails userDetails) {
        log.debug("Getting farms for user: {}", userDetails.getUsername());
        User user = getUserFromUserDetails(userDetails);
        List<Farm> farms;

        if (user.getRole() == Role.ADMIN) {
            farms = farmRepository.findAll();
            log.debug("Admin user retrieved all farms. Count: {}", farms.size());
        } else {
            farms = farmRepository.findByProductorId(user.getId());
            log.debug("Producer retrieved their farms. Count: {}", farms.size());
        }
        return farms;
    }

    @Transactional
    public Farm updateFarm(Long id, FarmDTO farmDTO, UserDetails userDetails) {
        log.debug("Updating farm with ID: {}", id);
        Farm farm = farmRepository.findByIdWithProductor(id)
                .orElseThrow(() -> new ResourceNotFoundException("Finca no encontrada con id: " + id));

        User user = getUserFromUserDetails(userDetails);
        if (user.getRole() != Role.ADMIN && !farm.getProductor().getId().equals(user.getId())) {
            throw new UnauthorizedException("No autorizado para actualizar esta finca");
        }

        farm.setNombre(farmDTO.getNombre());
        farm.setHectareas(farmDTO.getHectareas());
        farm.setMunicipio(farmDTO.getMunicipio());

        Farm updatedFarm = farmRepository.save(farm);
        log.debug("Farm updated successfully");
        return updatedFarm;
    }

    @Transactional
    public void deleteFarm(Long id, UserDetails userDetails) {
        log.debug("Deleting farm with ID: {}", id);
        Farm farm = farmRepository.findByIdWithProductor(id)
                .orElseThrow(() -> new ResourceNotFoundException("Finca no encontrada con id: " + id));

        User user = getUserFromUserDetails(userDetails);
        if (user.getRole() != Role.ADMIN && !farm.getProductor().getId().equals(user.getId())) {
            throw new UnauthorizedException("No autorizado para eliminar esta finca");
        }

        farmRepository.delete(farm);
        log.debug("Farm deleted successfully");
    }

    private User getUserFromUserDetails(UserDetails userDetails) {
        return userRepository.findByUsuario(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }
}
