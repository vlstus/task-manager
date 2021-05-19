 DELETE FROM roles;
 DELETE FROM users;


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