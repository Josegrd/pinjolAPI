package com.enigmacamp.loan_app.service;

import com.enigmacamp.loan_app.entity.Admin;

public interface AdminService {
    void createAdmin(Admin admin);

    Admin getById(String id);
}
