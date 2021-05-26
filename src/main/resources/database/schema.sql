DROP INDEX IF EXISTS unique_userName_idx;
DROP INDEX IF EXISTS unique_projectName_idx;
DROP INDEX IF EXISTS unique_taskName_idx;
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
CREATE UNIQUE INDEX unique_userName_idx ON users(name);

CREATE TABLE projects
(
    id              IDENTITY                PRIMARY KEY NOT NULL,
    name            VARCHAR                 NOT NULL,
    manager_id      INT,
    foreign key (manager_id) references users(id) ON DELETE SET NULL
);
CREATE UNIQUE INDEX unique_projectName_idx ON projects(name);

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
CREATE UNIQUE INDEX unique_taskName_idx ON tasks(name,project_id);