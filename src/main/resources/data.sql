-- data.sql
-- Inserting sample data for books
INSERT INTO books (title, author, ISBN, price, description) VALUES
    ('Attack on Titan', 'Hajime Isayama', '978-1-4278-1923-1', 29.99,
     'In a world where humanity resides within enormous walled cities to protect themselves from Titans, gigantic humanoid creatures, the story follows Eren Yeager and his friends who join the military to fight the Titans after their hometown is invaded and Erens mother is eaten.');

INSERT INTO books (title, author, ISBN, price, description) VALUES
  ('Death Note', 'Tsugumi Ohba', '978-4-08-873621-5', 24.99,
   'Light Yagami discovers a mysterious notebook called the Death Note, which grants him the power to kill anyone whose name he writes in it. As he takes justice into his own hands, a cat-and-mouse game with a genius detective named L unfolds.');

INSERT INTO books (title, author, ISBN, price, description) VALUES
  ('One Piece', 'Eiichiro Oda', '978-4-08-873621-5', 34.99,
   'Monkey D. Luffy sets out on a grand adventure to become the Pirate King and find the legendary One Piece treasure. Along the way, he forms a diverse crew, faces powerful foes, and explores the vast and mysterious world of the Grand Line.');

INSERT INTO books (title, author, ISBN, price, description) VALUES
  ('Naruto', 'Masashi Kishimoto', '978-1-4215-0639-6', 27.99,
   'Naruto Uzumaki, a young ninja with dreams of becoming the strongest ninja and leader of his village, navigates the challenges of the ninja world, faces powerful adversaries, and seeks acknowledgment from his peers.');

INSERT INTO books (title, author, ISBN, price, description) VALUES
  ('Fullmetal Alchemist', 'Hiromu Arakawa', '978-1-59816-816-0', 22.99,
   'Two brothers, Edward and Alphonse Elric, use alchemy in their quest to find the Philosophers Stone to restore their bodies after a failed alchemical experiment. The journey reveals dark secrets and a plot that could shake the entire nation.');

INSERT INTO books (title, author, ISBN, price, description) VALUES
    ('My Hero Academia', 'Kohei Horikoshi', '978-1-4215-9078-9', 31.99,
     'Set in a world where individuals possess superpowers known as "Quirks," My Hero Academia follows Izuku Midoriya, a Quirkless boy, as he enrolls in U.A. High School to become a hero and protect the world from villains.');

INSERT INTO books (title, author, ISBN, price, description) VALUES
    ('Demon Slayer', 'Koyoharu Gotouge', '978-4-08-881375-6', 26.99,
     'In a world where demons terrorize humanity, Tanjiro Kamado becomes a demon slayer after his family is slaughtered by demons. Alongside his friends, he seeks revenge and a cure for his demon-turned sister, Nezuko.');

INSERT INTO books (title, author, ISBN, price, description) VALUES
    ('One Punch Man', 'ONE', '978-4-08-870210-4', 19.99,
     'Saitama, a hero who can defeat any opponent with a single punch, seeks meaning and excitement in his hero career. Despite his overwhelming strength, he struggles with boredom and the lack of challenging foes.');

INSERT INTO books (title, author, ISBN, price, description) VALUES
    ('Dragon Ball Z', 'Akira Toriyama', '978-1-56931-930-7', 32.99,
     'Follow the adventures of Goku and his friends as they protect Earth from powerful foes and search for the Dragon Balls, magical orbs that grant wishes. The series explores themes of friendship, perseverance, and the pursuit of strength.');

INSERT INTO books (title, author, ISBN, price, description) VALUES
    ('Tokyo Ghoul', 'Sui Ishida', '978-1-4215-9941-9', 28.99,
     'Ken Kaneki, after a chance encounter with a ghoul, becomes part ghoul himself and navigates the complex world of ghouls and humans. The story delves into themes of identity, morality, and the blurred line between good and evil.');

INSERT INTO books (title, author, ISBN, price, description) VALUES
    ('Hunter x Hunter', 'Yoshihiro Togashi', '978-1-4215-7933-4', 30.99,
     'Gon Freecss embarks on a journey to become a Hunter and find his missing father. Along the way, he encounters powerful foes, forms lasting friendships, and uncovers the mysteries of the Hunter Association.');

INSERT INTO books (title, author, ISBN, price, description) VALUES
    ('Sword Art Online', 'Reki Kawahara', '978-0-316-34914-2', 23.99,
     'Players get trapped in a virtual reality MMORPG, and protagonist Kirito must navigate the game to survive. The series explores themes of virtual reality, relationships, and the consequences of living in a digital world.');

INSERT INTO books (title, author, ISBN, price, description) VALUES
    ('My Neighbor Totoro', 'Hayao Miyazaki', '978-1-59019-693-2', 19.99,
     'Two sisters, Satsuke and Mei, encounter friendly forest spirits in rural Japan while their mother is recovering in a nearby hospital. My Neighbor Totoro is a heartwarming tale that captures the magic of childhood and the wonders of nature.');

INSERT INTO books (title, author, ISBN, price, description) VALUES
    ('Steins;Gate', 'Chiyomaru Shikura', '978-4-04-101834-9', 25.99,
     'A group of friends discovers a way to send messages to the past, leading to unintended consequences and a struggle against a secretive organization. Steins;Gate combines science fiction, time travel, and intricate character relationships.');

INSERT INTO books (title, author, ISBN, price, description) VALUES
    ('Cowboy Bebop', 'Shinichirō Watanabe', '978-1-897376-58-1', 29.99,
     'Bounty hunters Spike Spiegel and Jet Black travel through space in the spaceship Bebop, pursuing dangerous criminals. Cowboy Bebop is known for its stylish animation, jazz-influenced soundtrack, and exploration of existential themes.');

INSERT INTO books (title, author, ISBN, price, description) VALUES
    ('Neon Genesis Evangelion', 'Hideaki Anno', '978-1-892022-23-5', 26.99,
     'In a post-apocalyptic world, teenagers pilot giant mechs known as Evangelions to protect Earth from mysterious beings called Angels. Neon Genesis Evangelion is renowned for its psychological depth, symbolism, and deconstruction of the mecha genre.');



-- Inserting sample data for users
INSERT INTO sec_user (first_name,last_name,email, encryptedPassword, enabled)
VALUES ('Frank','Kol', 'frank@sge.ca', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

INSERT INTO sec_user (first_name,last_name,email, encryptedPassword, enabled)
VALUES ('Prabh','Manchanda', 'manchapr@sheridancollege.ca', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

-- Inserting sample data for roles
INSERT INTO sec_role (roleName)
VALUES ('ROLE_USER');

INSERT INTO sec_role (roleName)
VALUES ('ROLE_ADMIN');


-- Assigning roles to users
INSERT INTO user_role (userId, roleId)-- Frank Kol has ROLE_USER
VALUES (1, 1);

INSERT INTO user_role (userId, roleId)-- Prabh Manchanda has ROLE_ADMIN
VALUES (2, 2);

