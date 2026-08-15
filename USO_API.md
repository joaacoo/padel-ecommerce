
Base URL: `http://localhost:8080`

## Registrar usuario

`POST /api/users/register`

```json
{"nombre":"Lucas","email":"lucas@example.com","password":"123456"}
```

## Iniciar sesión

`POST /api/users/login`

```json
{"email":"lucas@example.com","password":"123456"}
```

## Obtener usuario

`GET /api/users/{id}`

Ejemplo: `GET http://localhost:8080/api/users/1`

En los POST enviar el header `Content-Type: application/json`.
