package com.ecotur.guajira.backend.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tour_options")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TourOption {
    @Id
    private String id;
    private String tourName;
    private Double basePrice;
    private String days;

    private HospedajeOption hospedajeOption;
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class HospedajeOption {
        private String name;
        private String description;
    }
}
