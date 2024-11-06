package com.traceability.controller;

import com.traceability.dto.RemissionDTO;
import com.traceability.model.Remission;
import com.traceability.service.RemissionService;
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
@RequestMapping("/api/remissions")
@RequiredArgsConstructor
public class RemissionController {

    private final RemissionService remissionService;

    @PostMapping
    public ResponseEntity<Remission> createRemission(
            @Valid @RequestBody RemissionDTO remissionDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.debug("POST /remissions - Creating new remission");
        return ResponseEntity.ok(remissionService.createRemission(remissionDTO, userDetails));
    }

    @GetMapping
    public ResponseEntity<List<Remission>> getRemissions(
            @AuthenticationPrincipal UserDetails userDetails) {
        log.debug("GET /remissions - Retrieving remissions");
        return ResponseEntity.ok(remissionService.getRemissions(userDetails));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Remission> updateRemission(
            @PathVariable Long id,
            @Valid @RequestBody RemissionDTO remissionDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.debug("PUT /remissions/{} - Updating remission", id);
        return ResponseEntity.ok(remissionService.updateRemission(id, remissionDTO, userDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRemission(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.debug("DELETE /remissions/{} - Deleting remission", id);
        remissionService.deleteRemission(id, userDetails);
        return ResponseEntity.noContent().build();
    }
}
