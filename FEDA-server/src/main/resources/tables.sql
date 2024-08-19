CREATE database feda;

USE feda;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    role VARCHAR(20) NOT NULL,
    is_banned BOOLEAN NOT NULL DEFAULT TRUE,
    is_activated BOOLEAN NOT NULL DEFAULT FALSE,
    create_time DATETIME,
    update_time DATETIME
);

INSERT INTO user (username, password, role, is_banned, is_activated, create_time, update_time)
VALUES ('root', 'fd5cb51bafd60f6fdbedde6e62c473da6f247db271633e15919bab78a02ee9eb', 'ROOT', FALSE,TRUE,NOW(), NOW());

CREATE TABLE category (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          category_name VARCHAR(100) NOT NULL UNIQUE,
                          is_banned BOOLEAN NOT NULL DEFAULT FALSE,
                          create_time DATETIME,
                          update_time DATETIME
);

INSERT INTO category (category_name, is_banned, create_time, update_time)
VALUES ('主板块', FALSE, NOW(), NOW());

create table post
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT comment'帖子id',
    title VARCHAR(255) NOT NULL COMMENT'帖子标题',
    content TEXT NOT NULL COMMENT'帖子内容',
    category_id INT NOT NULL DEFAULT 1 comment '板块id',
    author_id BIGINT NOT NULL COMMENT'作者id',
    author_name VARCHAR(50) NOT NULL COMMENT '作者名',
    is_banned BOOLEAN NOT NULL DEFAULT FALSE,
    create_time DATETIME NOT NULL COMMENT'创建时间',
    update_time DATETIME NOT NULL COMMENT'更新时间（回帖）'
)
    COMMENT'发帖';


CREATE TABLE comment
(
    id BIGINT PRIMARY KEY auto_increment COMMENT'评论id',
    post_id BIGINT NOT NULL COMMENT'帖子id',
    parent_id BIGINT null COMMENT'回复的评论id',
    content TEXT NOT NULL COMMENT'评论内容',
    author_id BIGINT NOT NULL COMMENT'作者id',
    author_name VARCHAR(50) NOT NULL COMMENT'作者名',
    is_banned BOOLEAN NOT NULL DEFAULT FALSE,
    floor BIGINT NOT NULL COMMENT '楼层',
    create_time DATETIME NOT NULL COMMENT'创建时间'
)
    COMMENT'回帖';

CREATE TABLE message
(
    id BIGINT PRIMARY KEY auto_increment COMMENT'私信id',
    sender_id BIGINT NOT NULL COMMENT'发信人id',
    sender_name VARCHAR(50) NOT NULL COMMENT '发信人名',
    receiver_id BIGINT NOT NULL COMMENT'收信人id',
    receiver_name VARCHAR(50) NOT NULL COMMENT '收信人名',
    content TEXT NOT NULL COMMENT'私信内容',
    have_read BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否已读',
    create_time DATETIME NOT NULL COMMENT'创建时间'
)
    COMMENT'私信';


CREATE TABLE admin_action (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    admin_id BIGINT NOT NULL,
    action_type VARCHAR(255) NOT NULL,
    target_id BIGINT NOT NULL,
#     target_type VARCHAR(255) NOT NULL,
    timestamp DATETIME NOT NULL
);