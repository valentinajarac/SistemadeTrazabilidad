package com.traceability.controller;

import com.traceability.dto.FarmDTO;
import com.traceability.model.Farm;
import com.traceability.service.FarmService;
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
@RequestMapping("/farms")
@RequiredArgsConstructor
public class FarmController {

    private final FarmService farmService;

    @GetMapping
    public ResponseEntity<List<Farm>> getFarms(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(farmService.getFarms(userDetails));
    }

    @PostMapping
    public ResponseEntity<Farm> createFarm(@Valid @RequestBody FarmDTO farmDTO,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(farmService.createFarm(farmDTO, userDetails));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Farm> updateFarm(@PathVariable Long id,
                                           @Valid @RequestBody FarmDTO farmDTO,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(farmService.updateFarm(id, farmDTO, userDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFarm(@PathVariable Long id,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        farmService.deleteFarm(id, userDetails);
        return ResponseEntity.noContent().build();
    }
}
