DELETE FROM tasks;
DELETE FROM projects;
DELETE FROM users;

INSERT INTO users (id,name,password,role) VALUES
(
100000,
'John Doe',
'$2a$06$a/9qGOaM0OV56tWx34kvHuWAG2cnSHG337JWtkzeMa6jML/6bVft2',
'ROLE_DEVELOPER'
),
(
100001,
'Jane Doe',
'$2a$06$a/9qGOaM0OV56tWx34kvHuWAG2cnSHG337JWtkzeMa6jML/6bVft2',
'ROLE_MANAGER'
),
(
100002,
'Joe Doe',
'$2a$06$a/9qGOaM0OV56tWx34kvHuWAG2cnSHG337JWtkzeMa6jML/6bVft2',
'ROLE_ADMIN'
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




