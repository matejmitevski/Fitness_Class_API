package com.fitness.classscheduler.dto;

import java.util.List;
import com.fitness.classscheduler.dto.BulkEnrollmentDto;


public class BulkEnrollmentDto {

    private List<UserDto> users;

    public BulkEnrollmentDto() {
    }

    public BulkEnrollmentDto(List<UserDto> users) {
        this.users = users;
    }

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }
}
