package com.company.structureinventorysystem.web.user;

import com.company.structureinventorysystem.domain.user.User;
import com.company.structureinventorysystem.service.UserService;
import com.company.structureinventorysystem.web.shared.GenericController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping(value = "/api/user")
public class UserController extends GenericController<User> {

    public UserController(UserService service) {
        super(service);
    }
}
