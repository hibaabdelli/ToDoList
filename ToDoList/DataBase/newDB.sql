DROP DATABASE EXIXSTS newdata;
CREATE DATABASE newdata;
USE newdata;
CREATE TABLE  Tasks{
    id INT(15) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    description varchar(100),
    date DATE
};