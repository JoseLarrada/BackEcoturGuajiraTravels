package com.ecotur.guajira.backend.Services;

import com.ecotur.guajira.backend.Entities.Dto.ResponseTour;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.function.Function;

public abstract class GenericService<T, ID> {

    protected final MongoRepository<T, ID> repository;

    public GenericService(MongoRepository<T, ID> repository) {
        this.repository = repository;
    }


    public ResponseEntity<ResponseTour> add(T entity) {
        try {
            repository.save(entity);
            return ResponseEntity.ok(new ResponseTour(null, "Creado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<ResponseTour> update(T entity, Function<T, ID> getIdFunction) {
        try {
            ID id = getIdFunction.apply(entity);
            if (repository.findById(id).isPresent()) {
                repository.save(entity);
                return ResponseEntity.ok(new ResponseTour(null, "Actualizado correctamente"));
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<ResponseTour> delete(ID id) {
        try {
            if (repository.findById(id).isPresent()) {
                repository.deleteById(id);
                return ResponseEntity.ok(new ResponseTour(null, "Eliminado correctamente"));
            }
            return ResponseEntity.badRequest().body(new ResponseTour(null, "No se encontró el elemento"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseTour(null, e.getMessage()));
        }
    }

    public ResponseEntity<List<T>> getAll() {
        try {
            return ResponseEntity.ok(repository.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
