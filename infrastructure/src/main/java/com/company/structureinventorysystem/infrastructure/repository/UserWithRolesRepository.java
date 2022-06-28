package com.company.structureinventorysystem.infrastructure.repository;

import com.company.structureinventorysystem.domain.user.User;

public interface UserWithRolesRepository {

    User findByIdWithRoles(Long id);

}
