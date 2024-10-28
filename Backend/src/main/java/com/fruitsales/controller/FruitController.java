package com.fruitsales.controller;

import com.fruitsales.model.Fruit;
import com.fruitsales.service.FruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/fruits")
@CrossOrigin(origins = "*")
public class FruitController {

    private final FruitService fruitService;

    @Autowired
    public FruitController(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @GetMapping
    public List<Fruit> getAllFruits() {
        return fruitService.getAllFruits();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fruit> getFruitById(@PathVariable Long id) {
        return fruitService.getFruitById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Fruit createFruit(@Valid @RequestBody Fruit fruit) {
        return fruitService.saveFruit(fruit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fruit> updateFruit(@PathVariable Long id, @Valid @RequestBody Fruit fruit) {
        return fruitService.getFruitById(id)
                .map(existingFruit -> {
                    fruit.setId(id);
                    return ResponseEntity.ok(fruitService.saveFruit(fruit));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFruit(@PathVariable Long id) {
        return fruitService.getFruitById(id)
                .map(fruit -> {
                    fruitService.deleteFruit(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}