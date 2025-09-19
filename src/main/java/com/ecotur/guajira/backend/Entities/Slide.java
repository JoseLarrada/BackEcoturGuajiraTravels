package com.ecotur.guajira.backend.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "slides")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Slide {
    @Id
    private String id;
    private String image;
    private String title;
    private String description;
    private String location;
    private String duration;
    private String capacity;
    private Double price;
}
