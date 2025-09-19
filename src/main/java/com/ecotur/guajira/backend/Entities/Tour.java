package com.ecotur.guajira.backend.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tours")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tour {
    @Id
    private String id;
    private String image;
    private String title;
    private String duration;
    private Double price;
    private Double rating;
    private String badge;
    private String badgeColor;
    private String description;
}
