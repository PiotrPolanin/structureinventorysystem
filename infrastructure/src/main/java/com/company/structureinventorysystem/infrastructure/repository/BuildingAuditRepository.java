package com.company.structureinventorysystem.infrastructure.repository;

import com.company.structureinventorysystem.domain.audit.BuildingAudit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildingAuditRepository extends GenericRepository<BuildingAudit> {

    @Query("SELECT ba FROM BuildingAudit ba ORDER BY ba.createdOn ASC")
    List<BuildingAudit> findAllByCreatedOnAscOrder(Pageable pageable);

    @Query("SELECT ba FROM BuildingAudit ba ORDER BY ba.createdOn DESC")
    List<BuildingAudit> findAllByCreatedOnDescOrder(Pageable pageable);

    @Query("SELECT ba FROM BuildingAudit ba ORDER BY ba.updatedOn ASC")
    List<BuildingAudit> findAllByUpdatedOnAscOrder(Pageable pageable);

    @Query("SELECT ba FROM BuildingAudit ba ORDER BY ba.updatedOn DESC")
    List<BuildingAudit> findAllByUpdatedOnDescOrder(Pageable pageable);

}
