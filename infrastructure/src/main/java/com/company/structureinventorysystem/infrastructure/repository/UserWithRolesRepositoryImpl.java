package com.company.structureinventorysystem.infrastructure.repository;

import com.company.structureinventorysystem.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class UserWithRolesRepositoryImpl implements UserWithRolesRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public UserWithRolesRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findByIdWithRoles(Long id) {
        if (id != null) {
            return entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.roles WHERE u.id = :id", User.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }
        throw new IllegalArgumentException("The given id must not be null");
    }

}
