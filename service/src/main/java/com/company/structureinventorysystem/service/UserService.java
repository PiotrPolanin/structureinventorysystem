package com.company.structureinventorysystem.service;

import com.company.structureinventorysystem.domain.user.User;
import com.company.structureinventorysystem.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends GenericService<User> {
    public UserService(UserRepository repository) {
        super(repository);
    }
}
