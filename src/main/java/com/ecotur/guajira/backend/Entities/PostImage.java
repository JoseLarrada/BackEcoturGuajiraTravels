package com.ecotur.guajira.backend.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

// Clase para las imágenes del post
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostImage {
    @Field("url")
    private String url;

    @Field("caption")
    private String caption;
}
