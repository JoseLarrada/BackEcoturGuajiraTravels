package com.ecotur.guajira.backend.Services;

import com.ecotur.guajira.backend.Entities.DestinationData;
import com.ecotur.guajira.backend.Entities.Dto.ResponseTour;
import com.ecotur.guajira.backend.Repository.DestinationDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DestinationDaaService {
    private final DestinationDataRepository destinationDataRepository;

    private static final String UPLOAD_DIR = "uploads/";

    public ResponseEntity<?> createDestination(DestinationData destinationData,
                                               MultipartFile mainImage,
                                               MultipartFile[] galleryImages) {
        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) dir.mkdirs();

            // 🔹 Guardar mainImage
            if (mainImage != null && !mainImage.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + mainImage.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.write(path, mainImage.getBytes());
                destinationData.setMainImage("/uploads/" + fileName);
            }

            // 🔹 Guardar gallery
            if (galleryImages != null && galleryImages.length > 0 && destinationData.getGallery() != null) {
                List<DestinationData.GalleryItem> gallery = destinationData.getGallery();
                for (int i = 0; i < galleryImages.length && i < gallery.size(); i++) {
                    MultipartFile file = galleryImages[i];
                    if (!file.isEmpty()) {
                        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                        Path path = Paths.get(UPLOAD_DIR + fileName);
                        Files.write(path, file.getBytes());
                        gallery.get(i).setUrl("/uploads/" + fileName); // reemplaza el url temporal
                    }
                }
            }

            destinationDataRepository.save(destinationData);
            return ResponseEntity.ok(destinationData);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    public ResponseEntity<ResponseTour> updateDestination(DestinationData destinationData) {
        try {
            if(destinationDataRepository.findById(destinationData.getId()).isPresent()) {
                destinationDataRepository.save(destinationData);
                return ResponseEntity.ok(new ResponseTour(destinationData.getName(),"Actualizado correctamente"));
            }
            return ResponseEntity.notFound().build();
        }catch(Exception e) {
            return ResponseEntity.badRequest().body(new ResponseTour(destinationData.getName(),e.getMessage()));
        }
    }

    public ResponseEntity<ResponseTour> deleteDestination(String id) {
        try {
            destinationDataRepository.deleteById(id);
            return ResponseEntity.ok(new ResponseTour(null,"Eliminado correctamente"));
        }catch(Exception e) {
            return ResponseEntity.badRequest().body(new ResponseTour(null,e.getMessage()));
        }
    }

    public ResponseEntity<List<DestinationData>> getAllDestinationData(){
        return  ResponseEntity.ok(destinationDataRepository.findAll());
    }

}
