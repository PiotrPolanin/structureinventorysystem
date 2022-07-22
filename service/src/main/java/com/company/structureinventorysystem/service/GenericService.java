package com.company.structureinventorysystem.service;

import com.company.structureinventorysystem.domain.shared.SortDirection;
import com.company.structureinventorysystem.domain.shared.UpdateEntity;
import com.company.structureinventorysystem.infrastructure.repository.GenericRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class GenericService<T extends UpdateEntity<T>> {

    protected static final String DEFAULT_ERROR_MESSAGE_FOR_NULL = "Each parameter must not be null";
    protected final Class<T> entityClass;
    protected final GenericRepository<T> repository;

    public GenericService(Class<T> entityClass, GenericRepository<T> repository) {
        this.entityClass = entityClass;
        this.repository = repository;
    }

    protected void validateNullValues(String message, Object... objects) {
        for (Object o : objects) {
            if (o == null) {
                throw new IllegalArgumentException(message);
            }
        }
    }

    public T getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Entity %s with id %s not found", entityClass.getName(), id)));
    }

    public List<T> getAll(Integer pageNo, Integer pageSize, String sortBy, String dir) {
        validateNullValues(DEFAULT_ERROR_MESSAGE_FOR_NULL, pageNo, pageSize, sortBy, dir);
        if (!sortBy.isEmpty() && !dir.isEmpty()) {
            Sort sort = Sort.by(sortBy.trim());
            if (SortDirection.DESC.getValue().equals(dir.trim().toLowerCase(Locale.ROOT))) {
                sort = sort.descending();
            }
            Page<T> pageEntities = repository.findAll(PageRequest.of(pageNo, pageSize, sort));
            return pageEntities.hasContent() ? pageEntities.getContent() : new ArrayList<>();
        }
        throw new IllegalArgumentException("Parameter sortBy and dir must not be empty");
    }

    @Transactional
    public T save(T entity) {
        return repository.save(entity);
    }

    @Transactional
    public T update(Long id, T entity) {
        T dbEntity = getById(id);
        dbEntity.update(entity);
        return repository.save(dbEntity);
    }

    @Transactional
    public void removeById(Long id) {
        T dbEntity = getById(id);
        repository.delete(dbEntity);
    }

}
