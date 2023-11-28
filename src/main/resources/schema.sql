-- schema.sql

-- Table for books
CREATE TABLE books (
                       bookId INT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       author VARCHAR(255) NOT NULL,
                       ISBN VARCHAR(200) NOT NULL,
                       price DOUBLE NOT NULL,
                       description TEXT
);


CREATE TABLE cart (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      title VARCHAR(255),
                      author VARCHAR(255),
                      ISBN VARCHAR(200),
                      price DECIMAL(10, 2),
                      description TEXT
);


CREATE TABLE sec_user (
                          userId            BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                          first_name        VARCHAR(275),
                          last_name        VARCHAR(275),
                          email             VARCHAR(75) NOT NULL UNIQUE,
                          encryptedPassword VARCHAR(128) NOT NULL,
                          enabled           BIT NOT NULL
);

CREATE TABLE sec_role(
                         roleId   BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                         roleName VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE user_book (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           userId BIGINT NOT NULL,
                           bookId BIGINT NOT NULL
);


CREATE TABLE user_role
(
    id     BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    userId BIGINT NOT NULL,
    roleId BIGINT NOT NULL
);

ALTER TABLE user_role
    ADD CONSTRAINT user_role_uk UNIQUE (userId, roleId);

ALTER TABLE user_role
    ADD CONSTRAINT user_role_fk1 FOREIGN KEY (userId)
        REFERENCES sec_user (userId);

ALTER TABLE user_role
    ADD CONSTRAINT user_role_fk2 FOREIGN KEY (roleId)
        REFERENCES sec_role (roleId);
