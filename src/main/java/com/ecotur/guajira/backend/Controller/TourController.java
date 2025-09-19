package com.ecotur.guajira.backend.Controller;

import com.ecotur.guajira.backend.Entities.Dto.ResponseTour;
import com.ecotur.guajira.backend.Entities.Tour;
import com.ecotur.guajira.backend.Services.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/tour")
public class TourController {
    private final TourService tourService;

    @GetMapping
    public ResponseEntity<List<Tour>> getAllTours() {
        return tourService.getAllTours();
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addTour(
            @RequestPart("tour") String tourJson,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        return tourService.addTour(tourJson, image);
    }
    @PutMapping
    public ResponseEntity<ResponseTour> updateTour(@RequestBody Tour tour) {
        return tourService.updateTour(tour);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTour> deleteTour(@PathVariable String id) {
        return tourService.deleteTour(id);
    }

}
