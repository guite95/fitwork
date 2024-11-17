DROP DATABASE IF EXISTS fit_work;
CREATE DATABASE IF NOT EXISTS fit_work;

USE fit_work;

# 회원정보 관련 테이블
-- 회원 테이블
CREATE TABLE IF NOT EXISTS member (
	id VARCHAR(30),
    password VARCHAR(100) NOT NULL,
    name VARCHAR(50) NOT NULL,
    nickname VARCHAR(50) UNIQUE,
    email VARCHAR(100),
    phone_number VARCHAR(20) NOT NULL,
    address VARCHAR(100) NOT NULL,
    member_role VARCHAR(20) NOT NULL,
    PRIMARY KEY(id)
);


# 게시판 정보 관련 테이블
-- 게시판 테이블
CREATE TABLE IF NOT EXISTS board (
	board_no INT AUTO_INCREMENT,
    title VARCHAR(200),
    writer VARCHAR(50),
    content VARCHAR(10000),
    reg_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    view_cnt INT DEFAULT 0,
    like_cnt INT DEFAULT 0,
    is_modified BOOLEAN DEFAULT FALSE,
    PRIMARY KEY(board_no),
    FOREIGN KEY (writer) REFERENCES member(nickname) ON DELETE CASCADE ON UPDATE CASCADE
);
-- 게시판 좋아요 테이블
CREATE TABLE IF NOT EXISTS board_like (
	id VARCHAR(30),
    board_no INT,
    FOREIGN KEY(id) REFERENCES member(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(board_no) REFERENCES board(board_no) ON DELETE CASCADE ON UPDATE CASCADE
);
-- 댓글 테이블
CREATE TABLE IF NOT EXISTS comment (
	comment_no INT AUTO_INCREMENT,
    writer VARCHAR(50),
    content VARCHAR(300),
    reg_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    board_no INT,
    parent_comment_number INT DEFAULT 0,
    PRIMARY KEY(comment_no),
    FOREIGN KEY (writer) REFERENCES member(nickname) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (board_no) REFERENCES board(board_no) ON UPDATE CASCADE ON DELETE CASCADE
);
-- 게시판 파일 저장 테이블
CREATE TABLE IF NOT EXISTS board_file (
	file_no INT AUTO_INCREMENT,
    ori_name VARCHAR(300),
    system_name VARCHAR(500),
    path VARCHAR(200),
    board_no INT,
    PRIMARY KEY(file_no),
    FOREIGN KEY(board_no) REFERENCES board(board_no) ON UPDATE CASCADE ON DELETE CASCADE
);


# 카테고리 테이블
CREATE TABLE IF NOT EXISTS category (
	category_no INT AUTO_INCREMENT,
    category_name VARCHAR(30) UNIQUE,
    PRIMARY KEY(category_no)
);


# 클래스 정보 관련 테이블
-- 클래스 목록 테이블
CREATE TABLE IF NOT EXISTS class (
	class_no INT AUTO_INCREMENT,
    leader VARCHAR(50),
    class_name VARCHAR(100),
    category VARCHAR(30),
    location VARCHAR(100),
    description VARCHAR(10000),
    head_count INT DEFAULT 0,
    price INT NOT NULL,
    PRIMARY KEY(class_no),
    FOREIGN KEY (leader) REFERENCES member(nickname) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (category) REFERENCES category(category_name) ON UPDATE CASCADE ON DELETE CASCADE
);
-- 클래스 파일 테이블
CREATE TABLE IF NOT EXISTS class_file (
	file_no INT AUTO_INCREMENT,
    ori_name VARCHAR(300),
    system_name VARCHAR(500),
    path VARCHAR(200),
    class_no INT,
    PRIMARY KEY(file_no),
    FOREIGN KEY(class_no) REFERENCES class(class_no) ON UPDATE CASCADE ON DELETE CASCADE
);
-- 클래스 신청 목록
CREATE TABLE IF NOT EXISTS class_regist (
	nickname VARCHAR(50),
    class_no INT,
    FOREIGN KEY (nickname) REFERENCES member(nickname) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (class_no) REFERENCES class(class_no) ON UPDATE CASCADE ON DELETE CASCADE
);
-- 클래스 수강 인원
CREATE TABLE IF NOT EXISTS class_member (
	nickname VARCHAR(50),
    class_no INT,
    FOREIGN KEY (nickname) REFERENCES member(nickname) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (class_no) REFERENCES class(class_no) ON UPDATE CASCADE ON DELETE CASCADE
);


# 모임 정보 관련 테이블
-- 모임 목록 테이블
CREATE TABLE IF NOT EXISTS club (
	club_no INT AUTO_INCREMENT,
    leader VARCHAR(50),
    club_name VARCHAR(100),
    category VARCHAR(30),
    location VARCHAR(100),
    description VARCHAR(10000),
    head_count INT DEFAULT 0,
    price INT NOT NULL,
    PRIMARY KEY(club_no),
    FOREIGN KEY (leader) REFERENCES member(nickname) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (category) REFERENCES category(category_name) ON UPDATE CASCADE ON DELETE CASCADE
);
-- 모임 파일 테이블
CREATE TABLE IF NOT EXISTS club_file (
	file_no INT AUTO_INCREMENT,
    ori_name VARCHAR(300),
    system_name VARCHAR(500),
    path VARCHAR(200),
    club_no INT,
    PRIMARY KEY(file_no),
    FOREIGN KEY(club_no) REFERENCES club(club_no) ON UPDATE CASCADE ON DELETE CASCADE
);
-- 모임 신청 목록
CREATE TABLE IF NOT EXISTS club_regist (
	nickname VARCHAR(50),
    club_no INT,
    FOREIGN KEY (nickname) REFERENCES member(nickname) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (club_no) REFERENCES club(club_no) ON UPDATE CASCADE ON DELETE CASCADE
);
-- 모임 수강 인원
CREATE TABLE IF NOT EXISTS club_member (
	nickname VARCHAR(50),
    club_no INT,
    FOREIGN KEY (nickname) REFERENCES member(nickname) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (club_no) REFERENCES club(club_no) ON UPDATE CASCADE ON DELETE CASCADE
);