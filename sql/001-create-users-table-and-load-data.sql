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
