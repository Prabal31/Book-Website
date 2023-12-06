-- Creating a table to store information about books
CREATE TABLE books (
                       id INT AUTO_INCREMENT PRIMARY KEY,          -- Unique identifier for each book
                       title VARCHAR(255) NOT NULL,                -- Title of the book (cannot be null)
                       author VARCHAR(255) NOT NULL,               -- Author of the book (cannot be null)
                       ISBN VARCHAR(200) NOT NULL,                 -- International Standard Book Number
                       price DOUBLE NOT NULL,                      -- Price of the book
                       description TEXT                            -- Detailed description of the book
);

-- Creating a table to represent the user's shopping cart
CREATE TABLE cart (
                      id INT AUTO_INCREMENT PRIMARY KEY,          -- Unique identifier for each cart item
                      title VARCHAR(255),                         -- Title of the book in the cart
                      author VARCHAR(255),                        -- Author of the book in the cart
                      ISBN VARCHAR(200),                          -- International Standard Book Number in the cart
                      price DECIMAL(10, 2),                       -- Price of the book in the cart
                      description TEXT                            -- Detailed description of the book in the cart
);

-- Creating a table to store user information
CREATE TABLE sec_user (
                          userId BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,  -- Unique identifier for each user
                          first_name VARCHAR(275),                             -- First name of the user
                          last_name VARCHAR(275),                              -- Last name of the user
                          email VARCHAR(75) NOT NULL UNIQUE,                   -- Email address of the user (unique)
                          encryptedPassword VARCHAR(128) NOT NULL,            -- Encrypted password of the user
                          enabled BIT NOT NULL                                -- Indicates if the user is enabled or disabled
);

-- Creating a table to store role information
CREATE TABLE sec_role (
                          roleId BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,  -- Unique identifier for each role
                          roleName VARCHAR(30) NOT NULL UNIQUE                 -- Name of the role (unique)
);

-- Creating a table to establish a many-to-many relationship between users and books
CREATE TABLE user_book (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,       -- Unique identifier for each user-book relationship
                           userId BIGINT NOT NULL,                     -- Foreign key referencing the user table
                           bookId BIGINT NOT NULL,                     -- Foreign key referencing the books table
                           enabled BIT NOT NULL                        -- Indicates if the user-book relationship is enabled or disabled
);

-- Creating a table to store user-role relationships
CREATE TABLE user_role (
                           id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,  -- Unique identifier for each user-role relationship
                           userId BIGINT NOT NULL,                         -- Foreign key referencing the user table
                           roleId BIGINT NOT NULL                          -- Foreign key referencing the role table
);

-- Adding a unique constraint to ensure each user has only one instance of each role
ALTER TABLE user_role
    ADD CONSTRAINT user_role_uk UNIQUE (userId, roleId);

-- Adding foreign key constraints to maintain referential integrity
ALTER TABLE user_role
    ADD CONSTRAINT user_role_fk1 FOREIGN KEY (userId)
        REFERENCES sec_user (userId);

ALTER TABLE user_role
    ADD CONSTRAINT user_role_fk2 FOREIGN KEY (roleId)
        REFERENCES sec_role (roleId);
