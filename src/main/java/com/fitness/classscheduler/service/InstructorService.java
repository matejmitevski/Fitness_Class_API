package com.fitness.classscheduler.service;

import com.fitness.classscheduler.model.Instructor;
import com.fitness.classscheduler.repository.InstructorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {

    private final InstructorRepository repository;

    public InstructorService(InstructorRepository repository) {
        this.repository = repository;
    }

    public Instructor createInstructor(Instructor instructor) {
        return repository.save(instructor);
    }

    public List<Instructor> getAll() {
        return repository.findAll();
    }
}
