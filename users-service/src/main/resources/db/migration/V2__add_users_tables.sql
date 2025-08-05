DROP TABLE test_users;

CREATE TABLE users (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password_hash TEXT NOT NULL
);

CREATE TABLE user_addresses (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    street VARCHAR(255),
    city VARCHAR(255),
    zip_code VARCHAR(20),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
