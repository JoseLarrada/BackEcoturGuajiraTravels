package com.ecotur.guajira.backend.Services;

import com.ecotur.guajira.backend.Entities.DestinationData;
import com.ecotur.guajira.backend.Entities.Dto.ResponseTour;
import com.ecotur.guajira.backend.Repository.DestinationDataRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DestinationDaaService {
    private final DestinationDataRepository destinationDataRepository;

    private static final String UPLOAD_DIR = "uploads/";
    @Autowired
    private FileStorageService fileStorageService;

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

    public ResponseEntity<?> updateDestination(String id,
                                               String destinationJson,
                                               MultipartFile mainImage,
                                               MultipartFile[] galleryImages,
                                               String keptGalleryJson,
                                               String keptMainImageJson) {
        try {
            Optional<DestinationData> existingOpt = destinationDataRepository.findById(id);
            if (existingOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            ObjectMapper mapper = new ObjectMapper();
            DestinationData incoming = mapper.readValue(destinationJson, DestinationData.class);

            DestinationData existing = existingOpt.get();

            // 🔹 Actualizar campos simples
            existing.setName(incoming.getName());
            existing.setType(incoming.getType());
            existing.setExperiences(incoming.getExperiences());
            existing.setBestTime(incoming.getBestTime());
            existing.setIcon(incoming.getIcon());
            existing.setDescription(incoming.getDescription());
            existing.setHistory(incoming.getHistory());
            existing.setHighlights(incoming.getHighlights());
            existing.setActivities(incoming.getActivities());

            // 🔹 Imagen principal
            if (mainImage != null && !mainImage.isEmpty()) {
                // Caso: reemplazo
                String url = fileStorageService.saveFile(mainImage);
                existing.setMainImage(url);
            } else if (keptMainImageJson != null && !keptMainImageJson.trim().isEmpty()) {
                // Caso: conservar
                String kept = mapper.readValue(keptMainImageJson, String.class);
                existing.setMainImage(kept);
            } else {
                // Caso: eliminar
                existing.setMainImage(null);
            }

            // 🔹 Galería con conservación
            existing.setGallery(getUpdatedGallery(galleryImages, keptGalleryJson, incoming));

            destinationDataRepository.save(existing);
            return ResponseEntity.ok(existing);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private List<DestinationData.GalleryItem> getUpdatedGallery(MultipartFile[] galleryImages,
                                                                String keptGalleryJson,
                                                                DestinationData incoming) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<DestinationData.GalleryItem> finalGallery = new ArrayList<>();

        // 1. Conservar imágenes previas (manteniendo url + caption)
        if (keptGalleryJson != null && !keptGalleryJson.trim().isEmpty()) {
            List<DestinationData.GalleryItem> kept = mapper.readValue(
                    keptGalleryJson,
                    new TypeReference<List<DestinationData.GalleryItem>>() {}
            );
            finalGallery.addAll(kept);
        }

        // 2. Agregar nuevas imágenes con sus captions
        if (galleryImages != null && galleryImages.length > 0 && incoming.getGallery() != null) {
            List<DestinationData.GalleryItem> incomingGallery = incoming.getGallery();
            for (int i = 0; i < galleryImages.length && i < incomingGallery.size(); i++) {
                MultipartFile file = galleryImages[i];
                if (file != null && !file.isEmpty()) {
                    String url = fileStorageService.saveFile(file);
                    // asigno url y conservo el caption que viene en el JSON
                    DestinationData.GalleryItem item = new DestinationData.GalleryItem();
                    item.setUrl(url);
                    item.setCaption(incomingGallery.get(i).getCaption());
                    finalGallery.add(item);
                }
            }
        }

        return finalGallery;
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
