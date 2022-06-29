package com.company.structureinventorysystem.service;

import com.company.structureinventorysystem.configuration.PersistenceTestConfiguration;
import com.company.structureinventorysystem.configuration.UserPersistenceTestConfiguration;
import com.company.structureinventorysystem.configuration.UserServiceTestConfiguration;
import com.company.structureinventorysystem.domain.user.User;
import com.company.structureinventorysystem.domain.user.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {PersistenceTestConfiguration.class, UserPersistenceTestConfiguration.class, UserServiceTestConfiguration.class})
public class UserServiceTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserService userService;

    @Test
    public void shouldGetByIdThrowInvalidDataAccessApiUsageExceptionWhenIdIsNull() {
        InvalidDataAccessApiUsageException exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> userService.getById(null));
        assertEquals("The given id must not be null!; nested exception is java.lang.IllegalArgumentException: The given id must not be null!", exception.getMessage());
    }

    @Test
    public void shouldGetByIdThrowEntityNotFoundExceptionWhenIdNotFound() {
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> userService.getById(100L));
        assertEquals("Entity com.company.structureinventorysystem.domain.user.User with id 100 not found", exception.getMessage());
    }

    @Test
    public void shouldGetByIdReturnUserFromDB() {
        //When
        User johnSmith = userService.getById(1L);
        User lisaMonk = userService.getById(2L);
        User annMcDonald = userService.getById(3L);
        User jonathanDoe = userService.getById(4L);
        User luigiFresco = userService.getById(5L);
        User raulGonzales = userService.getById(6L);
        User marcoBellucci = userService.getById(7L);
        User markHolmes = userService.getById(8L);
        User amirKhan = userService.getById(9L);
        User miriamVelasques = userService.getById(10L);
        //Then
        assertEquals(1, johnSmith.getId());
        assertEquals("John", johnSmith.getFirstName());
        assertEquals("Smith", johnSmith.getLastName());

        assertEquals(2, lisaMonk.getId());
        assertEquals("Lisa", lisaMonk.getFirstName());
        assertEquals("Monk", lisaMonk.getLastName());

        assertEquals(3, annMcDonald.getId());
        assertEquals("Ann", annMcDonald.getFirstName());
        assertEquals("McDonald", annMcDonald.getLastName());

        assertEquals(4, jonathanDoe.getId());
        assertEquals("Jonathan", jonathanDoe.getFirstName());
        assertEquals("Doe", jonathanDoe.getLastName());

        assertEquals(5, luigiFresco.getId());
        assertEquals("Luigi", luigiFresco.getFirstName());
        assertEquals("Fresco", luigiFresco.getLastName());

        assertEquals(6, raulGonzales.getId());
        assertEquals("Raul", raulGonzales.getFirstName());
        assertEquals("Gonzales", raulGonzales.getLastName());

        assertEquals(7, marcoBellucci.getId());
        assertEquals("Marco", marcoBellucci.getFirstName());
        assertEquals("Bellucci", marcoBellucci.getLastName());

        assertEquals(8, markHolmes.getId());
        assertEquals("Mark", markHolmes.getFirstName());
        assertEquals("Holmes", markHolmes.getLastName());

        assertEquals(9, amirKhan.getId());
        assertEquals("Amir", amirKhan.getFirstName());
        assertEquals("Khan", amirKhan.getLastName());

        assertEquals(10, miriamVelasques.getId());
        assertEquals("Miriam", miriamVelasques.getFirstName());
        assertEquals("Velasques", miriamVelasques.getLastName());
    }

    @Test
    public void shouldGetByIdWithRolesThrowInvalidDataAccessApiUsageExceptionWhenIdIsNull() {
        InvalidDataAccessApiUsageException exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> userService.getByIdWithRoles(null));
        assertEquals("The given id must not be null; nested exception is java.lang.IllegalArgumentException: The given id must not be null", exception.getMessage());
    }

    @Test
    public void shouldGetByIdWithRolesThrowEmptyResultDataAccessExceptionWhenIdNotFound() {
        assertThrows(EmptyResultDataAccessException.class, () -> userService.getByIdWithRoles(11L));
    }

    @Test
    public void shouldGetByIdWitRolesReturnUserFromDB() {
        //When
        User johnSmith = userService.getByIdWithRoles(1L);
        User lisaMonk = userService.getByIdWithRoles(2L);
        User annMcDonald = userService.getByIdWithRoles(3L);
        User jonathanDoe = userService.getByIdWithRoles(4L);
        User luigiFresco = userService.getByIdWithRoles(5L);
        User raulGonzales = userService.getByIdWithRoles(6L);
        User marcoBellucci = userService.getByIdWithRoles(7L);
        User markHolmes = userService.getByIdWithRoles(8L);
        User amirKhan = userService.getByIdWithRoles(9L);
        User miriamVelasques = userService.getByIdWithRoles(10L);
        //Then
        assertEquals(1, johnSmith.getRoles().size());
        assertTrue(johnSmith.getRoles().contains(UserRole.VISITOR));
        assertEquals(1, lisaMonk.getRoles().size());
        assertTrue(lisaMonk.getRoles().contains(UserRole.USER));
        assertEquals(1, annMcDonald.getRoles().size());
        assertTrue(annMcDonald.getRoles().contains(UserRole.USER));
        assertEquals(1, jonathanDoe.getRoles().size());
        assertTrue(jonathanDoe.getRoles().contains(UserRole.USER));
        assertEquals(1, luigiFresco.getRoles().size());
        assertTrue(luigiFresco.getRoles().contains(UserRole.VISITOR));
        assertEquals(1, raulGonzales.getRoles().size());
        assertTrue(raulGonzales.getRoles().contains(UserRole.USER));
        assertEquals(1, marcoBellucci.getRoles().size());
        assertTrue(marcoBellucci.getRoles().contains(UserRole.VISITOR));
        assertEquals(3, markHolmes.getRoles().size());
        assertTrue(markHolmes.getRoles().contains(UserRole.VISITOR));
        assertTrue(markHolmes.getRoles().contains(UserRole.USER));
        assertTrue(markHolmes.getRoles().contains(UserRole.ADMIN));
        assertEquals(3, amirKhan.getRoles().size());
        assertTrue(amirKhan.getRoles().contains(UserRole.VISITOR));
        assertTrue(amirKhan.getRoles().contains(UserRole.USER));
        assertTrue(amirKhan.getRoles().contains(UserRole.ADMIN));
        assertEquals(1, miriamVelasques.getRoles().size());
        assertTrue(miriamVelasques.getRoles().contains(UserRole.VISITOR));
    }

    @Test
    public void shouldGetAllThrowIllegalArgumentExceptionWhenParametersAreNull() {
        IllegalArgumentException exceptionForNullPageNo = assertThrows(IllegalArgumentException.class, () -> userService.getAll(null, 10, "id", "asc"));
        assertEquals("Each parameter must not null or empty", exceptionForNullPageNo.getMessage());
        IllegalArgumentException exceptionForNullPageSize = assertThrows(IllegalArgumentException.class, () -> userService.getAll(0, null, "", "desc"));
        assertEquals("Each parameter must not null or empty", exceptionForNullPageSize.getMessage());
        IllegalArgumentException exceptionForNullSortBy = assertThrows(IllegalArgumentException.class, () -> userService.getAll(0, 20, null, "desc"));
        assertEquals("Each parameter must not null or empty", exceptionForNullSortBy.getMessage());
        IllegalArgumentException exceptionForNullDirectionSorting = assertThrows(IllegalArgumentException.class, () -> userService.getAll(0, 100, "firstName", null));
        assertEquals("Each parameter must not null or empty", exceptionForNullDirectionSorting.getMessage());
    }

    @Test
    public void shouldGetAllThrowIllegalArgumentExceptionWhenStringParametersAreEmpty() {
        IllegalArgumentException exceptionForEmptySortBy = assertThrows(IllegalArgumentException.class, () -> userService.getAll(0, 50, "", "desc"));
        assertEquals("Each parameter must not null or empty", exceptionForEmptySortBy.getMessage());
        IllegalArgumentException exceptionForEmptyDirectionSorting = assertThrows(IllegalArgumentException.class, () -> userService.getAll(0, 5, "lastName", ""));
        assertEquals("Each parameter must not null or empty", exceptionForEmptyDirectionSorting.getMessage());
    }

    @Test
    public void shouldGetAllThrowIllegalArgumentExceptionWhenPageNumberIsLessThanZero() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.getAll(-1, 0, "id", "asc"));
        assertEquals("Page index must not be less than zero!", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {-5, 0})
    public void shouldGetAllThrowIllegalArgumentExceptionWhenPageSizeIsLessThanOne(int pageSize) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.getAll(0, pageSize, "id", "desc"));
        assertEquals("Page size must not be less than one!", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"ids", "iD", "ID", "FName", "first_name", "firstname", "last-name", "last name", "academic+degree", "ACCADEMICDEGREE"})
    public void shouldGetAllThrowPropertyReferenceExceptionWhenSortParametersAreNotValid(String fieldName) {
        assertThrows(PropertyReferenceException.class, () -> userService.getAll(0, 100, fieldName, "asc"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"asc", "ASC", "unknown"})
    public void shouldGetAllReturnUsersSortedByIdAscendingDespiteOfDirParameterValue(String orderValue) {
        //When
        List<User> usersSortedById = userService.getAll(0, 10, "id", orderValue);
        //Then
        assertEquals(10, usersSortedById.size());
        for (int i = 0; i < usersSortedById.size(); i++) {
            assertEquals(i + 1, usersSortedById.get(i).getId());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"desc", "DESC", " desc "})
    public void shouldGetAllReturnUsersSortedByIdDescending(String orderValue) {
        //When
        List<User> usersSortedById = userService.getAll(0, 10, "id", orderValue);
        //Then
        assertEquals(10, usersSortedById.size());
        for (int i = 0; i < usersSortedById.size(); i++) {
            assertEquals(usersSortedById.size() - i, usersSortedById.get(i).getId());
        }
    }

    @Test
    public void shouldGetAllReturnUsersSortedByFirstNameAscending() {
        //When
        List<User> usersSortedByFirstName = userService.getAll(0, 10, "firstName", "asc");
        //Then
        assertEquals(10, usersSortedByFirstName.size());
        assertEquals("Amir", usersSortedByFirstName.get(0).getFirstName());
        assertEquals("Ann", usersSortedByFirstName.get(1).getFirstName());
        assertEquals("John", usersSortedByFirstName.get(2).getFirstName());
        assertEquals("Jonathan", usersSortedByFirstName.get(3).getFirstName());
        assertEquals("Lisa", usersSortedByFirstName.get(4).getFirstName());
        assertEquals("Luigi", usersSortedByFirstName.get(5).getFirstName());
        assertEquals("Marco", usersSortedByFirstName.get(6).getFirstName());
        assertEquals("Mark", usersSortedByFirstName.get(7).getFirstName());
        assertEquals("Miriam", usersSortedByFirstName.get(8).getFirstName());
        assertEquals("Raul", usersSortedByFirstName.get(9).getFirstName());
    }

    @Test
    public void shouldGetAllReturnUsersSortedByFirstNameDescending() {
        //When
        List<User> usersSortedByFirstName = userService.getAll(0, 10, "firstName", "desc");
        //Then
        assertEquals(10, usersSortedByFirstName.size());
        assertEquals("Amir", usersSortedByFirstName.get(9).getFirstName());
        assertEquals("Ann", usersSortedByFirstName.get(8).getFirstName());
        assertEquals("John", usersSortedByFirstName.get(7).getFirstName());
        assertEquals("Jonathan", usersSortedByFirstName.get(6).getFirstName());
        assertEquals("Lisa", usersSortedByFirstName.get(5).getFirstName());
        assertEquals("Luigi", usersSortedByFirstName.get(4).getFirstName());
        assertEquals("Marco", usersSortedByFirstName.get(3).getFirstName());
        assertEquals("Mark", usersSortedByFirstName.get(2).getFirstName());
        assertEquals("Miriam", usersSortedByFirstName.get(1).getFirstName());
        assertEquals("Raul", usersSortedByFirstName.get(0).getFirstName());
    }

    @Test
    public void shouldGetAllReturnUsersSortedByLastNameAscending() {
        //When
        List<User> usersSortedByLastName = userService.getAll(0, 10, "lastName", "asc");
        //Then
        assertEquals(10, usersSortedByLastName.size());
        assertEquals("Bellucci", usersSortedByLastName.get(0).getLastName());
        assertEquals("Doe", usersSortedByLastName.get(1).getLastName());
        assertEquals("Fresco", usersSortedByLastName.get(2).getLastName());
        assertEquals("Gonzales", usersSortedByLastName.get(3).getLastName());
        assertEquals("Holmes", usersSortedByLastName.get(4).getLastName());
        assertEquals("Khan", usersSortedByLastName.get(5).getLastName());
        assertEquals("McDonald", usersSortedByLastName.get(6).getLastName());
        assertEquals("Monk", usersSortedByLastName.get(7).getLastName());
        assertEquals("Smith", usersSortedByLastName.get(8).getLastName());
        assertEquals("Velasques", usersSortedByLastName.get(9).getLastName());
    }

    @Test
    public void shouldGetAllReturnUsersSortedByLastNameDescending() {
        //When
        List<User> usersSortedByLastName = userService.getAll(0, 10, "lastName", "desc");
        //Then
        assertEquals(10, usersSortedByLastName.size());
        assertEquals("Bellucci", usersSortedByLastName.get(9).getLastName());
        assertEquals("Doe", usersSortedByLastName.get(8).getLastName());
        assertEquals("Fresco", usersSortedByLastName.get(7).getLastName());
        assertEquals("Gonzales", usersSortedByLastName.get(6).getLastName());
        assertEquals("Holmes", usersSortedByLastName.get(5).getLastName());
        assertEquals("Khan", usersSortedByLastName.get(4).getLastName());
        assertEquals("McDonald", usersSortedByLastName.get(3).getLastName());
        assertEquals("Monk", usersSortedByLastName.get(2).getLastName());
        assertEquals("Smith", usersSortedByLastName.get(1).getLastName());
        assertEquals("Velasques", usersSortedByLastName.get(0).getLastName());
    }

    @Test
    public void shouldGetAllReturnUsersSortedByIdAscendingWithPageSizeEqualsFive() {
        //Given
        List<Long> refIdsFor_page_1 = List.of(1L, 2L, 3L, 4L, 5L);
        List<Long> refIdsFor_page_2 = List.of(6L, 7L, 8L, 9L, 10L);
        //When
        List<User> usersSortedById_page_1 = userService.getAll(0, 5, "id", "asc");
        List<User> usersSortedById_page_2 = userService.getAll(1, 5, "id", "asc");
        //Then
        assertEquals(5, usersSortedById_page_1.size());
        assertTrue(usersSortedById_page_1.stream().map(u -> u.getId()).collect(Collectors.toList()).containsAll(refIdsFor_page_1));
        assertEquals(5, usersSortedById_page_2.size());
        assertTrue(usersSortedById_page_2.stream().map(u -> u.getId()).collect(Collectors.toList()).containsAll(refIdsFor_page_2));
    }

    @Test
    public void shouldGetAllReturnUsersSortedByIdAscendingWithPageSizeEqualsTwo() {
        //Given
        List<Long> refIdsFor_page_1 = List.of(1L, 2L);
        List<Long> refIdsFor_page_2 = List.of(3L, 4L);
        List<Long> refIdsFor_page_3 = List.of(5L, 6L);
        List<Long> refIdsFor_page_4 = List.of(7L, 8L);
        List<Long> refIdsFor_page_5 = List.of(9L, 10L);
        //When
        List<User> usersSortedById_page_1 = userService.getAll(0, 2, "id", "asc");
        List<User> usersSortedById_page_2 = userService.getAll(1, 2, "id", "asc");
        List<User> usersSortedById_page_3 = userService.getAll(2, 2, "id", "asc");
        List<User> usersSortedById_page_4 = userService.getAll(3, 2, "id", "asc");
        List<User> usersSortedById_page_5 = userService.getAll(4, 2, "id", "asc");
        //Then
        assertEquals(2, usersSortedById_page_1.size());
        assertTrue(usersSortedById_page_1.stream().map(u -> u.getId()).collect(Collectors.toList()).containsAll(refIdsFor_page_1));
        assertEquals(2, usersSortedById_page_2.size());
        assertTrue(usersSortedById_page_2.stream().map(u -> u.getId()).collect(Collectors.toList()).containsAll(refIdsFor_page_2));
        assertEquals(2, usersSortedById_page_3.size());
        assertTrue(usersSortedById_page_3.stream().map(u -> u.getId()).collect(Collectors.toList()).containsAll(refIdsFor_page_3));
        assertEquals(2, usersSortedById_page_4.size());
        assertTrue(usersSortedById_page_4.stream().map(u -> u.getId()).collect(Collectors.toList()).containsAll(refIdsFor_page_4));
        assertEquals(2, usersSortedById_page_5.size());
        assertTrue(usersSortedById_page_5.stream().map(u -> u.getId()).collect(Collectors.toList()).containsAll(refIdsFor_page_5));
    }

    @Test
    public void shouldGetAllReturnEmptyListWhenPageNumberExceedPageSize() {
        //When
        List<User> empty = userService.getAll(100, 50, "firstName", "desc");
        //Then
        assertNotNull(empty);
        assertEquals(0, empty.size());
    }

    @Test
    public void shouldSaveThrowInvalidDataAccessApiUsageExceptionWhenUserIsNull() {
        InvalidDataAccessApiUsageException exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> userService.save(null));
        assertEquals("Entity must not be null.; nested exception is java.lang.IllegalArgumentException: Entity must not be null.", exception.getMessage());
    }

    @Test
    public void shouldSaveThrowConstraintViolationExceptionWhenRequiredPropertiesAreNull() {
        //Given
        User user = new User(null, null);
        //Then
        assertThrows(ConstraintViolationException.class, () -> userService.save(user));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "A", "Hubert Blaine Wolfeschlegelsteinhausenbergerdorff Sr."})
    public void shouldSaveThrowConstraintViolationExceptionWhenFirstNameExceedsLimits(String firstName) {
        //Given
        User user = new User(firstName, "Doe");
        //Then
        assertThrows(ConstraintViolationException.class, () -> userService.save(user));
    }


    @ParameterizedTest
    @ValueSource(strings = {"", "Hubert Blaine Wolfeschlegelsteinhausenbergerdorff Sr."})
    public void shouldSaveThrowConstraintViolationExceptionWhenLastNameExceedsLimits(String lastName) {
        //Given
        User user = new User("John", lastName);
        //Then
        assertThrows(ConstraintViolationException.class, () -> userService.save(user));
    }

    @Test
    @Transactional
    public void shouldSavePersistNotNullUser() {
        //Given
        User gillianAnderson = new User("Gillian", "Anderson");
        gillianAnderson.addRole(UserRole.USER);
        //When
        User savedUser = userService.save(gillianAnderson);
        User savedUserFromDB = entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.roles WHERE u.id = :id", User.class).setParameter("id", savedUser.getId()).getSingleResult();
        //Then
        assertNotNull(savedUserFromDB);
        assertEquals("Gillian", savedUserFromDB.getFirstName());
        assertEquals("Anderson", savedUserFromDB.getLastName());
        assertEquals(1, gillianAnderson.getRoles().size());
        assertTrue(gillianAnderson.getRoles().contains(UserRole.USER));
    }

    @Test
    public void shouldUpdateThrowInvalidDataAccessApiUsageExceptionWhenIdIsNull() {
        //Given
        User davidBloom = new User("David", "Bloom");
        //Then
        InvalidDataAccessApiUsageException exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> userService.update(null, davidBloom));
        assertEquals("The given id must not be null!; nested exception is java.lang.IllegalArgumentException: The given id must not be null!", exception.getMessage());
    }

    @Test
    public void shouldUpdateThrowEntityNotFoundExceptionWhenIdNotFound() {
        //Given
        User richardBlame = new User("Richard", "Blame");
        //Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> userService.update(50L, richardBlame));
        assertEquals("Entity com.company.structureinventorysystem.domain.user.User with id 50 not found", exception.getMessage());
    }

    @Test
    public void shouldUpdateThrowIllegalArgumentExceptionWhenUserIsNull() {
        //Given
        Long id = 3L;
        //Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.update(id, null));
        assertEquals("Updated state must not null", exception.getMessage());
        User userFromDB = entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.roles WHERE u.id = :id", User.class).setParameter("id", id).getSingleResult();
        assertEquals(3, userFromDB.getId());
        assertEquals("Ann", userFromDB.getFirstName());
        assertEquals("McDonald", userFromDB.getLastName());
        assertEquals(1, userFromDB.getRoles().size());
        assertTrue(userFromDB.getRoles().contains(UserRole.USER));
    }

    @Test
    @Transactional
    public void shouldUpdateReplaceStateOfUserWithRoles() {
        //Given
        Long id = 4L;
        User johnDoe = new User("John", "Doe");
        johnDoe.addRole(UserRole.VISITOR);
        //When
        userService.update(id, johnDoe);
        User updatedUserFromDB = entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.roles WHERE u.id = :id", User.class).setParameter("id", id).getSingleResult();
        //Then
        assertNotNull(updatedUserFromDB);
        assertEquals(id, updatedUserFromDB.getId());
        assertEquals(johnDoe.getFirstName(), updatedUserFromDB.getFirstName());
        assertEquals(johnDoe.getLastName(), updatedUserFromDB.getLastName());
        assertEquals(1, updatedUserFromDB.getRoles().size());
        assertTrue(updatedUserFromDB.getRoles().contains(UserRole.VISITOR));
    }

    @Test
    @Transactional
    public void shouldUpdateReplaceOnlyStateOfUserWhenRolesAreNull() {
        //Given
        Long id = 9L;
        User amirGupta = new User("Amir", "Gupta", "master of engineering");
        List<UserRole> roles = Stream.of(UserRole.VISITOR, UserRole.USER, UserRole.ADMIN).collect(Collectors.toList());
        //When
        userService.update(id, amirGupta);
        User updatedUserFromDB = entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.roles WHERE u.id = :id", User.class).setParameter("id", id).getSingleResult();
        //Then
        assertNotNull(updatedUserFromDB);
        assertEquals(id, updatedUserFromDB.getId());
        assertEquals(amirGupta.getFirstName(), updatedUserFromDB.getFirstName());
        assertEquals(amirGupta.getLastName(), updatedUserFromDB.getLastName());
        assertEquals(amirGupta.getAcademicDegree(), updatedUserFromDB.getAcademicDegree());
        assertEquals(3, updatedUserFromDB.getRoles().size());
        assertTrue(updatedUserFromDB.getRoles().containsAll(roles));
    }

    @Test
    public void shouldRemoveByIdThrowInvalidDataAccessApiUsageExceptionWhenIdIsNull() {
        InvalidDataAccessApiUsageException exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> userService.removeById(null));
        assertEquals("The given id must not be null!; nested exception is java.lang.IllegalArgumentException: The given id must not be null!", exception.getMessage());
    }

    @Test
    public void shouldRemoveByIdThrowEntityNotFoundExceptionWhenIdNotFound() {
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> userService.removeById(1000L));
        assertEquals("Entity com.company.structureinventorysystem.domain.user.User with id 1000 not found", exception.getMessage());
    }

    @Test
    @Transactional
    public void shouldRemoveByIdEraseUserFromDB() {
        //Given
        long id_3 = 3L;
        long id_10 = 10L;
        //When
        userService.removeById(id_3);
        userService.removeById(id_10);
        List<User> users = entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.roles WHERE u.id = :firstId OR u.id = :secondId", User.class)
                .setParameter("firstId", id_3)
                .setParameter("secondId", id_10).getResultList();
        List<Long> ids = entityManager.createNativeQuery("SELECT user_id FROM structureinventorysystem.user_roles r WHERE r.user_id = :firstId OR r.user_id = :secondId")
                .setParameter("firstId", id_3)
                .setParameter("secondId", id_10)
                .getResultList();
        //Then
        assertNotNull(users);
        assertEquals(0, users.size());
        assertNotNull(ids);
        assertEquals(0, ids.size());
    }

}
