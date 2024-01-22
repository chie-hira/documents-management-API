DROP TABLE IF EXISTS locations;

CREATE TABLE locations (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    location VARCHAR(50) NOT NULL,
    shelf_number VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE (location, shelf_number)
);

INSERT INTO locations (location, shelf_number) VALUES ("倉庫", "スチール書庫-1");
INSERT INTO locations (location, shelf_number) VALUES ("倉庫", "スチール書庫-2");
INSERT INTO locations (location, shelf_number) VALUES ("事務室", "棚-1");
INSERT INTO locations (location, shelf_number) VALUES ("事務室", "棚-2");
INSERT INTO locations (location, shelf_number) VALUES ("物置", "スチール書庫-1");
