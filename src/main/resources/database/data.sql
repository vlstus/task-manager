DELETE FROM projects;
DELETE FROM tasks;
DELETE FROM statuses;
DELETE FROM users;
DELETE FROM roles;
DELETE FROM tasks_developers;

INSERT INTO roles (name) VALUES
('DEVELOPER'),
('ADMIN'),
('MANAGER');

INSERT INTO statuses (name) VALUES
('TO_DO'),
('IN_PROGRESS'),
('DONE');

INSERT INTO users (name,password,role_id) VALUES
('John Doe','12345', (SELECT id FROM roles WHERE roles.name = 'DEVELOPER')),
('Jane Doe','12345', (SELECT id FROM roles WHERE roles.name = 'MANAGER')),
('Jack Doe','12345', (SELECT id FROM roles WHERE roles.name = 'ADMIN'));

INSERT INTO projects (name,manager_id) VALUES
('Tasks Management',
(SELECT id FROM users WHERE users.name = 'Jane Doe' AND
 users.role_id = (SELECT id FROM roles WHERE roles.name = 'MANAGER')));

INSERT INTO tasks (name,project_id,status_id,manager_id) VALUES
('Define domain model',
(SELECT id FROM projects WHERE projects.name = 'Tasks Management'),
(SELECT id FROM statuses WHERE statuses.name = 'IN_PROGRESS'),
(SELECT id FROM users WHERE users.name = 'Jane Doe' AND
 users.role_id = (SELECT id FROM roles WHERE roles.name = 'MANAGER')));

 INSERT INTO tasks_developers (task_id,developer_id) VALUES
 ((SELECT id FROM tasks WHERE tasks.name = 'Define domain model'),
 (SELECT id FROM users WHERE users.name = 'John Doe' AND
  users.role_id = (SELECT id FROM roles WHERE roles.name = 'DEVELOPER')))