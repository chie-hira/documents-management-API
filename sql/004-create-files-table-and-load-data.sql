DROP TABLE IF EXISTS files;

CREATE TABLE files (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    file_name VARCHAR(50) NOT NULL UNIQUE,
    borrower_id INT UNSIGNED,
    file_category_id INT UNSIGNED,
    location_Id INT UNSIGNED,
    FOREIGN KEY (borrower_id) REFERENCES users(id),
    FOREIGN KEY (file_category_id) REFERENCES file_categories(id),
    FOREIGN KEY (location_id) REFERENCES locations(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by INT UNSIGNED,
    updated_by INT UNSIGNED,
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id)
);

INSERT INTO files (file_name, file_category_id, location_id, created_at, created_by) VALUES ("令和5年度総務資料", "1", "1", '2023-04-01', "1");
INSERT INTO files (file_name, file_category_id, location_id, created_at, created_by) VALUES ("令和5年度経理資料", "3", "2", '2023-05-01', "2");
INSERT INTO files (file_name, file_category_id, location_id, created_at, created_by) VALUES ("令和5年度庶務資料", "1", "2", '2023-06-01', "2");
INSERT INTO files (file_name, file_category_id, location_id, created_at, created_by) VALUES ("令和5年度技術資料", "6", "4", '2023-09-11', "3");
INSERT INTO files (file_name, borrower_id, file_category_id, location_id, created_at, created_by) VALUES ("令和5年度人事資料", "1", "4", "1", '2023-04-01', "1");
INSERT INTO files (file_name, borrower_id, file_category_id, location_id, created_at, created_by) VALUES ("令和5年度測量成果", "3", "2", "3", '2023-12-11', "3");
