package com.example.demo.repository;

import com.example.demo.dto.Evolution1RM;
import com.example.demo.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    List<Performance> findBySeanceId(Long seanceId);

    @Query(value = "SELECT CAST(s.date AS DATE) as date, MAX(p.poids * (1 + p.repetitions / 30.0)) as max1rm " +
            "FROM performances p " +
            "JOIN seance s ON p.seance_id = s.id " +
            "WHERE p.exercice_id = :exerciceId AND s.date >= CURRENT_DATE - 30 " +
            "GROUP BY CAST(s.date AS DATE) " +
            "ORDER BY date ASC", nativeQuery = true)
    List<Evolution1RM> getEvolution1RMAuCoursDes30DerniersJours(@Param("exerciceId") Long exerciceId);
}