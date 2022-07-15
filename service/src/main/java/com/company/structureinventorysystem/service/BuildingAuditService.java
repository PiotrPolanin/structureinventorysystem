package com.company.structureinventorysystem.service;

import com.company.structureinventorysystem.domain.audit.BuildingAudit;
import com.company.structureinventorysystem.domain.shared.SortDirection;
import com.company.structureinventorysystem.infrastructure.repository.BuildingAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class BuildingAuditService extends GenericService<BuildingAudit> {

    private static final String ERROR_MESSAGE_FOR_INEQUALITY_OF_DATES = "The date of update %s for the attribute updatedBy must not be before the date of creation %s for the attribute createdBy";
    private final BuildingAuditRepository buildingAuditRepository;

    @Autowired
    public BuildingAuditService(BuildingAuditRepository repository) {
        super(BuildingAudit.class, repository);
        this.buildingAuditRepository = repository;
    }

    @Override
    @Transactional
    public BuildingAudit save(BuildingAudit audit) {
        validateNullValues(String.format("Entity %s must not be null", BuildingAudit.class.getName()), audit);
        if (audit.isUpdatedBeforeCreatedOn()) {
            throw new IllegalStateException(String.format(ERROR_MESSAGE_FOR_INEQUALITY_OF_DATES, audit.getUpdatedOn(), audit.getCreatedOn()));
        } else {
            return repository.save(audit);
        }
    }

    @Override
    @Transactional
    public BuildingAudit update(Long id, BuildingAudit audit) {
        validateNullValues(DEFAULT_ERROR_MESSAGE_FOR_NULL, id, audit);
        if (audit.isUpdatedBeforeCreatedOn()) {
            throw new IllegalStateException(String.format(ERROR_MESSAGE_FOR_INEQUALITY_OF_DATES, audit.getUpdatedOn(), audit.getCreatedOn()));
        } else {
            BuildingAudit dbAudit = getById(id);
            dbAudit.update(audit);
            return repository.save(dbAudit);
        }
    }

    private List<BuildingAudit> getAllByCreatedOn(Integer pageNo, Integer pageSize, String order) {
        return SortDirection.ASC.toString().equals(order) ? buildingAuditRepository.findAllByCreatedOnAscOrder(PageRequest.of(pageNo, pageSize)) : buildingAuditRepository.findAllByCreatedOnDescOrder(PageRequest.of(pageNo, pageSize));
    }

    private List<BuildingAudit> getAllByUpdatedOn(Integer pageNo, Integer pageSize, String order) {
        return SortDirection.ASC.toString().equals(order) ? buildingAuditRepository.findAllByUpdatedOnAscOrder(PageRequest.of(pageNo, pageSize)) : buildingAuditRepository.findAllByUpdatedOnDescOrder(PageRequest.of(pageNo, pageSize));
    }

    private boolean equalsSortDirectionValue(String order) {
        return SortDirection.ASC.toString().equals(order) | SortDirection.DESC.toString().equals(order);
    }

    @Override
    public List<BuildingAudit> getAll(Integer pageNo, Integer pageSize, String sortBy, String dir) {
        validateNullValues(DEFAULT_ERROR_MESSAGE_FOR_NULL, pageNo, pageSize, sortBy, dir);
        if (!sortBy.isEmpty() && !dir.isEmpty()) {
            Sort sort = Sort.by(sortBy.trim());
            String ucDir = dir.toUpperCase(Locale.ROOT);
            String order = equalsSortDirectionValue(ucDir) ? ucDir : SortDirection.ASC.toString();
            if (SortDirection.DESC.toString().equals(order)) {
                sort = sort.descending();
            }
            List<BuildingAudit> entities;
            switch (sortBy) {
                case "createdOn":
                    entities = getAllByCreatedOn(pageNo, pageSize, order);
                    break;
                case "updatedOn":
                    entities = getAllByUpdatedOn(pageNo, pageSize, order);
                    break;
                default:
                    Page<BuildingAudit> buildingAuditPage = buildingAuditRepository.findAll(PageRequest.of(pageNo, pageSize, sort));
                    entities = buildingAuditPage.hasContent() ? buildingAuditPage.getContent() : new ArrayList<>();
            }
            return entities;
        }
        throw new IllegalArgumentException("Parameter sortBy and dir must not be empty");
    }

}
