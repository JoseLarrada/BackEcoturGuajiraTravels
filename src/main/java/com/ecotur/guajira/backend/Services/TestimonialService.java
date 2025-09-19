package com.ecotur.guajira.backend.Services;

import com.ecotur.guajira.backend.Entities.Dto.ResponseTour;
import com.ecotur.guajira.backend.Entities.Testimonial;
import com.ecotur.guajira.backend.Repository.TestimonialRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class TestimonialService extends GenericService<Testimonial, String> {
    private final FileStorageService fileStorageService;

    public TestimonialService(TestimonialRepository testimonialRepository, FileStorageService fileStorageService) {
        super(testimonialRepository);
        this.fileStorageService = fileStorageService;
    }

    public ResponseEntity<ResponseTour> addTestimonial(String testimonialJson, MultipartFile image) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Testimonial testimonial = mapper.readValue(testimonialJson, Testimonial.class);

            if (image != null && !image.isEmpty()) {
                String imageUrl = fileStorageService.saveFile(image);
                testimonial.setImage(imageUrl);
            }

            repository.save(testimonial);
            return ResponseEntity.ok(new ResponseTour(testimonial.getName(), "Reseña creada correctamente"));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseTour("testimonial", e.getMessage()));
        }
    }

    public ResponseEntity<ResponseTour> updateTestimonial(Testimonial testimonial) {
        return update(testimonial,Testimonial::getId);
    }
    public ResponseEntity<ResponseTour> deleteTestimonial(String id) {
        return delete(id);
    }
    public ResponseEntity<List<Testimonial>> getAllTestimonial() {
        return getAll();
    }

}
