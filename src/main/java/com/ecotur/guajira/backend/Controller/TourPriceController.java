package com.ecotur.guajira.backend.Controller;

import com.ecotur.guajira.backend.Entities.Dto.ResponseTour;
import com.ecotur.guajira.backend.Entities.ToursPrice;
import com.ecotur.guajira.backend.Services.TourPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/tour-price")
public class TourPriceController {
    private final TourPriceService tourPriceService;

    @GetMapping
    public ResponseEntity<List<ToursPrice>> getAllToursPrice() {
        return tourPriceService.getAllToursPrice();
    }

    @PostMapping
    public ResponseEntity<ResponseTour> addToursPrice(@RequestBody ToursPrice tour) {
        return tourPriceService.addToursPrice(tour);
    }

    @PutMapping
    public ResponseEntity<ResponseTour> updateToursPrice(@RequestBody ToursPrice tour) {
        return tourPriceService.updateToursPrice(tour);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTour> deleteToursPrice(@PathVariable String id) {
        return tourPriceService.deleteToursPrice(id);
    }

}
