package com.company.structureinventorysystem.infrastructure.repository;

import com.company.structureinventorysystem.domain.user.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends GenericRepository<User>, UserWithRolesRepository {

}
