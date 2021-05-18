DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS tasks_developers;


CREATE TABLE users
(
    id          IDENTITY                NOT NULL PRIMARY KEY,
    name        VARCHAR                 NOT NULL,
    password    VARCHAR                 NOT NULL,
    role        INT
);
CREATE UNIQUE INDEX users_unique_idx ON users(name);

CREATE TABLE projects
(
    id          IDENTITY                NOT NULL PRIMARY KEY,
    name        VARCHAR                 NOT NULL,
    manager_id  INT,
    foreign key(manager_id) references users(id)
);
CREATE UNIQUE INDEX projects_unique_idx ON projects(name);

CREATE TABLE tasks
(
    id          IDENTITY                NOT NULL PRIMARY KEY,
    name        VARCHAR                 NOT NULL,
    project_id  INT,
    status      INT,
    manager_id  INT,
    foreign key(project_id) references projects(id),
    foreign key(manager_id) references users(id)
);
CREATE UNIQUE INDEX tasks_unique_idx ON tasks(name,project_id);

CREATE TABLE tasks_developers
(
    task_id         INT,
    developer_id    INT,
    foreign key(task_id) references tasks(id),
    foreign key(developer_id) references users(id)
);
CREATE UNIQUE INDEX tasks_developers_unique_relation ON tasks_developers(task_id,developer_id);