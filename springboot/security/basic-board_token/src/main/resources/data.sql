DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS board;
DROP TABLE IF EXISTS member;


CREATE TABLE member (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user_id VARCHAR(50) NOT NULL UNIQUE,
                        password VARCHAR(100) NOT NULL,
                        user_name VARCHAR(50) NOT NULL,
                        role VARCHAR(50) NOT NULL
);


CREATE TABLE board (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(200) NOT NULL,
                       content TEXT NOT NULL,
                       user_id VARCHAR(50) NOT NULL,
                       file_path VARCHAR(255),
                       created_at DATETIME NOT NULL
);


CREATE TABLE comment (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         board_id BIGINT NOT NULL,
                         user_id VARCHAR(50) NOT NULL,
                         content TEXT NOT NULL,
                         created_at DATETIME NOT NULL,

                         CONSTRAINT fk_comment_board
                             FOREIGN KEY (board_id)
                                 REFERENCES board(id)
                                 ON DELETE CASCADE
);


-- 비밀번호 1234
INSERT INTO member (
    user_id,
    password,
    user_name,
    role
) VALUES
      (
          'test',
          '$2y$10$eqAD5rTMDAPuStXlMpYl0OQwBjLbhT8F9iSOfZMCddPLRDyxYQG22',
          '관리자',
          'ROLE_ADMIN'
      ),
      (
          'user',
          '$2y$10$eqAD5rTMDAPuStXlMpYl0OQwBjLbhT8F9iSOfZMCddPLRDyxYQG22',
          '일반사용자',
          'ROLE_USER'
      );


INSERT INTO board (
    title,
    content,
    user_id,
    file_path,
    created_at
) VALUES
      (
          '관리자 테스트 게시글',
          '관리자 작성 게시글',
          'test',
          NULL,
          NOW()
      ),
      (
          '사용자 테스트 게시글',
          '일반 사용자 작성 게시글',
          'user',
          NULL,
          NOW()
      ),
      (
          '삭제 권한 테스트',
          '본인 삭제 테스트용',
          'user',
          NULL,
          NOW()
      );


INSERT INTO comment (
    board_id,
    user_id,
    content,
    created_at
) VALUES
      (
          1,
          'user',
          '댓글 테스트입니다.',
          NOW()
      ),
      (
          2,
          'test',
          '관리자 댓글 테스트입니다.',
          NOW()
      );