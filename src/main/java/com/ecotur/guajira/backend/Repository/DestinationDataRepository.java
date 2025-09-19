package com.ecotur.guajira.backend.Repository;

import com.ecotur.guajira.backend.Entities.DestinationData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationDataRepository extends MongoRepository<DestinationData, String> {
}
