package com.fruitsales.service;

import com.fruitsales.model.Fruit;
import com.fruitsales.repository.FruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FruitService {
    
    private final FruitRepository fruitRepository;

    @Autowired
    public FruitService(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    public List<Fruit> getAllFruits() {
        return fruitRepository.findAll();
    }

    public Optional<Fruit> getFruitById(Long id) {
        return fruitRepository.findById(id);
    }

    public Fruit saveFruit(Fruit fruit) {
        return fruitRepository.save(fruit);
    }

    public void deleteFruit(Long id) {
        fruitRepository.deleteById(id);
    }
}