package com.ecotur.guajira.backend.Services;

import com.ecotur.guajira.backend.Entities.Destino;
import com.ecotur.guajira.backend.Entities.Dto.ResponseTour;
import com.ecotur.guajira.backend.Repository.DestinoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DestinoService {
    private final DestinoRepository destinoRepository;
    private final FileStorageService fileStorageService;

    public ResponseEntity<ResponseTour> addDestino(String destinoJson, MultipartFile imagen) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Destino destino = mapper.readValue(destinoJson, Destino.class);

            // Guardar imagen si viene
            if (imagen != null && !imagen.isEmpty()) {
                destino.setImagen(fileStorageService.saveFile(imagen));
            }

            destinoRepository.save(destino);
            return ResponseEntity.ok(new ResponseTour(destino.getNombre(), "Creado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseTour(null, e.getMessage()));
        }
    }

    public ResponseEntity<ResponseTour> updateDestino(Destino destino){
        try {
            if(destinoRepository.existsById(destino.getId())){
                destinoRepository.save(destino);
                return ResponseEntity.ok(new ResponseTour(destino.getNombre(), "Actualizado correctamente"));
            }
            return ResponseEntity.badRequest().body(new ResponseTour(destino.getNombre(), "No se encontro el destino"));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<ResponseTour> deleteDestino(String id){
        try {
            if(destinoRepository.existsById(id)){
                destinoRepository.deleteById(id);
                return ResponseEntity.ok(new ResponseTour(null, "Eliminado correctamente"));
            }
            return ResponseEntity.badRequest().body(new ResponseTour(null, "No existe el destino"));
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<List<Destino>> getAllDestinos(){
        return  ResponseEntity.ok(destinoRepository.findAll());
    }

}
