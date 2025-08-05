DROP TABLE IF EXISTS test_orders;

CREATE TABLE orders (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL
);

CREATE TABLE order_items (
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL,
    product_id UUID NOT NULL,
    quantity INT NOT NULL,
    price_per_unit NUMERIC(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);

