package com.template.products_service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

/**
 * Product document for MongoDB.
 * Contains multilingual descriptions, technical specifications, metadata,
 * gallery, and category-dependent attributes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    private String id;

    @Field("name")
    @NotBlank
    private String name;

    @Field("category")
    @NotNull
    private String category;
    /**
     * Multilingual descriptions, e.g. {"pl": "Opis po polsku", "en": "Description
     * in English"}
     */
    @Field("descriptions")
    @NotNull
    private Map<String, String> descriptions;
    /**
     * Technical specifications, e.g. {"screen": "55 inch", "resolution": "4K"}
     */
    @Field("specifications")
    @NotNull
    private Map<String, String> specifications;
    /**
     * Metadata, e.g. {"brand": "Samsung", "model": "QLED"}
     */
    @Field("metadata")
    private Map<String, String> metadata;
    /**
     * Gallery of image URLs.
     */
    @Field("gallery")
    @Size(min = 0)
    private List<String> gallery;
    /**
     * Category-dependent attributes, e.g. {"screen_size": "55 inch"} for TV,
     * {"battery_capacity": "3000mAh"} for headphones.
     */
    @Field("attributes")
    private Map<String, String> attributes;
}