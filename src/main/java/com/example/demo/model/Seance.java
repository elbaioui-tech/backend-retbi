package com.example.demo.model;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "seances")
public class Seance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private TypeSeance type; // MUSCULATION ou MMA

    @ManyToOne
    @JoinColumn(name = "programme_id", nullable = false)
    private Programme programme;

    public Seance() {}

    public Seance(LocalDate date, TypeSeance type, Programme programme) {
        this.date = date;
        this.type = type;
        this.programme = programme;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public TypeSeance getType() { return type; }
    public void setType(TypeSeance type) { this.type = type; }
    public Programme getProgramme() { return programme; }
    public void setProgramme(Programme programme) { this.programme = programme; }



    @Enumerated(EnumType.STRING)
    private StatutSeance statut = StatutSeance.BROUILLON;

    // N'oublie pas de générer le Getter et le Setter :
    public StatutSeance getStatut() {
        return statut;
    }

    public void setStatut(StatutSeance statut) {
        this.statut = statut;
    }



}