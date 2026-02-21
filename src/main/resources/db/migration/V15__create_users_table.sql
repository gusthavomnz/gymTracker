CREATE TABLE users (
    user_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    body_weight DECIMAL(5,2),
    gender VARCHAR(20),
    PRIMARY KEY (user_id)
) ENGINE=InnoDB;