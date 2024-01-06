DROP TABLE IF EXISTS files_categories;

CREATE TABLE file_categories (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    privacy_type VARCHAR(50) NOT NULL,
    storage_year int NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE (privacy_type, storage_year)
);

INSERT INTO file_categories (privacy_type, storage_year) VALUES ("公開", "5");
INSERT INTO file_categories (privacy_type, storage_year) VALUES ("公開", "10");
INSERT INTO file_categories (privacy_type, storage_year) VALUES ("取扱注意", "5");
INSERT INTO file_categories (privacy_type, storage_year) VALUES ("取扱注意", "10");
INSERT INTO file_categories (privacy_type, storage_year) VALUES ("永久保存・公開", "50");
INSERT INTO file_categories (privacy_type, storage_year) VALUES ("永久保存・取扱注意", "50");
