package com.company.structureinventorysystem.service;

import com.company.structureinventorysystem.domain.user.User;
import com.company.structureinventorysystem.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends GenericService<User> {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        super(User.class, repository);
        this.repository = repository;
    }

    public User getByIdWithRoles(Long id) {
        return repository.findByIdWithRoles(id);
    }

}
