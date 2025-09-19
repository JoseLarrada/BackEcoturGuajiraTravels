package com.ecotur.guajira.backend.Repository;

import com.ecotur.guajira.backend.Entities.DestinoItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinoItemRepository extends MongoRepository<DestinoItem, String> {
}
