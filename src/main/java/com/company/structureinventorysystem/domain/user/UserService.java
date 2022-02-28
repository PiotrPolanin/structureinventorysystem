package com.company.structureinventorysystem.domain.user;

import com.company.structureinventorysystem.domain.shared.GenericService;
import com.company.structureinventorysystem.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends GenericService<User> {
    public UserService(UserRepository repository) {
        super(repository);
    }
}
