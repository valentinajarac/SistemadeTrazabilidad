package com.traceability.service;

import com.traceability.dto.CropDTO;
import com.traceability.exception.ResourceNotFoundException;
import com.traceability.exception.UnauthorizedException;
import com.traceability.model.Crop;
import com.traceability.model.Role;
import com.traceability.model.User;
import com.traceability.repository.CropRepository;
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
public class CropService {

    private final CropRepository cropRepository;
    private final UserRepository userRepository;

    @Transactional
    public Crop createCrop(CropDTO cropDTO, UserDetails userDetails) {
        log.debug("Creating new crop for user: {}", userDetails.getUsername());
        User user = getUserFromUserDetails(userDetails);
        Crop crop = new Crop();
        updateCropFromDTO(crop, cropDTO);
        crop.setProductor(user);
        Crop savedCrop = cropRepository.save(crop);
        log.debug("Crop created with ID: {}", savedCrop.getId());
        return savedCrop;
    }

    public List<Crop> getCrops(UserDetails userDetails) {
        User user = getUserFromUserDetails(userDetails);
        List<Crop> crops;
        if (user.getRole() == Role.ADMIN) {
            crops = cropRepository.findAll();
            log.debug("Admin retrieved all crops. Count: {}", crops.size());
        } else {
            crops = cropRepository.findByProductorId(user.getId());
            log.debug("Producer retrieved their crops. Count: {}", crops.size());
        }
        return crops;
    }

    @Transactional
    public Crop updateCrop(String id, CropDTO cropDTO, UserDetails userDetails) {
        log.debug("Updating crop with ID: {}", id);
        Crop existingCrop = cropRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("Crop not found with id: " + id));

        User user = getUserFromUserDetails(userDetails);
        if (user.getRole() != Role.ADMIN && !existingCrop.getProductor().getId().equals(user.getId())) {
            throw new UnauthorizedException("Not authorized to update this crop");
        }

        updateCropFromDTO(existingCrop, cropDTO);
        Crop updatedCrop = cropRepository.save(existingCrop);
        log.debug("Crop updated successfully");
        return updatedCrop;
    }

    @Transactional
    public void deleteCrop(String id, UserDetails userDetails) {
        log.debug("Deleting crop with ID: {}", id);
        Crop crop = cropRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("Crop not found with id: " + id));

        User user = getUserFromUserDetails(userDetails);
        if (user.getRole() != Role.ADMIN && !crop.getProductor().getId().equals(user.getId())) {
            throw new UnauthorizedException("Not authorized to delete this crop");
        }

        cropRepository.delete(crop);
        log.debug("Crop deleted successfully");
    }

    private User getUserFromUserDetails(UserDetails userDetails) {
        return userRepository.findByUsuario(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private void updateCropFromDTO(Crop crop, CropDTO dto) {
        crop.setNumeroPlants(dto.getNumeroPlants());
        crop.setHectareas(dto.getHectareas());
        crop.setProducto(dto.getProducto());
        crop.setEstado(dto.getEstado());
    }
}
