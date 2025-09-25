package com.ecotur.guajira.backend.Services;


import com.ecotur.guajira.backend.Entities.Dto.ResponseTour;
import com.ecotur.guajira.backend.Entities.Tour;
import com.ecotur.guajira.backend.Repository.TourRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class TourService extends GenericService<Tour, String> {
    private final FileStorageService fileStorageService;

    public TourService(TourRepository repository, FileStorageService fileStorageService) {
        super(repository);
        this.fileStorageService = fileStorageService;
    }

    public ResponseEntity<List<Tour>> getAllTours() {
        return getAll();
    }

    public ResponseEntity<ResponseTour> addTour(String tourJson, MultipartFile image) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Tour tour = mapper.readValue(tourJson, Tour.class);

            if (image != null && !image.isEmpty()) {
                String imageUrl = fileStorageService.saveFile(image);
                tour.setImage(imageUrl);
            }

            repository.save(tour);
            return ResponseEntity.ok(new ResponseTour(tour.getTitle(),"Tour Creado correctamente"));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseTour("Error al cargar imagen",e.getMessage()));
        }
    }

    public ResponseEntity<ResponseTour> updateTour(String id, String tourJson, MultipartFile image) {
        try {
            Optional<Tour> existingOpt = repository.findById(id);
            if (existingOpt.isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseTour(null, "No se encontró el tour"));
            }

            ObjectMapper mapper = new ObjectMapper();
            Tour updates = mapper.readValue(tourJson, Tour.class);

            Tour existing = existingOpt.get();

            // 🔹 Actualizar campos básicos
            existing.setTitle(updates.getTitle());
            existing.setDuration(updates.getDuration());
            existing.setPrice(updates.getPrice());
            existing.setRating(updates.getRating());
            existing.setBadge(updates.getBadge());
            existing.setBadgeColor(updates.getBadgeColor());
            existing.setDescription(updates.getDescription());

            // 🔹 Imagen opcional
            if (image != null && !image.isEmpty()) {
                existing.setImage(fileStorageService.saveFile(image));
            }

            repository.save(existing);

            return ResponseEntity.ok(new ResponseTour(existing.getTitle(), "Tour actualizado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseTour(null, e.getMessage()));
        }
    }

    public ResponseEntity<ResponseTour> deleteTour(String id) {
        return delete(id);
    }

}
