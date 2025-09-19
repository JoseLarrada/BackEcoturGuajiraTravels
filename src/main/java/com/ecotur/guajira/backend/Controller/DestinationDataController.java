package com.ecotur.guajira.backend.Controller;

import com.ecotur.guajira.backend.Entities.DestinationData;
import com.ecotur.guajira.backend.Entities.Dto.ResponseTour;
import com.ecotur.guajira.backend.Services.DestinationDaaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/destination-data")
public class DestinationDataController {
    private final DestinationDaaService destinationDaaService;

    @GetMapping
    public ResponseEntity<List<DestinationData>> getAll() {
        return destinationDaaService.getAllDestinationData();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createDestination(
            @RequestPart("destinationData") String destinationDataJson,
            @RequestPart(value = "mainImage", required = false) MultipartFile mainImage,
            @RequestPart(value = "galleryImages", required = false) MultipartFile[] galleryImages) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            DestinationData destinationData = mapper.readValue(destinationDataJson, DestinationData.class);

            return destinationDaaService.createDestination(destinationData, mainImage, galleryImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error parseando JSON: " + e.getMessage());
        }
    }


    @PutMapping
    public ResponseEntity<ResponseTour> updateTour(@RequestBody DestinationData destinationData) {
        return destinationDaaService.updateDestination(destinationData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTour> deleteTour(@PathVariable String id) {
        return destinationDaaService.deleteDestination(id);
    }

}
