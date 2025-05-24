package com.fitness.classscheduler.controller;

import com.fitness.classscheduler.model.Instructor;
import com.fitness.classscheduler.service.InstructorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructors")
public class InstructorController {

    private final InstructorService service;

    public InstructorController(InstructorService service) {
        this.service = service;
    }

    @PostMapping
    public Instructor create(@RequestBody Instructor instructor) {
        return service.createInstructor(instructor);
    }

    @GetMapping
    public List<Instructor> getAll() {
        return service.getAll();
    }
}
