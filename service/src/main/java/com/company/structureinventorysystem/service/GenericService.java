package com.company.structureinventorysystem.service;

import com.company.structureinventorysystem.domain.shared.SortDirection;
import com.company.structureinventorysystem.domain.shared.UpdateEntity;
import com.company.structureinventorysystem.infrastructure.repository.GenericRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericService<T extends UpdateEntity<T>> {

    private final GenericRepository<T> repository;

    public GenericService(GenericRepository<T> repository) {
        this.repository = repository;
    }

    public T getById(Long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<T> getAll(Integer pageNo, Integer pageSize, String sortBy, String dir) {
        Sort sort = Sort.by(sortBy);
        if (SortDirection.DESC.getValue().equals(dir)) {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<T> pageEntities = repository.findAll(pageable);
        if (pageEntities.hasContent()) {
            return pageEntities.getContent();
        }
        return new ArrayList<>();
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
