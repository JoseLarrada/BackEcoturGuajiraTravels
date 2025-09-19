package com.ecotur.guajira.backend.Repository;

import com.ecotur.guajira.backend.Entities.TourOption;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourOptionRepository extends MongoRepository<TourOption, String> {
}
