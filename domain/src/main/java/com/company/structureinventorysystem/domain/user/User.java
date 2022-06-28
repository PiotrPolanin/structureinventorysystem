package com.company.structureinventorysystem.domain.user;

import com.company.structureinventorysystem.domain.shared.BaseEntity;
import com.company.structureinventorysystem.domain.shared.UpdateEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", schema = "structureinventorysystem")
public class User extends BaseEntity<Long> implements UpdateEntity<User> {

    @NotNull(message = "{validation.error.user.firstName.NotNull}")
    @Size(min = 2, max = 50, message = "{validation.error.user.firstName.Size}")
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @NotNull(message = "{validation.error.user.lastName.NotNull}")
    @Size(min = 1, max = 50, message = "{validation.error.user.lastName.Size}")
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "academic_degree")
    private String academicDegree;
    @ElementCollection(targetClass = UserRole.class)
    @Enumerated(value = EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), foreignKey = @ForeignKey(name = "FK_users_user_roles"), schema = "structureinventorysystem")
    private final Set<UserRole> roles = new HashSet<>();

    public User(@NotNull String firstName, @NotNull String lastName, String academicDegree) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.academicDegree = academicDegree;
    }

    public User(@NotNull String firstName, @NotNull String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User() {
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAcademicDegree(String academicDegree) {
        this.academicDegree = academicDegree;
    }

    public boolean addRole(UserRole role) {
        return roles.add(role);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAcademicDegree() {
        return academicDegree;
    }

    public Set<UserRole> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    @Override
    public void update(@NotNull User updated) {
        if (updated == null) {
            throw new IllegalArgumentException("Updated state must not null");
        }
        this.firstName = updated.getFirstName();
        this.lastName = updated.getLastName();
        this.academicDegree = updated.getAcademicDegree();
        if (updated.getRoles().size() > 0) {
            this.roles.clear();
            this.roles.addAll(updated.getRoles());
        }
    }

    @Override
    public String toString() {
        return "User [" +
                "id: " + id +
                ", first name: " + firstName +
                ", last name: " + lastName +
                ", academic degree: " + academicDegree +
                ", roles: " + roles + "]";
    }

}
