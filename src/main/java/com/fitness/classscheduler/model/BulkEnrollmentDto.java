package com.fitness.classscheduler.model;

import java.util.List;


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
