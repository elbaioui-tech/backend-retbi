package com.example.demo.entity;

import com.example.demo.model.Exercice;
import com.example.demo.model.Seance;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "performances")
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PositiveOrZero(message = "Le poids ne peut pas être négatif")
    private double poids;

    @Positive(message = "Le nombre de répétitions doit être au minimum de 1")
    private int repetitions;

    @Positive(message = "Le nombre de séries doit être au minimum de 1")
    private int series;

    @ManyToOne
    @JoinColumn(name = "seance_id", nullable = false)
    private Seance seance;

    @ManyToOne
    @JoinColumn(name = "exercice_id", nullable = false)
    private Exercice exercice;

    public Performance() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getPoids() { return poids; }
    public void setPoids(double poids) { this.poids = poids; }

    public int getRepetitions() { return repetitions; }
    public void setRepetitions(int repetitions) { this.repetitions = repetitions; }

    public int getSeries() { return series; }
    public void setSeries(int series) { this.series = series; }

    public Seance getSeance() { return seance; }
    public void setSeance(Seance seance) { this.seance = seance; }

    public Exercice getExercice() { return exercice; }
    public void setExercice(Exercice exercice) { this.exercice = exercice; }
}