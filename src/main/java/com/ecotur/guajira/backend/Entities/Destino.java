package com.ecotur.guajira.backend.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "destinos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Destino {
    @Id
    private String id;
    private String nombre;
    private String imagen;
    private String descripcion;
    private List<String> experiencias;
    private String icon;
    private String color;
}
