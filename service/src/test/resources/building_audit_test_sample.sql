INSERT INTO structureinventorysystem.building_audits (name, structure_type, location, description, created_on, created_by_user_id, updated_on, updated_by_user_id)
VALUES
('Building audit inventory in Bytom', 'BUILDING', 'Bytom', 'Building audit in Bytom', NOW() + INTERVAL 5 DAY, 1, NOW() + INTERVAL 7 DAY, 5),
('Building audit inventory in Ruda Slaska', 'BUILDING', 'Ruda Slaska', 'Building audit in Ruda Slaska', NOW() + INTERVAL 7 DAY, 3, NOW() + INTERVAL 10 DAY, 10),
('Building audit inventory in Katowice', 'BUILDING', 'Katowice', 'Building audit in Katowice', NOW() + INTERVAL 10 DAY, 4, NOW() + INTERVAL 15 DAY, 6),
('Building audit inventory in Bierun', 'BUILDING', 'Bierun', 'Building audit in Bierun', NOW() + INTERVAL 16 DAY, 2, NOW() + INTERVAL 23 DAY, 8),
('Building audit inventory in Jastrzebie Zdroj', 'BUILDING', 'Jastrzebie Zdroj', 'Building audit in Jastrzebie Zdroj', NOW() + INTERVAL 20 DAY, 3, NOW() + INTERVAL 21 DAY, 3),
('Building audit inventory in Rydultowy', 'BUILDING', 'Rydultowy', 'Building audit in Rydultowy', NOW() + INTERVAL 20 DAY, 9, NOW() + INTERVAL 21 DAY, 1);