package com.example.demo.controller;

import com.example.demo.model.Exercice;
import com.example.demo.repository.ExerciceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercices")
public class ExerciceController {

    private final ExerciceRepository exerciceRepository;

    public ExerciceController(ExerciceRepository exerciceRepository) {
        this.exerciceRepository = exerciceRepository;
    }

    @GetMapping
    public ResponseEntity<List<Exercice>> getAllExercices() {
        List<Exercice> exercices = exerciceRepository.findAll();
        return ResponseEntity.ok(exercices);
    }
    @PostMapping
    public ResponseEntity<Exercice> creerExercice(@RequestBody Exercice exercice) {
        Exercice nouvelExercice = exerciceRepository.save(exercice);
        return ResponseEntity.ok(nouvelExercice);
    }
}