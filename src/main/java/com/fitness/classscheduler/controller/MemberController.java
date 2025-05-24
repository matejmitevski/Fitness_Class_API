package com.fitness.classscheduler.controller;

import com.fitness.classscheduler.model.Member;
import com.fitness.classscheduler.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @PostMapping
    public Member create(@RequestBody Member member) {
        return service.createMember(member);
    }

    @GetMapping
    public List<Member> getAll() {
        return service.getAll();
    }
}
