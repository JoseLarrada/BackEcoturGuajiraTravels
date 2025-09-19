package com.ecotur.guajira.backend.Controller;

import com.ecotur.guajira.backend.Entities.DestinoItem;
import com.ecotur.guajira.backend.Entities.Dto.ResponseTour;
import com.ecotur.guajira.backend.Services.DestinoItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/destino-item")
public class DestinoItemController {
    private final DestinoItemService destinoItemService;

    @GetMapping
    public ResponseEntity<List<DestinoItem>> getAll() {
        return destinoItemService.getAllDestinoItem();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addDestinoItem(
            @RequestPart("destinoItem") String destinoItemJson,
            @RequestPart(value = "images", required = false) MultipartFile[] images) {

        return destinoItemService.addDestinoItem(destinoItemJson, images);
    }

    @PutMapping
    public ResponseEntity<ResponseTour> updateDestinoItem(@RequestBody DestinoItem destinoItem) {
        return destinoItemService.updateDestinoItem(destinoItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTour> deleteDestinoItem(@PathVariable String id) {
        return destinoItemService.deleteDestinoItem(id);
    }
}
