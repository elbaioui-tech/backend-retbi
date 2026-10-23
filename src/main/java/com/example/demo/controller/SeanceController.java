package com.example.demo.controller;

import com.example.demo.model.Programme;
import com.example.demo.model.Seance;
import com.example.demo.repository.ProgrammeRepository;
import com.example.demo.repository.SeanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seances")
public class SeanceController {

    private final SeanceRepository seanceRepository;
    private final ProgrammeRepository programmeRepository;

    @Autowired
    private com.example.demo.service.SeanceService seanceService;

    public SeanceController(SeanceRepository seanceRepository, ProgrammeRepository programmeRepository) {
        this.seanceRepository = seanceRepository;
        this.programmeRepository = programmeRepository;
    }

    @GetMapping("/historique")
    public ResponseEntity<Page<Seance>> obtenirHistorique(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ResponseEntity.ok(seanceRepository.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Seance> creerSeance(@RequestBody Seance seance, @RequestParam Long programmeId) {
        Programme programme = programmeRepository.findById(programmeId)
                .orElseThrow(() -> new RuntimeException("Programme non trouvé avec l'id : " + programmeId));

        seance.setProgramme(programme);
        Seance nouvelleSeance = seanceRepository.save(seance);
        return ResponseEntity.ok(nouvelleSeance);
    }

    @GetMapping("/programme/{programmeId}")
    public ResponseEntity<List<Seance>> getSeancesParProgramme(@PathVariable Long programmeId) {
        List<Seance> seances = seanceRepository.findByProgrammeId(programmeId);
        return ResponseEntity.ok(seances);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seance> modifierSeance(@PathVariable Long id, @RequestBody Seance seanceDetails) {
        Seance seance = seanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Séance non trouvée avec l'id : " + id));

        seance.setDate(seanceDetails.getDate());
        seance.setType(seanceDetails.getType());

        Seance seanceMiseAJour = seanceRepository.save(seance);
        return ResponseEntity.ok(seanceMiseAJour);
    }

    @GetMapping
    public ResponseEntity<List<Seance>> getAllSeances() {
        return ResponseEntity.ok(seanceRepository.findAll());
    }

    @PatchMapping("/{id}/cloturer")
    public ResponseEntity<Seance> cloturerSeance(@PathVariable Long id) {
        return seanceRepository.findById(id).map(seance -> {
            seance.setStatut(com.example.demo.model.StatutSeance.TERMINE);
            return ResponseEntity.ok(seanceRepository.save(seance));
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/volume")
    public ResponseEntity<Double> obtenirVolumeTotal(@PathVariable Long id) {
        double volume = seanceService.calculerVolumeTotal(id);
        return ResponseEntity.ok(volume);
    }
}