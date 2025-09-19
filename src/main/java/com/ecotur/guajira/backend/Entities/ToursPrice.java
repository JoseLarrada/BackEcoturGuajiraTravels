package com.ecotur.guajira.backend.Entities;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tours_price")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToursPrice {
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