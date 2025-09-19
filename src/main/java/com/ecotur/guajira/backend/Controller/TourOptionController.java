package com.ecotur.guajira.backend.Controller;

import com.ecotur.guajira.backend.Entities.Dto.ResponseTour;
import com.ecotur.guajira.backend.Entities.TourOption;
import com.ecotur.guajira.backend.Services.TourOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/tour-option")
public class TourOptionController {
    private final TourOptionService tourOptionService;

    @PostMapping
    public ResponseEntity<ResponseTour> addSTourOption(@RequestBody TourOption tourOption) {
        return tourOptionService.addSTourOption(tourOption);
    }
    @PutMapping
    public ResponseEntity<ResponseTour> updateTourOption(@RequestBody TourOption tourOption) {
        return tourOptionService.updateTourOption(tourOption);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTour> deleteTourOption(@PathVariable String id) {
        return tourOptionService.deleteTourOption(id);
    }
    @GetMapping
    public ResponseEntity<List<TourOption>> getAllTourOption() {
        return tourOptionService.getAllTourOption();
    }

}
