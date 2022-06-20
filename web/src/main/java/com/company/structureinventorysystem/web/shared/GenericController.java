package com.company.structureinventorysystem.web.shared;

import com.company.structureinventorysystem.domain.shared.UpdateEntity;
import com.company.structureinventorysystem.service.GenericService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

public abstract class GenericController<T extends UpdateEntity<T>> {

    private final GenericService<T> service;

    public GenericController(GenericService<T> service) {
        this.service = service;
    }

    @PostMapping(value = "")
    public ResponseEntity<String> create(@RequestHeader(name = "Accept-Language", defaultValue = "en") String lang, @Valid @RequestBody T body, BindingResult validationResults) {
        String validationMessage = validationMessage(validationResults);
        if (validationMessage != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationMessage);
        }
        service.save(body);
        return ResponseEntity.status(HttpStatus.CREATED).body("Entity was created");
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<T> getById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
    }

    @GetMapping(value = "")
    public ResponseEntity<List<T>> getAll(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                          @RequestParam(name = "pageSize", defaultValue = "50") Integer pageSize,
                                          @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                          @RequestParam(name = "dir", required = false) String dir) {
        List<T> entities = service.getAll(pageNo, pageSize, sortBy, dir);
        return ResponseEntity.status(HttpStatus.OK).body(entities);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") Long id, @Valid @RequestBody T body, BindingResult validationResults) {
        String validationMessage = validationMessage(validationResults);
        if (validationMessage != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationMessage);
        }
        service.update(id, body);
        return ResponseEntity.status(HttpStatus.OK).body("Entity was updated");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<T> deleteById(@PathVariable(name = "id") Long id) {
        service.removeById(id);
        return ResponseEntity.noContent().build();
    }

    protected String validationMessage(BindingResult validationResults) {
        return validationResults.hasErrors() ? "Error validation message: " + validationResults.getAllErrors().
                stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(";")) : null;
    }

}
