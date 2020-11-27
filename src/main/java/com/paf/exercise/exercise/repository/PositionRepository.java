package com.paf.exercise.exercise.repository;

import com.paf.exercise.exercise.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Long> {

    List<Position> findByLabelAndRunning(String label, Boolean running);
}
