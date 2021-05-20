DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id              IDENTITY                PRIMARY KEY NOT NULL,
    name            VARCHAR                 NOT NULL,
    password        VARCHAR                 NOT NULL,
    role            VARCHAR                 NOT NULL
);

CREATE TABLE projects
(
    id              IDENTITY                PRIMARY KEY NOT NULL,
    name            VARCHAR                 NOT NULL,
    manager_id      INT                     NOT NULL,
    foreign key (manager_id) references users(id)
);

CREATE TABLE tasks
(
    id              IDENTITY                PRIMARY KEY NOT NULL,
    name            VARCHAR                 NOT NULL,
    status          VARCHAR                 NOT NULL,
    manager_id      INT                     NOT NULL,
    project_id      INT                     NOT NULL,
    developer_id    INT                     NOT NULL,
    foreign key (manager_id) references users(id),
    foreign key (project_id) references projects(id),
    foreign key (developer_id) references users(id)
);