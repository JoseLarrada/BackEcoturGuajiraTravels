package com.ecotur.guajira.backend.Repository;

import com.ecotur.guajira.backend.Entities.Slide;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlideRepository extends MongoRepository<Slide, String> {
}
