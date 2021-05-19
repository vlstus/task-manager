DELETE FROM roles;
DELETE FROM users;
DELETE FROM statuses;
DELETE FROM tasks;

INSERT INTO roles (type) VALUES
(
'DEVELOPER'
),
(
'MANAGER'
),
(
'ADMIN'
);


INSERT INTO users (name,password,role_id) VALUES
(
'John Doe',
'Password',
(SELECT id FROM roles WHERE roles.type = 'DEVELOPER')
),
(
'Jane Doe',
'Password',
(SELECT id FROM roles WHERE roles.type = 'MANAGER')
);


INSERT INTO statuses (type) VALUES
(
'TO_DO'
),
(
'IN_PROGRESS'
),
(
'DONE'
);

INSERT INTO tasks (name,status_id,manager_id) VALUES
(
'DESIGN DOMAIN MODEL',
(SELECT id FROM statuses WHERE statuses.type = 'IN_PROGRESS'),
(SELECT id FROM users WHERE users.role_id = (SELECT id FROM roles WHERE roles.type = 'MANAGER') AND users.name = 'Jane Doe')
);

INSERT INTO tasks_developers (task_id,developer_id) VALUES
(
(SELECT id FROM users WHERE users.role_id = (SELECT id FROM roles WHERE roles.type = 'DEVELOPER') AND users.name = 'John Doe'),
(SELECT id FROM tasks WHERE tasks.name = 'DESIGN DOMAIN MODEL')
);