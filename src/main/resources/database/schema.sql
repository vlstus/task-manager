DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS statuses;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS tasks_developers;


CREATE TABLE roles
(
    id          IDENTITY                NOT NULL PRIMARY KEY,
    name        VARCHAR                 NOT NULL
);
CREATE UNIQUE INDEX roles_unique_id ON roles(id);

CREATE TABLE statuses
(
    id          IDENTITY                NOT NULL PRIMARY KEY,
    name        VARCHAR                 NOT NULL
);
CREATE UNIQUE INDEX statuses_unique_idx  ON statuses(name);

CREATE TABLE users
(
    id          IDENTITY                NOT NULL PRIMARY KEY,
    name        VARCHAR                 NOT NULL,
    password    VARCHAR                 NOT NULL,
    role_id     INT,
    foreign key(role_id) references roles(id)
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
    status_id   INT,
    manager_id  INT,
    foreign key(project_id) references projects(id),
    foreign key(status_id) references statuses(id),
    foreign key(manager_id) references users(id)
);
CREATE UNIQUE INDEX tasks_unique_idx ON tasks(name);

CREATE TABLE tasks_developers
(
    task_id         INT,
    developer_id    INT,
    foreign key(task_id) references tasks(id),
    foreign key(developer_id) references users(id)
);
CREATE UNIQUE INDEX tasks_developers_unique_relation ON tasks_developers(task_id,developer_id);