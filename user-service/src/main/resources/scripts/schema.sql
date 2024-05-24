DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id          bigint NOT NULL  AUTO_INCREMENT,
    email       varchar(100),
    username    varchar(50),
    password    varchar(500),
    profile_url varcahr(500),
    created_at  timestamp DEFAULT NOW(),
    updated_at  timestamp DEFAULT NOW(),

    primary key(id)
)