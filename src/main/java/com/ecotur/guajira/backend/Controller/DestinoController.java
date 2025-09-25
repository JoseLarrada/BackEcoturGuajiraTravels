package com.ecotur.guajira.backend.Controller;

import com.ecotur.guajira.backend.Entities.Destino;
import com.ecotur.guajira.backend.Entities.Dto.ResponseTour;
import com.ecotur.guajira.backend.Services.DestinoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/destino")
public class DestinoController {
    private final DestinoService destinoService;

    @GetMapping
    public ResponseEntity<List<Destino>> getAllDestino(){
        return destinoService.getAllDestinos();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseTour> addDestino(
            @RequestPart("destino") String destinoJson,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {

        return destinoService.addDestino(destinoJson, imagen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTour> updateDestino(
            @PathVariable String id,
            @RequestPart("destino") String destinoJson,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        return destinoService.updateDestino(id, destinoJson, imagen);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTour> deleteDestino(@PathVariable String id){
        return destinoService.deleteDestino(id);
    }

}
