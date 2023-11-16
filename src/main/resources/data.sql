-- data.sql

-- Insert sample books
INSERT INTO books (title, author, ISBN, price, description)
VALUES
    ('The Great Gatsby', 'F. Scott Fitzgerald', '978-0-7432-7356-5', 10.99, 'Classic American novel.'),
    ('To Kill a Mockingbird', 'Harper Lee', '978-0-06-112008-4', 12.99, 'Pulitzer Prize-winning novel.'),
    ('1984', 'George Orwell', '978-0-452-28423-4', 8.99, 'Dystopian fiction.'),
    ('The Catcher in the Rye', 'J.D. Salinger', '978-0-316-76948-0', 11.99, 'Coming-of-age novel.'),
    ('Pride and Prejudice', 'Jane Austen', '978-0-19-953556-9', 9.99, 'Classic romantic novel.'),
    ('The Hobbit', 'J.R.R. Tolkien', '978-0-261-10236-0', 15.99, 'Fantasy novel.'),
    ('Harry Potter and the Sorcerer s Stone', 'J.K. Rowling', '978-0-590-35340-3', 14.99, 'Fantasy novel.'),
    ('The Lord of the Rings', 'J.R.R. Tolkien', '978-0-395-08254-2', 18.99, 'Epic fantasy trilogy.'),
    ('To the Lighthouse', 'Virginia Woolf', '978-0-15-690739-2', 13.99, 'Modernist novel.'),
    ('Brave New World', 'Aldous Huxley', '978-0-06-085052-4', 10.99, 'Dystopian fiction.'),
    ('One Hundred Years of Solitude', 'Gabriel Garcia Marquez', '978-0-06-112008-4', 12.99, 'Magical realism.'),
    ('The Odyssey', 'Homer', '978-0-14-026886-7', 11.99, 'Epic poem.'),
    ('Frankenstein', 'Mary Shelley', '978-0-14-143947-1', 9.99, 'Gothic novel.'),
    ('The Road', 'Cormac McCarthy', '978-0-307-26560-9', 14.99, 'Post-apocalyptic fiction.'),
    ('The Picture of Dorian Gray', 'Oscar Wilde', '978-0-14-143957-0', 10.99, 'Philosophical novel.'),
    ('The Grapes of Wrath', 'John Steinbeck', '978-0-14-303943-3', 12.99, 'Great Depression novel.'),
    ('The Alchemist', 'Paulo Coelho', '978-0-06-112241-5', 11.99, 'Philosophical novel.'),
    ('Moby-Dick', 'Herman Melville', '978-0-06-287022-2', 13.99, 'Adventure novel.'),
    ('The Shining', 'Stephen King', '978-0-385-12167-5', 15.99, 'Horror novel.'),
    ('The Scarlet Letter', 'Nathaniel Hawthorne', '978-0-553-21269-7', 9.99, 'Historical novel.');

-- Insert sample users
INSERT INTO users (username, password, email)
VALUES ('john_doe', 'password123', 'john.doe@example.com'),
       ('jane_smith', 'pass456', 'jane.smith@example.com');
