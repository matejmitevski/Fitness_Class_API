package com.fitness.classscheduler.service;

import com.fitness.classscheduler.model.FitnessClassDto;
import com.fitness.classscheduler.model.FitnessClass;
import com.fitness.classscheduler.model.Instructor;
import com.fitness.classscheduler.model.User;
import com.fitness.classscheduler.repository.FitnessClassRepository;
import com.fitness.classscheduler.repository.InstructorRepository;
import com.fitness.classscheduler.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import java.util.List;

@Service
public class FitnessClassService {

    @Autowired
    private FitnessClassRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    public FitnessClass createFromDto(@Valid FitnessClassDto dto) {
        Instructor instructor = instructorRepository.findById(dto.getInstructorId())
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        FitnessClass fc = new FitnessClass();
        fc.setTitle(dto.getTitle());
        fc.setStartTime(dto.getStartTime());
        fc.setEndTime(dto.getEndTime());
        fc.setCapacity(dto.getCapacity());
        fc.setInstructor(instructor);
        return repository.save(fc);
    }


    public List<FitnessClass> getAll() {
        return repository.findAll();
    }

    public FitnessClass getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + id));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<User> getAttendees(Long classId) {
        FitnessClass fc = getById(classId);
        return fc.getAttendees();
    }


    public void removeAttendee(Long classId, Long userId) {
        FitnessClass fc = getById(classId);
        fc.getAttendees().removeIf(user -> user.getId().equals(userId));
        repository.save(fc);
    }

    public FitnessClass enrollUser(Long classId, User user) {
        FitnessClass fc = getById(classId);
        User savedUser = userRepository.save(user);
        fc.getAttendees().add(savedUser);
        return repository.save(fc);
    }

    public FitnessClassDto toDto(FitnessClass fc) {
        FitnessClassDto dto = new FitnessClassDto();
        dto.setId(fc.getId());
        dto.setTitle(fc.getTitle());
        dto.setStartTime(fc.getStartTime());
        dto.setEndTime(fc.getEndTime());
        dto.setInstructorId(fc.getInstructor().getId());
        dto.setCapacity(fc.getCapacity());
        dto.setStatus(fc.getStatus());
        dto.setCanceled(fc.isCanceled());

        List<Long> attendeeIds = fc.getAttendees().stream()
                .map(User::getId)
                .toList();
        dto.setAttendeeIds(attendeeIds);

        return dto;
    }

    public FitnessClass enrollUsersBulk(Long classId, List<User> users) {
        FitnessClass fc = getById(classId);

        for (User user : users) {
            User savedUser;
            if (user.getId() != null) {
                savedUser = userRepository.findById(user.getId())
                        .orElseThrow(() -> new RuntimeException("User not found: " + user.getId()));
            } else {
                savedUser = userRepository.save(user);
            }
            if (!fc.getAttendees().contains(savedUser)) {
                fc.getAttendees().add(savedUser);
            }
        }
        return repository.save(fc);
    }

    public FitnessClass updateClassPartial(Long classId, FitnessClassDto dto) {
        FitnessClass fc = getById(classId);

        if (dto.getTitle() != null) {
            fc.setTitle(dto.getTitle());
        }
        if (dto.getStartTime() != null) {
            fc.setStartTime(dto.getStartTime());
        }
        if (dto.getEndTime() != null) {
            fc.setEndTime(dto.getEndTime());
        }
        if (dto.getCapacity() != null) {
            fc.setCapacity(dto.getCapacity());
        }
        if (dto.getInstructorId() != null) {
            Instructor instructor = instructorRepository.findById(dto.getInstructorId())
                    .orElseThrow(() -> new RuntimeException("Instructor not found"));
            fc.setInstructor(instructor);
        }

        return repository.save(fc);
    }

    public List<User> getWaitlist(Long classId) {
        FitnessClass fc = getById(classId);
        return fc.getWaitlist();
    }

    public FitnessClass addToWaitlist(Long classId, User user) {
        FitnessClass fc = getById(classId);
        User savedUser = userRepository.save(user);

        if (!fc.getWaitlist().contains(savedUser)) {
            fc.getWaitlist().add(savedUser);
        }
        return repository.save(fc);
    }

    public FitnessClass removeFromWaitlist(Long classId, Long userId) {
        FitnessClass fc = getById(classId);
        fc.getWaitlist().removeIf(user -> user.getId().equals(userId));
        return repository.save(fc);
    }

    public void cancelClass(Long classId) {
        FitnessClass fitnessClass = repository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));
        fitnessClass.setCanceled(true);
        repository.save(fitnessClass);
    }

    public Map<String, Object> getClassSummary(Long classId) {
        FitnessClass fitnessClass = repository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        Map<String, Object> summary = new HashMap<>();
        summary.put("classTitle", fitnessClass.getTitle());
        summary.put("enrolledCount", fitnessClass.getAttendees().size());
        summary.put("waitlistCount", fitnessClass.getWaitlist().size());
        summary.put("isCanceled", fitnessClass.isCanceled());

        return summary;
    }


}

