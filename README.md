# Homework 4 from IT-camp by T1
Topic: Spring Security
### How to use:
1) Run Spring Boot Application
2) Register with ``POST /auth/signup`` endpoint. Here is request body example:
```
{
  "login": "micha",
  "password": "123",
  "email": "qwgf@gmail.com",
  "roles": ["ROLE_ADMIN", "ROLE_PREMIUM_USER", "ROLE_GUEST"]
}
```
3) Sign in with ``POST /auth/signin`` endpoint. It will return jwt access-token
4) Add header ``Authorization: Bearer [token]`` to your request, now you can access all(depending on your roles) /test endpoints

To refresh the token, use ``POST /auth/refresh`` here's the example:
```
{
    "refresh_token": "bff7853a-6b1f-47fd-95ee-58d5b7bfc69c"
}
```
You can get refresh token in the same response where you got access token

Jwt expiration time is set to 120 seconds to ease testing of refreshing
