package com.ecotur.guajira.backend.Services;

import com.ecotur.guajira.backend.Entities.Dto.ResponseTour;
import com.ecotur.guajira.backend.Entities.Slide;
import com.ecotur.guajira.backend.Repository.SlideRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class SlideService extends GenericService<Slide, String> {

    private final FileStorageService fileStorageService;

    public SlideService(SlideRepository slideRepository, FileStorageService fileStorageService) {
        super(slideRepository);
        this.fileStorageService = fileStorageService;
    }

    public ResponseEntity<?> addSlide(String slideJson, MultipartFile image) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Slide slide = mapper.readValue(slideJson, Slide.class);

            if (image != null && !image.isEmpty()) {
                String imageUrl = fileStorageService.saveFile(image);
                slide.setImage(imageUrl);
            }

            repository.save(slide);
            return ResponseEntity.ok(slide);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    public ResponseEntity<ResponseTour> updateSlide(String id, String slideJson, MultipartFile image) {
        try {
            Optional<Slide> existingOpt = repository.findById(id);
            if (existingOpt.isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseTour(null, "No se encontró el slide"));
            }

            ObjectMapper mapper = new ObjectMapper();
            Slide updates = mapper.readValue(slideJson, Slide.class);

            Slide existing = existingOpt.get();

            // 🔹 Actualizar campos básicos
            existing.setTitle(updates.getTitle());
            existing.setDescription(updates.getDescription());
            existing.setLocation(updates.getLocation());
            existing.setDuration(updates.getDuration());
            existing.setCapacity(updates.getCapacity());
            existing.setPrice(updates.getPrice());

            // 🔹 Si viene nueva imagen, reemplazar
            if (image != null && !image.isEmpty()) {
                existing.setImage(fileStorageService.saveFile(image));
            }

            repository.save(existing);

            return ResponseEntity.ok(new ResponseTour(existing.getTitle(), "Slide actualizado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseTour(null, e.getMessage()));
        }
    }
    public ResponseEntity<ResponseTour> deleteSlide(String id) {
        return delete(id);
    }
    public ResponseEntity<List<Slide>> getAllSlide() {
        return getAll();
    }
}
