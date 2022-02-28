package com.company.structureinventorysystem.web;

import com.company.structureinventorysystem.domain.user.User;
import com.company.structureinventorysystem.domain.user.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "/api/user")
public class UserController extends GenericController<User> {

    public UserController(UserService service) {
        super(service);
    }
}
