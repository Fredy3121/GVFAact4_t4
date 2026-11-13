# Actividad 4 - Tema 4: API REST Pura con Spring Security, JWT, DTOs, Validaciones y Bruno
## Alumno: Fredy Azarel Gómez Vasquez

API RESTful desarrollada con Spring Boot que implementa autenticación real y sin estado (*stateless*) mediante **Spring Security** y **JSON Web Tokens (JWT)**, persistencia relacional en **MySQL**, paginación de resultados, transferencia segura de datos mediante **DTOs**, validación de entrada con **Bean Validation (`@Valid`)** y colección de pruebas versionada en **Bruno**.

---

## 1. Tecnologías y Herramientas

- **Lenguaje:** Java 21
- **Framework:** Spring Boot (Spring Web, Spring Data JPA, Spring Security, Bean Validation)
- **Seguridad:** JSON Web Tokens (`io.jsonwebtoken:jjwt` v0.12.6) + BCryptPasswordEncoder
- **Base de Datos:** MySQL Server (`actividad4_db`)
- **Gestor de Dependencias:** Apache Maven
- **Cliente API Versionado:** Bruno (`./bruno`)
- **Despliegue:** VPS Ubuntu Linux (Puerto `8087`)

---

## 2. Arquitectura y Endpoints de la API

### 2.1. Autenticación (Rutas Públicas `/api/auth/**`)

| Método | Endpoint | Descripción | Códigos HTTP |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Registra un nuevo usuario encriptando su contraseña y devuelve un JWT | `201 Created`, `400 Bad Request` |
| `POST` | `/api/auth/login` | Autentica credenciales y genera un token de acceso `Bearer` | `200 OK`, `401 Unauthorized` |

### 2.2. Gestión de Productos (Rutas Protegidas `/api/productos/**` — Requieren `Bearer <JWT>`)

| Método | Endpoint | Descripción | Códigos HTTP |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/productos?page=0&size=5` | Lista los productos de forma paginada mediante `ProductoResponseDTO` | `200 OK`, `401 Unauthorized` |
| `GET` | `/api/productos/{id}` | Consulta un producto específico por su identificador | `200 OK`, `404 Not Found`, `401 Unauthorized` |
| `POST` | `/api/productos` | Crea un producto validando los datos de entrada con `@Valid` | `201 Created`, `400 Bad Request`, `401 Unauthorized` |
| `PUT` | `/api/productos/{id}` | Actualiza los datos y categoría de un producto existente | `200 OK`, `400 Bad Request`, `404 Not Found` |
| `DELETE` | `/api/productos/{id}` | Elimina un producto de la base de datos | `200 OK`, `404 Not Found`, `401 Unauthorized` |

---

## 3. Colección de Bruno

La colección completa de pruebas de la API se encuentra almacenada y versionada dentro de la carpeta **`/bruno`** en la raíz de este repositorio, lista para importarse directamente en **Bruno** (`bruno.io`).

---

## 4. Evidencia de Pruebas en Bruno

### 4.1. Bloqueo de Seguridad sin Token (`401 Unauthorized`)
Petición a un endpoint protegido (`/api/productos`) sin incluir encabezado `Authorization`, verificando que Spring Security rechace el acceso.

![Acceso Denegado Sin Token](img/01_sin_token_401.png)

---

### 4.2. Registro de Usuario (`POST /api/auth/register`)
Creación de cuenta con validación de campos (`@Email`, `@Size`) y generación del token JWT sin exponer la contraseña en la respuesta.

![Registro de Usuario](img/02_register_jwt.png)

---

### 4.3. Inicio de Sesión (`POST /api/auth/login`)
Autenticación de usuario registrado y obtención del token `Bearer` válido.

![Login de Usuario](img/03_login_jwt.png)

---

### 4.4. Operación CREATE (`POST /api/productos` con JWT)
Creación de un nuevo producto enviando el token en `Auth -> Bearer Token` y validando los datos mediante `ProductoRequestDTO`.

![Crear Producto JWT](img/04_post_crear_producto.png)

---

### 4.5. Operación READ Paginada (`GET /api/productos` con JWT)
Consulta del listado de productos paginado con la información de la categoría integrada en `ProductoResponseDTO`.

![Listar Productos Paginado](img/05_get_productos_paginado.png)

---

### 4.6. Operación UPDATE (`PUT /api/productos/{id}` con JWT)
Actualización de un producto existente devolviendo código `200 OK`.

![Actualizar Producto](img/06_put_actualizar_producto.png)

---

### 4.7. Operación DELETE (`DELETE /api/productos/{id}` con JWT)
Eliminación de un registro en MySQL confirmada con respuesta JSON.

![Eliminar Producto](img/07_delete_eliminar_producto.png)

---

## 5. Despliegue en el Servidor VPS

Las cuatro actividades del tema se encuentran activas y ejecutándose de manera concurrente e independiente en sus respectivos puertos:

- **Actividad 4 — API REST + JWT (Puerto 8087):**  
  `http://179.197.84.73:8087/api`
- **Actividad 3 — CRUD JPA + MySQL (Puerto 8085):**  
  `http://179.197.84.73:8085/api/productos`
- **Actividad 2 — MVC + Thymeleaf (Puerto 8083):**  
  `http://179.197.84.73:8083/productos/lista`
- **Actividad 1 — REST Básico (Puerto 8081):**  
  `http://179.197.84.73:8081/api/info/proyecto`