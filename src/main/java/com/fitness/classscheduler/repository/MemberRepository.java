package com.fitness.classscheduler.repository;

import com.fitness.classscheduler.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
