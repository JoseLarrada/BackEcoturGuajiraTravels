package com.ecotur.guajira.backend.Repository;

import com.ecotur.guajira.backend.Entities.Tour;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends MongoRepository<Tour, String> {
}
