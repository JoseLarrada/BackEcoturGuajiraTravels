package com.ecotur.guajira.backend.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "empresa_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaInfo {
    @Id
    private String id;
    private String razonSocial;
    private String telefono;
    private String ciudad;
    private String departamento;
    private String direccion;
    private String nit;
    private String actividad;
    private String formaJuridica;
    private List<String> exposiciones;
    private List<TimelineEvent> timeline;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TimelineEvent {
        private String year;
        private String title;
        private String description;
    }
}
