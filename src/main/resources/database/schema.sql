DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;


CREATE TABLE roles
(
    id              IDENTITY                PRIMARY KEY NOT NULL,
    type            VARCHAR                 NOT NULL
);


CREATE TABLE users
(
    id              IDENTITY                PRIMARY KEY NOT NULL,
    name            VARCHAR                 NOT NULL,
    password        VARCHAR                 NOT NULL,
    role_id         INT                     NOT NULL,
    foreign key (role_id) references roles (id)
);
