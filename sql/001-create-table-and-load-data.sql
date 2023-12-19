DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    is_admin BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO users (name, email, password, is_admin) VALUES ("kuro", "kuro@neko.com", "password", true);
INSERT INTO users (name, email, password, is_admin) VALUES ("shiro", "shiro@neko.com", "password", false);
INSERT INTO users (name, email, password, is_admin) VALUES ("aka", "aka@neko.com", "password", false);


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

DROP TABLE IF EXISTS places;

CREATE TABLE places (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    place VARCHAR(50) NOT NULL,
    shelf_number int,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE (place, shelf_number)
);

INSERT INTO places (place, shelf_number) VALUES ("資料室", "1");
INSERT INTO places (place, shelf_number) VALUES ("資料室", "2");
INSERT INTO places (place, shelf_number) VALUES ("資料室", "3");
INSERT INTO places (place, shelf_number) VALUES ("書庫", "1");
INSERT INTO places (place, shelf_number) VALUES ("書庫", "2");
INSERT INTO places (place, shelf_number) VALUES ("書庫", "3");

DROP TABLE IF EXISTS files;

CREATE TABLE files (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    borrower_id INT UNSIGNED,
    file_category_id INT UNSIGNED,
    place_id INT UNSIGNED,
    FOREIGN KEY (borrower_id) REFERENCES users(id),
    FOREIGN KEY (file_category_id) REFERENCES file_categories(id),
    FOREIGN KEY (place_id) REFERENCES places(id),
    file_name VARCHAR(50) NOT NULL UNIQUE,
    detail VARCHAR(250),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by INT UNSIGNED,
    updated_by INT UNSIGNED,
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id)
);

INSERT INTO files (borrower_id, file_category_id, place_id, file_name, detail, created_at, created_by) VALUES ("1", "1", "1", "令和5年度総務資料", "書類の詳細", '2023-04-01', "1");
INSERT INTO files (borrower_id, file_category_id, place_id, file_name, detail, created_at, created_by) VALUES ("1", "4", "1", "令和5年度人事資料", "書類の詳細", '2023-04-01', "1");
INSERT INTO files (borrower_id, file_category_id, place_id, file_name, detail, created_at, created_by) VALUES ("2", "3", "2", "令和5年度経理資料", "書類の詳細", '2023-05-01', "2");
INSERT INTO files (borrower_id, file_category_id, place_id, file_name, detail, created_at, created_by) VALUES ("2", "1", "2", "令和5年度庶務資料", "書類の詳細", '2023-06-01', "2");
INSERT INTO files (borrower_id, file_category_id, place_id, file_name, detail, created_at, created_by) VALUES ("3", "2", "3", "令和5年度測量成果", "書類の詳細", '2023-12-11', "3");
INSERT INTO files (borrower_id, file_category_id, place_id, file_name, detail, created_at, created_by) VALUES ("3", "6", "4", "令和5年度技術資料", "書類の詳細", '2023-09-11', "3");
