DROP DATABASE IF EXISTS newdata;
CREATE DATABASE newdata;
USE newdata;

CREATE TABLE Users (
    id INT(15) PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE Tasks (
    id INT(15) PRIMARY KEY AUTO_INCREMENT,
    user_id INT(15),
    name VARCHAR(30) NOT NULL,
    description VARCHAR(100),
    //char id of user li khayartoo
    date DATE,
    state ENUM('complated', 'not_complated', 'abandoned') NOT NULL,
    priority VARCHAR (30),
    mood VARCHAR(30),
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);
