-- schema.sql

-- Table for books
CREATE TABLE books (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       author VARCHAR(255) NOT NULL,
                       ISBN VARCHAR(20) NOT NULL,
                       price DOUBLE NOT NULL,
                       description TEXT
);

-- Table for users
CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL
);

CREATE TABLE cart (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      title VARCHAR(255),
                      author VARCHAR(255),
                      ISBN VARCHAR(20),
                      price DECIMAL(10, 2),
                      description TEXT
);
