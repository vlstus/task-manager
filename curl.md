### curl samples (application deployed at application context `topjava`).
> For windows use `Git Bash`

#### get All Users
`curl --request GET 'http://localhost:8080/api/v1/users/'`

#### get User with ID 100001
`curl  --request GET 'http://localhost:8080/api/v1/users/100002'`

#### create User
`curl --request POST 'http://localhost:8080/api/v1/users/' \
--header 'Content-Type: application/json' \
--data-raw '    {
"name": "Joe Doe",
"password": "Password",
"role": "DEVELOPER"
}'`

