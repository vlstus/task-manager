DELETE FROM projects;
DELETE FROM tasks;
DELETE FROM users;
DELETE FROM tasks_developers;


INSERT INTO users (id,name,password,role) VALUES
(100000,'John Doe','12345', 0),
(100001,'Jane Doe','12345', 1),
(100002,'Jack Doe','12345', 2);

INSERT INTO projects (id,name,manager_id) VALUES
(100000,'Tasks Management', (SELECT id FROM users WHERE users.name = 'Jane Doe') );

INSERT INTO tasks (name,project_id,status,manager_id) VALUES
('Define domain model',
 (SELECT id FROM projects WHERE projects.name = 'Tasks Management'),
  0,
   (SELECT id FROM users WHERE users.name = 'Jane Doe'));

 INSERT INTO tasks_developers (task_id,developer_id) VALUES
 ((SELECT id FROM tasks WHERE tasks.name = 'Define domain model'),
  (SELECT id FROM users WHERE users.name = 'John Doe'))