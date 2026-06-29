CREATE TABLE food_products (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL,
    default_unit_code VARCHAR(32) NOT NULL,
    default_variant_id UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE product_variants (
    id UUID PRIMARY KEY,
    product_id UUID NOT NULL REFERENCES food_products(id),
    name VARCHAR(255) NOT NULL,
    is_default BOOLEAN NOT NULL DEFAULT FALSE,
    density_g_per_ml NUMERIC(10,4),
    average_piece_weight_g NUMERIC(10,2),
    calories_per_100g NUMERIC(10,2),
    protein_per_100g NUMERIC(10,2),
    fat_per_100g NUMERIC(10,2),
    carbs_per_100g NUMERIC(10,2),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE food_products
    ADD CONSTRAINT fk_food_products_default_variant
    FOREIGN KEY (default_variant_id) REFERENCES product_variants(id);

CREATE TABLE measurement_unit_definitions (
    id UUID PRIMARY KEY,
    product_id UUID NOT NULL REFERENCES food_products(id),
    product_variant_id UUID REFERENCES product_variants(id),
    name VARCHAR(100) NOT NULL,
    unit_type VARCHAR(32) NOT NULL,
    quantity_value NUMERIC(12,4) NOT NULL,
    quantity_unit_code VARCHAR(32) NOT NULL,
    is_default BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE recipes (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    category_id UUID,
    image_path TEXT,
    base_servings INTEGER NOT NULL CHECK (base_servings > 0),
    instructions TEXT,
    notes TEXT,
    legacy_ingredients_text TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE recipe_ingredients (
    id UUID PRIMARY KEY,
    recipe_id UUID NOT NULL REFERENCES recipes(id) ON DELETE CASCADE,
    product_id UUID NOT NULL REFERENCES food_products(id),
    product_variant_id UUID REFERENCES product_variants(id),
    original_quantity_value NUMERIC(12,4) NOT NULL,
    original_quantity_unit_code VARCHAR(32) NOT NULL,
    display_order INTEGER NOT NULL,
    note TEXT
);

INSERT INTO food_products (id, name, category, default_unit_code) VALUES
    ('00000000-0000-0000-0000-000000000001', 'woda', 'płyny', 'ml'),
    ('00000000-0000-0000-0000-000000000002', 'mleko', 'nabiał', 'ml'),
    ('00000000-0000-0000-0000-000000000003', 'jajko', 'nabiał', 'szt'),
    ('00000000-0000-0000-0000-000000000004', 'masło', 'nabiał', 'g');

INSERT INTO product_variants (id, product_id, name, is_default, density_g_per_ml, average_piece_weight_g, calories_per_100g) VALUES
    ('10000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', 'domyślna', TRUE, 1.0000, NULL, 0),
    ('10000000-0000-0000-0000-000000000002', '00000000-0000-0000-0000-000000000002', '3.2% tłuszczu', TRUE, 1.0300, NULL, 61),
    ('10000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000002', '0.5% tłuszczu', FALSE, 1.0350, NULL, 38),
    ('10000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000003', 'M', TRUE, NULL, 56, 143),
    ('10000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-000000000003', 'S', FALSE, NULL, 43, 143),
    ('10000000-0000-0000-0000-000000000006', '00000000-0000-0000-0000-000000000003', 'L', FALSE, NULL, 63, 143),
    ('10000000-0000-0000-0000-000000000007', '00000000-0000-0000-0000-000000000004', 'kostka 200 g', TRUE, NULL, NULL, 720);

UPDATE food_products SET default_variant_id = '10000000-0000-0000-0000-000000000001' WHERE id = '00000000-0000-0000-0000-000000000001';
UPDATE food_products SET default_variant_id = '10000000-0000-0000-0000-000000000002' WHERE id = '00000000-0000-0000-0000-000000000002';
UPDATE food_products SET default_variant_id = '10000000-0000-0000-0000-000000000004' WHERE id = '00000000-0000-0000-0000-000000000003';
UPDATE food_products SET default_variant_id = '10000000-0000-0000-0000-000000000007' WHERE id = '00000000-0000-0000-0000-000000000004';

INSERT INTO measurement_unit_definitions (id, product_id, product_variant_id, name, unit_type, quantity_value, quantity_unit_code, is_default) VALUES
    ('20000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', NULL, 'szklanka', 'HOUSEHOLD', 250, 'ml', TRUE),
    ('20000000-0000-0000-0000-000000000002', '00000000-0000-0000-0000-000000000001', NULL, 'szklanka mała', 'HOUSEHOLD', 200, 'ml', FALSE),
    ('20000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000002', '10000000-0000-0000-0000-000000000002', 'szklanka', 'HOUSEHOLD', 250, 'ml', TRUE),
    ('20000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000002', NULL, 'karton', 'PACKAGE', 1000, 'ml', TRUE),
    ('20000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-000000000004', '10000000-0000-0000-0000-000000000007', 'kostka', 'PACKAGE', 200, 'g', TRUE);
