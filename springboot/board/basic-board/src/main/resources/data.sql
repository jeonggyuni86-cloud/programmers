drop database java_basic;

CREATE DATABASE java_basic
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

show databases;

use java_basic;

show tables;

-- member table

CREATE TABLE member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    user_name VARCHAR(20) NOT NULL
);
desc member;


CREATE TABLE board (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title varchar(200) NOT NULL,
    content TEXT NOT NULL,
    user_id varchar(50) NOT NULL,
    file_path VARCHAR(255),
    created DATETIME DEFAULT CURRENT_TIMESTAMP
);

desc board;