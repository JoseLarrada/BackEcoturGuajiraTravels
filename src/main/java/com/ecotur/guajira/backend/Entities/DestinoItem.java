package com.ecotur.guajira.backend.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "destinos_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DestinoItem {
    @Id
    private String id;
    private String title;
    private String phrase;
    private List<String> images;
    private String color;
    private String icon;
}
