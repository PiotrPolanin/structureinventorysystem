INSERT INTO structureinventorysystem.users(first_name, last_name)
VALUES
('John', 'Smith'),
('Lisa', 'Monk'),
('Ann', 'McDonald'),
('Jonathan', 'Doe'),
('Luigi', 'Fresco'),
('Raul', 'Gonzales'),
('Marco', 'Bellucci'),
('Mark', 'Holmes'),
('Amir', 'Khan'),
('Miriam', 'Velasques');

INSERT INTO structureinventorysystem.user_roles(user_id, roles)
VALUES
(1, 'VISITOR'),
(2, 'USER'),
(3, 'USER'),
(4, 'USER'),
(5, 'VISITOR'),
(6, 'USER'),
(7, 'VISITOR'),
(8, 'USER'),
(8, 'VISITOR'),
(8, 'ADMIN'),
(9, 'USER'),
(9, 'VISITOR'),
(9, 'ADMIN'),
(10, 'VISITOR');
