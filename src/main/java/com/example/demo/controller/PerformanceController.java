package com.example.demo.controller;

import com.example.demo.dto.Evolution1RM;
import com.example.demo.entity.Performance;
import com.example.demo.model.Exercice;
import com.example.demo.model.Seance;
import com.example.demo.repository.ExerciceRepository;
import com.example.demo.repository.PerformanceRepository;
import com.example.demo.repository.SeanceRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/performances")
public class PerformanceController {

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private ExerciceRepository exerciceRepository;

    @PostMapping
    public ResponseEntity<Performance> ajouterPerformance(
            @RequestParam Long seanceId,
            @RequestParam Long exerciceId,
            @Valid @RequestBody Performance performanceDetails) {

        Seance seance = seanceRepository.findById(seanceId)
                .orElseThrow(() -> new RuntimeException("Séance non trouvée avec l'id : " + seanceId));

        Exercice exercice = exerciceRepository.findById(exerciceId)
                .orElseThrow(() -> new RuntimeException("Exercice non trouvé avec l'id : " + exerciceId));

        performanceDetails.setSeance(seance);
        performanceDetails.setExercice(exercice);

        Performance nouvellePerformance = performanceRepository.save(performanceDetails);
        return ResponseEntity.ok(nouvellePerformance);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Performance> obtenirPerformance(@PathVariable Long id) {
        return performanceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Performance> modifierPerformance(
            @PathVariable Long id,
            @Valid @RequestBody Performance details) {
        return performanceRepository.findById(id).map(performance -> {
            performance.setPoids(details.getPoids());
            performance.setRepetitions(details.getRepetitions());
            performance.setSeries(details.getSeries());
            return ResponseEntity.ok(performanceRepository.save(performance));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerPerformance(@PathVariable Long id) {
        if (performanceRepository.existsById(id)) {
            performanceRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/evolution-1rm/{exerciceId}")
    public ResponseEntity<List<Evolution1RM>> obtenirEvolution1RM(@PathVariable Long exerciceId) {
        return ResponseEntity.ok(performanceRepository.getEvolution1RMAuCoursDes30DerniersJours(exerciceId));
    }
}