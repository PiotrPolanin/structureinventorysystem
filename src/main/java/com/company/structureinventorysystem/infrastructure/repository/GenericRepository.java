package com.company.structureinventorysystem.infrastructure.repository;

import com.company.structureinventorysystem.domain.shared.UpdateEntity;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface GenericRepository <T extends UpdateEntity<T>> extends PagingAndSortingRepository<T, Long> {
}
