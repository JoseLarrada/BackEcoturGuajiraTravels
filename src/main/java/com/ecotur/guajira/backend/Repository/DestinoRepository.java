package com.ecotur.guajira.backend.Repository;

import com.ecotur.guajira.backend.Entities.Destino;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinoRepository extends MongoRepository<Destino, String> {
}
