package com.ecotur.guajira.backend.Controller;

import com.ecotur.guajira.backend.Entities.Dto.ResponseTour;
import com.ecotur.guajira.backend.Entities.Slide;
import com.ecotur.guajira.backend.Services.SlideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/slide")
public class SlideController {
    private final SlideService slideService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addSlide(
            @RequestPart("slide") String slideJson,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        return slideService.addSlide(slideJson, image);
    }

    @PutMapping
    public ResponseEntity<ResponseTour> updateSlide(@RequestBody Slide slide) {
        return slideService.updateSlide(slide);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTour> deleteSlide(@PathVariable String id) {
        return slideService.deleteSlide(id);
    }

    @GetMapping
    public ResponseEntity<List<Slide>> getAllSlide() {
        return slideService.getAllSlide();
    }
}
