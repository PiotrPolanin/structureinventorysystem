package com.company.structureinventorysystem.web.user;

import com.company.structureinventorysystem.domain.user.User;
import com.company.structureinventorysystem.infrastructure.repository.UserRepository;
import com.company.structureinventorysystem.web.shared.GenericService;
import org.springframework.stereotype.Service;

@Service
public class UserService extends GenericService<User> {
    public UserService(UserRepository repository) {
        super(repository);
    }
}
