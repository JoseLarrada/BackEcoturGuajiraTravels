package com.ecotur.guajira.backend.Services;

import com.ecotur.guajira.backend.Entities.DestinoItem;
import com.ecotur.guajira.backend.Entities.Dto.ResponseTour;
import com.ecotur.guajira.backend.Repository.DestinoItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DestinoItemService extends GenericService<DestinoItem, String> {

    private final FileStorageService fileStorageService;

    public DestinoItemService(DestinoItemRepository destinoItemRepository,
                              FileStorageService fileStorageService) {
        super(destinoItemRepository);
        this.fileStorageService = fileStorageService;
    }

    public ResponseEntity<List<DestinoItem>> getAllDestinoItem(){
        return  getAll();
    }

    public ResponseEntity<?> addDestinoItem(String destinoItemJson, MultipartFile[] images) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            DestinoItem destinoItem = mapper.readValue(destinoItemJson, DestinoItem.class);

            return getResponseEntity(images, destinoItem);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    public ResponseEntity<?> updateDestinoItem(String id,
                                               String destinoItemJson,
                                               MultipartFile[] images,
                                               String keptImagesJson) {
        try {
            Optional<DestinoItem> existingOpt = repository.findById(id);
            if (existingOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            ObjectMapper mapper = new ObjectMapper();
            DestinoItem destinoItemUpdates = mapper.readValue(destinoItemJson, DestinoItem.class);

            DestinoItem existing = existingOpt.get();

            // Actualizar campos simples
            existing.setTitle(destinoItemUpdates.getTitle());
            existing.setPhrase(destinoItemUpdates.getPhrase());
            existing.setColor(destinoItemUpdates.getColor());
            existing.setIcon(destinoItemUpdates.getIcon());

            // Actualizar imágenes con conservación
            return getResponseEntityWithKeptImages(images, existing, keptImagesJson, mapper);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    private ResponseEntity<?> getResponseEntityWithKeptImages(MultipartFile[] images,
                                                              DestinoItem existing,
                                                              String keptImagesJson,
                                                              ObjectMapper mapper) throws IOException {
        List<String> finalImages = new ArrayList<>();

        // 1. Agregar imágenes conservadas
        if (keptImagesJson != null && !keptImagesJson.trim().isEmpty()) {
            List<String> keptImages = mapper.readValue(keptImagesJson, List.class);
            finalImages.addAll(keptImages);
        }

        // 2. Agregar nuevas imágenes
        if (images != null && images.length > 0) {
            for (MultipartFile file : images) {
                if (file != null && !file.isEmpty()) {
                    String newImageUrl = fileStorageService.saveFile(file);
                    finalImages.add(newImageUrl);
                }
            }
        }

        existing.setImages(finalImages);
        repository.save(existing);
        return ResponseEntity.ok(existing);
    }

    private ResponseEntity<?> getResponseEntity(MultipartFile[] images, DestinoItem existing) throws IOException {
        if (images != null && images.length > 0) {
            List<String> urls = new ArrayList<>();
            for (MultipartFile file : images) {
                if (file != null && !file.isEmpty()) {
                    urls.add(fileStorageService.saveFile(file));
                }
            }
            existing.setImages(urls);
        }

        repository.save(existing);
        return ResponseEntity.ok(existing);
    }

    public ResponseEntity<ResponseTour> deleteDestinoItem(String id){
        return delete(id);
    }
}
