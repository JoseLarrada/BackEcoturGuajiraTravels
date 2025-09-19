package com.ecotur.guajira.backend.Services;

import com.ecotur.guajira.backend.Entities.DestinoItem;
import com.ecotur.guajira.backend.Entities.Dto.ResponseTour;
import com.ecotur.guajira.backend.Repository.DestinoItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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

            if (images != null && images.length > 0) {
                List<String> urls = new ArrayList<>();
                for (MultipartFile file : images) {
                    if (file != null && !file.isEmpty()) {
                        urls.add(fileStorageService.saveFile(file));
                    }
                }
                destinoItem.setImages(urls);
            }

            repository.save(destinoItem);
            return ResponseEntity.ok(destinoItem);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    public ResponseEntity<ResponseTour> updateDestinoItem(DestinoItem destinoItem){
        return update(destinoItem, DestinoItem::getId);
    }

    public ResponseEntity<ResponseTour> deleteDestinoItem(String id){
        return delete(id);
    }
}
