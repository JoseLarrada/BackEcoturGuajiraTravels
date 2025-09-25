package com.ecotur.guajira.backend.Services;

import com.ecotur.guajira.backend.Entities.Dto.ResponseTour;
import com.ecotur.guajira.backend.Entities.Testimonial;
import com.ecotur.guajira.backend.Repository.TestimonialRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

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

    public ResponseEntity<ResponseTour> updateTestimonial(String id, String testimonialJson, MultipartFile image) {
        try {
            Optional<Testimonial> existingOpt = repository.findById(id);
            if (existingOpt.isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseTour(null, "No se encontró el testimonial"));
            }

            ObjectMapper mapper = new ObjectMapper();
            Testimonial updates = mapper.readValue(testimonialJson, Testimonial.class);

            Testimonial existing = existingOpt.get();

            // 🔹 Actualizar campos básicos
            existing.setName(updates.getName());
            existing.setLocation(updates.getLocation());
            existing.setRating(updates.getRating());
            existing.setText(updates.getText());

            // 🔹 Actualizar imagen solo si llega nueva
            if (image != null && !image.isEmpty()) {
                existing.setImage(fileStorageService.saveFile(image));
            }

            repository.save(existing);

            return ResponseEntity.ok(new ResponseTour(existing.getName(), "Testimonial actualizado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseTour(null, e.getMessage()));
        }
    }
    public ResponseEntity<ResponseTour> deleteTestimonial(String id) {
        return delete(id);
    }
    public ResponseEntity<List<Testimonial>> getAllTestimonial() {
        return getAll();
    }

}
