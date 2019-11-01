DROP TABLE IF EXISTS numbers_levels;

CREATE TABLE numbers_levels (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numbers VARCHAR(250),
    averagetime INT,
    timesplayed INT,
);

INSERT INTO numbers_levels (numbers, averagetime, timesplayed) VALUES
    ('001002003004005006007', 20000, 1),
    ('002004006008010012014', 40000, 2),
    ('003006009012015018021', 60000, 3);
