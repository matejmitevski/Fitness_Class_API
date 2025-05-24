package com.fitness.classscheduler.repository;

import com.fitness.classscheduler.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}
