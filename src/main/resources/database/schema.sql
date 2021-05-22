DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS projects;
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
    manager_id      INT,
    foreign key (manager_id) references users(id) ON DELETE SET NULL
);

CREATE TABLE tasks
(
    id              IDENTITY                PRIMARY KEY NOT NULL,
    name            VARCHAR                 NOT NULL,
    status          VARCHAR                 NOT NULL,
    manager_id      INT,
    project_id      INT,
    developer_id    INT,
    foreign key (manager_id) references users(id) ON DELETE SET NULL,
    foreign key (project_id) references projects(id) ON DELETE CASCADE,
    foreign key (developer_id) references users(id) ON DELETE SET NULL
);