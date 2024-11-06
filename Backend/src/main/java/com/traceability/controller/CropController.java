package com.traceability.controller;

import com.traceability.dto.CropDTO;
import com.traceability.model.Crop;
import com.traceability.service.CropService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/crops")
@RequiredArgsConstructor
public class CropController {

    private final CropService cropService;

    @PostMapping
    public ResponseEntity<Crop> createCrop(
            @Valid @RequestBody CropDTO cropDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.debug("POST /crops - Creating new crop");
        return ResponseEntity.ok(cropService.createCrop(cropDTO, userDetails));
    }

    @GetMapping
    public ResponseEntity<List<Crop>> getCrops(
            @AuthenticationPrincipal UserDetails userDetails) {
        log.debug("GET /crops - Retrieving crops for user: {}", userDetails.getUsername());
        return ResponseEntity.ok(cropService.getCrops(userDetails));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Crop> updateCrop(
            @PathVariable Long id,
            @Valid @RequestBody CropDTO cropDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.debug("PUT /crops/{} - Updating crop", id);
        return ResponseEntity.ok(cropService.updateCrop(id.toString(), cropDTO, userDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrop(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.debug("DELETE /crops/{} - Deleting crop", id);
        cropService.deleteCrop(id.toString(), userDetails);
        return ResponseEntity.noContent().build();
    }
}
