package com.enigmacamp.loan_app.service;

import com.enigmacamp.loan_app.entity.Role;

public interface RoleService {
    Role getOrSave(Role role);
}
