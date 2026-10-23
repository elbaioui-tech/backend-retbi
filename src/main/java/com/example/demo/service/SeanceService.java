package com.example.demo.service;

import com.example.demo.entity.Performance;
import com.example.demo.repository.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SeanceService {

    @Autowired
    private PerformanceRepository performanceRepository;

    public double calculerVolumeTotal(Long seanceId) {
        List<Performance> performances = performanceRepository.findBySeanceId(seanceId);
        return performances.stream()
                .mapToDouble(p -> p.getPoids() * p.getRepetitions() * p.getSeries())
                .sum();
    }
}