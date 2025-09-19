package com.ecotur.guajira.backend.Repository;

import com.ecotur.guajira.backend.Entities.Testimonial;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestimonialRepository extends MongoRepository<Testimonial, String> {
}
