DROP TABLE IF EXISTS locations;

CREATE TABLE locations (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    location VARCHAR(50) NOT NULL,
    shelf_number int,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE (location, shelf_number)
);

INSERT INTO locations (location, shelf_number) VALUES ("資料室", "1");
INSERT INTO locations (location, shelf_number) VALUES ("資料室", "2");
INSERT INTO locations (location, shelf_number) VALUES ("資料室", "3");
INSERT INTO locations (location, shelf_number) VALUES ("書庫", "1");
INSERT INTO locations (location, shelf_number) VALUES ("書庫", "2");
INSERT INTO locations (location, shelf_number) VALUES ("書庫", "3");
