package com.fitness.classscheduler.repository;

import com.fitness.classscheduler.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
