package com.ecotur.guajira.backend.Controller;

import com.ecotur.guajira.backend.Entities.Dto.ResponseTour;
import com.ecotur.guajira.backend.Entities.Testimonial;
import com.ecotur.guajira.backend.Services.TestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/testimonial")
public class TestimonialController {
    private final TestimonialService testimonialService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseTour> addTestimonial(
            @RequestPart("testimonial") String testimonialJson,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        return testimonialService.addTestimonial(testimonialJson, image);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseTour> updateTestimonial(
            @PathVariable String id,
            @RequestPart("testimonial") String testimonialJson,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        return testimonialService.updateTestimonial(id, testimonialJson, image);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTour> deleteTestimonial(@PathVariable String id) {
        return testimonialService.deleteTestimonial(id);
    }
    @GetMapping
    public ResponseEntity<List<Testimonial>> getAllTestimonial() {
        return testimonialService.getAllTestimonial();
    }
}
