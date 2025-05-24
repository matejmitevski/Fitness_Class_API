package com.fitness.classscheduler.controller;

import com.fitness.classscheduler.dto.FitnessClassDto;
import com.fitness.classscheduler.model.FitnessClass;
import com.fitness.classscheduler.model.User;
import com.fitness.classscheduler.service.FitnessClassService;
import com.fitness.classscheduler.dto.BulkEnrollmentDto;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
public class FitnessClassController {

    @Autowired
    private FitnessClassService service;

    @PostMapping
    public ResponseEntity<FitnessClassDto> create(@Valid @RequestBody FitnessClassDto dto) {
        FitnessClass created = service.createFromDto(dto);
        return ResponseEntity.ok(service.toDto(created));
    }


    @GetMapping
    public List<FitnessClassDto> getAll() {
        return service.getAll().stream()
                .map(service::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public FitnessClassDto getById(@PathVariable Long id) {
        return service.toDto(service.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{classId}/attendees")
    public ResponseEntity<FitnessClassDto> enroll(@PathVariable Long classId, @RequestBody User user) {
        FitnessClass fc = service.enrollUser(classId, user);
        return ResponseEntity.ok(service.toDto(fc));
    }

    @GetMapping("/{classId}/attendees")
    public List<User> getAttendees(@PathVariable Long classId) {
        return service.getAttendees(classId);
    }

    @DeleteMapping("/{classId}/attendees/{userId}")
    public ResponseEntity<?> removeAttendee(@PathVariable Long classId, @PathVariable Long userId) {
        service.removeAttendee(classId, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{classId}/attendees/bulk")
    public ResponseEntity<FitnessClassDto> enrollUsersBulk(
            @PathVariable Long classId,
            @RequestBody @Valid BulkEnrollmentDto bulkDto) {

        List<User> users = bulkDto.getUsers().stream()
                .map(dto -> {
                    User user = new User();
                    user.setId(dto.getId());
                    user.setName(dto.getName());
                    user.setEmail(dto.getEmail());
                    return user;
                })
                .toList();
        FitnessClass fc = service.enrollUsersBulk(classId, users);
        return ResponseEntity.ok(service.toDto(fc));
    }

    @PatchMapping("/{classId}")
    public ResponseEntity<FitnessClassDto> patchClass(@PathVariable Long classId, @RequestBody FitnessClassDto dto) {
        FitnessClass updatedClass = service.updateClassPartial(classId, dto);
        return ResponseEntity.ok(service.toDto(updatedClass));
    }

    @GetMapping("/{classId}/waitlist")
    public ResponseEntity<List<User>> getWaitlist(@PathVariable Long classId) {
        List<User> waitlist = service.getWaitlist(classId);
        return ResponseEntity.ok(waitlist);
    }

    @PostMapping("/{classId}/waitlist")
    public ResponseEntity<FitnessClassDto> addToWaitlist(@PathVariable Long classId, @RequestBody User user) {
        FitnessClass updatedClass = service.addToWaitlist(classId, user);
        return ResponseEntity.ok(service.toDto(updatedClass));
    }

    @DeleteMapping("/{classId}/waitlist/{userId}")
    public ResponseEntity<Void> removeFromWaitlist(@PathVariable Long classId, @PathVariable Long userId) {
        service.removeFromWaitlist(classId, userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<String> cancelClass(@PathVariable Long id) {
        service.cancelClass(id);
        return ResponseEntity.ok("Class canceled successfully.");
    }

    @GetMapping("/{id}/summary")
    public ResponseEntity<Map<String, Object>> getClassSummary(@PathVariable Long id) {
        return ResponseEntity.ok(service.getClassSummary(id));
    }


}
