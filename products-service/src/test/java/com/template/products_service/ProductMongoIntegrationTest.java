package com.template.products_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class ProductMongoIntegrationTest {
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldSaveAndFindComplexProductByName() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("kolor", "czarny");
        attributes.put("pamięć", "128GB");
        attributes.put("wodoodporny", true);
        List<String> tags = Arrays.asList("elektronika", "smartfon", "promocja");
        List<Product.Variant> variants = Arrays.asList(
                new Product.Variant("128GB", 1999.99),
                new Product.Variant("256GB", 2299.99));
        List<Product.Review> reviews = Arrays.asList(
                new Product.Review("Jan", 5, "Super telefon!"),
                new Product.Review("Anna", 4, "Dobry stosunek jakości do ceny."));
        Product product = new Product("Smartfon XYZ", 1999.99, tags, attributes, variants, reviews);
        Product saved = restTemplate.postForObject("http://localhost:" + port + "/products", product, Product.class);
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        Product found = restTemplate.getForObject("http://localhost:" + port + "/products/by-name/Smartfon XYZ",
                Product.class);
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Smartfon XYZ");
        assertThat(found.getTags()).containsExactlyElementsOf(tags);
        assertThat(found.getAttributes()).containsAllEntriesOf(attributes);
        assertThat(found.getVariants()).hasSize(2);
        assertThat(found.getReviews()).hasSize(2);
        assertThat(found.getVariants().get(0).getName()).isEqualTo("128GB");
        assertThat(found.getReviews().get(0).getAuthor()).isEqualTo("Jan");
    }
}