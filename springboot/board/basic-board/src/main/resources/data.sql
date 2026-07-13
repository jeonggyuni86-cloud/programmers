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


-- comment 테이블 생성
-- board_id 는 board.id 를 가리키는 외래키(FK) - "이 댓글이 어느 게시글 것인지"를 나타낸다
CREATE TABLE comment (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         content TEXT NOT NULL,
                         user_id VARCHAR(30) NOT NULL,
                         created DATETIME DEFAULT CURRENT_TIMESTAMP,
                         board_id BIGINT NOT NULL,
                         CONSTRAINT fk_comment_board FOREIGN KEY (board_id) REFERENCES board (id)
);

SELECT * FROM comment
LIMIT 0;

SELECT * FROM board
LIMIT 0;