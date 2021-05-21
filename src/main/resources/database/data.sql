DELETE FROM tasks;
DELETE FROM projects;
DELETE FROM users;

INSERT INTO users (id,name,password,role) VALUES
(
100000,
'John Doe',
'Password',
'DEVELOPER'
),
(
100001,
'Jane Doe',
'Password',
'MANAGER'
);

INSERT INTO projects (id,name,manager_id) VALUES
(
100000,
'Task Management',
(SELECT id FROM users WHERE users.name = 'Jane Doe')
);

INSERT INTO tasks (id,name,status,manager_id,project_id,developer_id) VALUES
(
100000,
'DESIGN DOMAIN MODEL',
'TO_DO',
(SELECT id FROM users WHERE users.name = 'Jane Doe'),
(SELECT id FROM projects WHERE projects.name = 'Task Management'),
(SELECT id FROM users WHERE users.name = 'John Doe')
);




