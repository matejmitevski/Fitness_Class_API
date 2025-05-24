package com.fitness.classscheduler.service;

import com.fitness.classscheduler.model.Member;
import com.fitness.classscheduler.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public Member createMember(Member member) {
        return repository.save(member);
    }

    public List<Member> getAll() {
        return repository.findAll();
    }
}
