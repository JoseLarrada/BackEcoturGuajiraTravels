package com.ecotur.guajira.backend.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "destinations_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DestinationData {
    @Id
    private String id;
    private String name;
    private String type;
    private Integer experiences;
    private String bestTime;
    private String icon;
    private String mainImage;
    private String description;
    private String history;
    private List<String> highlights;
    private List<String> activities;
    private List<GalleryItem> gallery;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GalleryItem {
        private String url;
        private String caption;
    }
}
