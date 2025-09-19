package com.ecotur.guajira.backend.Services;

import com.ecotur.guajira.backend.Entities.Dto.ResponseTour;
import com.ecotur.guajira.backend.Entities.Slide;
import com.ecotur.guajira.backend.Repository.SlideRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    public ResponseEntity<ResponseTour> updateSlide(Slide slide) {
        return update(slide,Slide::getId);
    }
    public ResponseEntity<ResponseTour> deleteSlide(String id) {
        return delete(id);
    }
    public ResponseEntity<List<Slide>> getAllSlide() {
        return getAll();
    }
}
