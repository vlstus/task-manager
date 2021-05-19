DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS tasks_developers;
DROP TABLE IF EXISTS statuses;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS projects_tasks;
DROP TABLE IF EXISTS projects;


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


CREATE TABLE statuses
(
    id              IDENTITY                PRIMARY KEY NOT NULL,
    type            VARCHAR                 NOT NULL
);

CREATE TABLE tasks
(
    id              IDENTITY                PRIMARY KEY NOT NULL,
    name            VARCHAR                 NOT NULL,
    status_id       INT                     NOT NULL,
    manager_id      INT                     NOT NULL,
    foreign key (status_id) references statuses(id),
    foreign key (manager_id) references users(id)
);

CREATE TABLE tasks_developers
(
    task_id         INT                     NOT NULL,
    developer_id    INT                     NOT NULL,
    foreign key (task_id) references tasks(id),
    foreign key (developer_id) references users(id)
);


CREATE TABLE projects
(
    id              IDENTITY                PRIMARY KEY NOT NULL,
    name            VARCHAR                 NOT NULL,
    manager_id      INT                     NOT NULL,
    foreign key (manager_id) references users(id)
);

CREATE TABLE projects_tasks
(
    project_id      INT                     NOT NULL,
    task_id         INT                     NOT NULL,
    foreign key (project_id) references projects(id),
    foreign key (task_id) references tasks(id)
);