package com.ecotur.guajira.backend.Repository;

import com.ecotur.guajira.backend.Entities.ToursPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToursPriceRepository extends MongoRepository<ToursPrice, String> {
}
