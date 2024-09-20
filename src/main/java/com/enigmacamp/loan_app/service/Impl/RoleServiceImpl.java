package com.enigmacamp.loan_app.service.Impl;

import com.enigmacamp.loan_app.entity.Role;
import com.enigmacamp.loan_app.repository.RoleRepository;
import com.enigmacamp.loan_app.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(Role role) {
        Optional<Role> optionalRole = roleRepository.findByName(role.getName());
        if (optionalRole.isPresent()) {
            return optionalRole.get();
        }

        return roleRepository.save(role);
    }
}
