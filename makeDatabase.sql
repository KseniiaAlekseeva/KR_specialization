DROP DATABASE IF EXISTS human_friends;
CREATE DATABASE human_friends;

USE human_friends;

CREATE TABLE animal_types
(
	id INT AUTO_INCREMENT PRIMARY KEY,
	type VARCHAR(200)
);

INSERT INTO animal_types (type)
VALUES ('pack'),
('pet');

CREATE TABLE pack_animals
(
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR (200),
    type_id INT,
    FOREIGN KEY (type_id) REFERENCES animal_types (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO pack_animals (name, type_id)
VALUES ('horse', 1),
('donkey', 1),
('camel', 1);

CREATE TABLE pets
(
	  id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR (200),
    type_id INT,
    FOREIGN KEY (type_id) REFERENCES animal_types (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO pets (name, type_id)
VALUES ('cat', 2),
('dog', 2),
('hamster', 2);

CREATE TABLE cats
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200),
    birthday DATE,
    commands VARCHAR(200),
    subtype_id int,
    Foreign KEY (subtype_id) REFERENCES pets (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO cats (name, birthday, commands, subtype_id)
VALUES ('Lisa', '2011-01-01', "jump", 1),
('Mia', '2016-01-01', "sleep", 1),
('Tom', '2017-01-01', "go", 1);

CREATE TABLE dogs
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200),
    birthday DATE,
    commands VARCHAR(200),
    subtype_id int,
    Foreign KEY (subtype_id) REFERENCES pets (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO dogs (name, birthday, commands, subtype_id)
VALUES ('Joy', '2020-01-01', "go, jump, stop, sing", 2),
('Andi', '2021-06-12', "go, sing", 2),
('Toy', '2018-05-01', "seat, no", 2),
('Tigra', '2021-05-10', "seat", 2);

CREATE TABLE hamsters
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200),
    birthday DATE,
    commands VARCHAR(200),
    subtype_id int,
    Foreign KEY (subtype_id) REFERENCES pets (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO hamsters (name, birthday, commands, subtype_id)
VALUES ('Micky', '2020-10-12', "", 3),
('Ted', '2021-03-12', "play", 3),
('Bonya', '2022-07-11', "run", 3),
('Kuzya', '2022-05-10', "run, play", 3);

CREATE TABLE horses
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200),
    birthday DATE,
    commands VARCHAR(200),
    subtype_id int,
    Foreign KEY (subtype_id) REFERENCES pack_animals (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO horses (name, birthday, commands, subtype_id)
VALUES ('Jack', '2020-01-12', "run, step, stop", 1),
('Denny', '2017-03-12', "run", 1),
('Buyvol', '2016-07-12', "stop", 1),
('Sammy', '2020-11-10', "", 1);

CREATE TABLE donkeys
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20),
    birthday DATE,
    commands VARCHAR(50),
    subtype_id int,
    Foreign KEY (subtype_id) REFERENCES pack_animals (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO donkeys (name, birthday, commands, subtype_id)
VALUES ('Mick', '2019-04-10', "", 2),
('Jhonny', '2020-03-12', "go, stop", 2),
('Tito', '2021-07-12', "go", 2);

CREATE TABLE camels
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20),
    birthday DATE,
    commands VARCHAR(50),
    subtype_id int,
    Foreign KEY (subtype_id) REFERENCES pack_animals (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO camels (name, birthday, commands, subtype_id)
VALUES ('Mig', '2022-04-10', "go", 3),
('Taifoon', '2019-03-12', "stop", 3);

DELETE FROM camels;

CREATE TABLE hd_union
SELECT  * FROM horses
UNION
SELECT  * FROM donkeys;

DROP TABLE IF EXISTS animals;

CREATE TEMPORARY TABLE animals AS
SELECT all_pets.*, pets.name as subtype_name
FROM
(SELECT * FROM cats
UNION SELECT * FROM dogs
UNION SELECT * FROM hamsters
) all_pets
LEFT JOIN pets
ON all_pets.subtype_id=pets.id
UNION
SELECT all_pack.*, pack_animals.name as subtype_name
FROM
(SELECT * FROM horses
UNION SELECT * FROM donkeys
UNION SELECT * FROM camels
) all_pack
LEFT JOIN pack_animals
ON all_pack.subtype_id=pack_animals.id;

CREATE TABLE young_animals AS
SELECT *, TIMESTAMPDIFF(MONTH, birthday, CURDATE()) AS age_in_month
FROM animals WHERE birthday BETWEEN ADDDATE(curdate(), INTERVAL -3 YEAR) AND ADDDATE(CURDATE(), INTERVAL -1 YEAR);

CREATE TABLE full_table AS
SELECT all_animals.*, animal_types.type
FROM
(SELECT all_pets.*, pets.name as subtype_name, pets.type_id
FROM
(SELECT * FROM cats
UNION SELECT * FROM dogs
UNION SELECT * FROM hamsters
) all_pets
LEFT JOIN pets
ON all_pets.subtype_id=pets.id
UNION
SELECT all_pack.*, pack_animals.name as subtype_name, pack_animals.type_id
FROM
(SELECT * FROM horses
UNION SELECT * FROM donkeys
UNION SELECT * FROM camels
) all_pack
LEFT JOIN pack_animals
ON all_pack.subtype_id=pack_animals.id) all_animals
LEFT JOIN animal_types
ON all_animals.type_id=animal_types.id;
