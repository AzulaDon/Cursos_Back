# Plataforma de Cursos - Backend API

API REST desarrollada con Spring Boot 4.0 para una plataforma de cursos en linea. Proporciona autenticacion JWT, gestion de cursos, inscripciones, seguimiento de progreso y generacion de certificados.

## Tecnologias

- **Java 21**
- **Spring Boot 4.0.5**
- **Spring Security** con JWT
- **Spring Data JPA**
- **MariaDB**
- **Lombok**
- **Gradle**

## Requisitos Previos

- JDK 21+
- MariaDB 10.5+
- Gradle 9.3+

## Configuracion

### Base de Datos

Crear la base de datos en MariaDB:

```sql
CREATE DATABASE db_plataforma_cursos;
```

### Variables de Configuracion

Editar `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:mariadb://localhost:3306/db_plataforma_cursos
    username: root
    password: tu_password

jwt:
  secret: TU_CLAVE_SECRETA_MUY_LARGA_Y_SEGURA
  expiration: 86400000  # 24 horas en milisegundos

cors:
  allowed-origins: http://localhost:3000,http://localhost:5173
```

## Ejecucion

```bash
# Desarrollo
./gradlew bootRun

# Compilar
./gradlew build

# Tests
./gradlew test
```

El servidor iniciara en `http://localhost:8080`

---

## Endpoints API

### Autenticacion (`/api/auth`)

| Metodo | Endpoint | Descripcion | Acceso |
|--------|----------|-------------|--------|
| POST | `/registro` | Registrar nuevo usuario | Publico |
| POST | `/login` | Iniciar sesion | Publico |

**Registro:**
```json
POST /api/auth/registro
{
  "nombre": "Juan Perez",
  "correo": "juan@email.com",
  "password": "password123",
  "plataforma": "web"
}
```

**Login:**
```json
POST /api/auth/login
{
  "correo": "juan@email.com",
  "password": "password123"
}
```

**Respuesta exitosa:**
```json
{
  "success": true,
  "message": "Inicio de sesion exitoso",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIs...",
    "usuario": {
      "id": 1,
      "nombre": "Juan Perez",
      "correo": "juan@email.com",
      "rol": "usuario"
    }
  }
}
```

---

### Cursos (`/api/cursos`)

| Metodo | Endpoint | Descripcion | Acceso |
|--------|----------|-------------|--------|
| GET | `/` | Obtener cursos activos | Publico |
| GET | `/todos` | Obtener todos los cursos | Publico |
| GET | `/{id}` | Obtener curso por ID | Publico |
| GET | `/categoria/{categoria}` | Filtrar por categoria | Publico |
| GET | `/buscar?q={query}` | Buscar cursos | Publico |
| POST | `/` | Crear curso | Admin |
| PUT | `/{id}` | Actualizar curso | Admin |
| DELETE | `/{id}` | Eliminar curso | Admin |

**Crear curso (Admin):**
```json
POST /api/cursos
Authorization: Bearer {token}
{
  "titulo": "Curso de Java",
  "descripcion": "Aprende Java desde cero",
  "categoria": "Programacion",
  "portadaUrl": "https://example.com/imagen.jpg",
  "docenteId": 1,
  "duracion": 40,
  "activo": true
}
```

---

### Videos (`/api/videos`)

| Metodo | Endpoint | Descripcion | Acceso |
|--------|----------|-------------|--------|
| GET | `/curso/{cursoId}` | Videos de un curso | Autenticado |
| GET | `/{id}` | Video por ID | Autenticado |
| POST | `/` | Crear video | Admin |
| PUT | `/{id}` | Actualizar video | Admin |
| DELETE | `/{id}` | Eliminar video | Admin |

**Crear video (Admin):**
```json
POST /api/videos
Authorization: Bearer {token}
{
  "cursoId": 1,
  "titulo": "Introduccion a Java",
  "descripcion": "Video introductorio",
  "urlVideo": "https://youtube.com/watch?v=xxx",
  "duracion": 15,
  "orden": 1
}
```

---

### Inscripciones (`/api/inscripciones`)

| Metodo | Endpoint | Descripcion | Acceso |
|--------|----------|-------------|--------|
| GET | `/mis-cursos` | Cursos del usuario | Autenticado |
| GET | `/completados` | Cursos completados | Autenticado |
| GET | `/{id}` | Inscripcion por ID | Autenticado |
| GET | `/curso/{cursoId}` | Inscripcion en curso | Autenticado |
| GET | `/verificar/{cursoId}` | Verificar si esta inscrito | Autenticado |
| POST | `/curso/{cursoId}` | Inscribirse a curso | Autenticado |

**Inscribirse:**
```json
POST /api/inscripciones/curso/1
Authorization: Bearer {token}
```

---

### Progreso (`/api/progreso`)

| Metodo | Endpoint | Descripcion | Acceso |
|--------|----------|-------------|--------|
| GET | `/inscripcion/{id}` | Progreso de inscripcion | Autenticado |
| GET | `/inscripcion/{id}/video/{videoId}` | Progreso de video | Autenticado |
| POST | `/inscripcion/{id}` | Guardar progreso | Autenticado |

**Guardar progreso:**
```json
POST /api/progreso/inscripcion/1
Authorization: Bearer {token}
{
  "videoId": 5,
  "completado": true
}
```

---

### Calificaciones (`/api/calificaciones`)

| Metodo | Endpoint | Descripcion | Acceso |
|--------|----------|-------------|--------|
| GET | `/curso/{cursoId}` | Calificaciones del curso | Autenticado |
| GET | `/curso/{cursoId}/promedio` | Promedio del curso | Autenticado |
| GET | `/inscripcion/{id}` | Calificacion de inscripcion | Autenticado |
| POST | `/inscripcion/{id}` | Calificar curso | Autenticado |

**Calificar:**
```json
POST /api/calificaciones/inscripcion/1
Authorization: Bearer {token}
{
  "estrellas": 5,
  "comentario": "Excelente curso, muy completo"
}
```

---

### Certificados (`/api/certificados`)

| Metodo | Endpoint | Descripcion | Acceso |
|--------|----------|-------------|--------|
| GET | `/mis-certificados` | Certificados del usuario | Autenticado |
| GET | `/inscripcion/{id}` | Certificado de inscripcion | Autenticado |
| GET | `/verificar/{codigo}` | Verificar certificado | Publico |
| POST | `/generar/{inscripcionId}` | Generar certificado | Autenticado |

---

### Docentes (`/api/docentes`)

| Metodo | Endpoint | Descripcion | Acceso |
|--------|----------|-------------|--------|
| GET | `/` | Listar docentes | Autenticado |
| GET | `/{id}` | Docente por ID | Autenticado |
| GET | `/buscar?especialidad={esp}` | Buscar por especialidad | Autenticado |
| POST | `/` | Crear docente | Admin |
| PUT | `/{id}` | Actualizar docente | Admin |
| DELETE | `/{id}` | Eliminar docente | Admin |

---

### Categorias (`/api/categorias`)

| Metodo | Endpoint | Descripcion | Acceso |
|--------|----------|-------------|--------|
| GET | `/` | Listar categorias | Publico |

---

### Administracion (`/api/admin`)

| Metodo | Endpoint | Descripcion | Acceso |
|--------|----------|-------------|--------|
| GET | `/usuarios` | Listar usuarios | Admin |
| GET | `/dashboard/progreso` | Dashboard de progreso | Admin |
| GET | `/dashboard/calificaciones` | Dashboard calificaciones | Admin |
| GET | `/estadisticas` | Estadisticas generales | Admin |

---

## Estructura de Respuestas

Todas las respuestas siguen el formato:

```json
{
  "success": true,
  "message": "Mensaje descriptivo",
  "data": { ... }
}
```

**Error:**
```json
{
  "success": false,
  "message": "Descripcion del error",
  "data": null
}
```

---

## Autenticacion

La API utiliza JWT (JSON Web Tokens). Para endpoints protegidos, incluir el header:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIs...
```

---

## Roles de Usuario

| Rol | Descripcion |
|-----|-------------|
| `usuario` | Puede inscribirse, ver cursos, registrar progreso |
| `admin` | Acceso completo: CRUD de cursos, videos, docentes y dashboard |

---

## Estructura del Proyecto

```
src/main/java/com/curso/OmetepecGuerrero/
├── config/
│   ├── CorsConfig.java
│   └── SecurityConfig.java
├── controller/
│   ├── AdminController.java
│   ├── AuthController.java
│   ├── CalificacionController.java
│   ├── CategoriaController.java
│   ├── CertificadoController.java
│   ├── CursoController.java
│   ├── DocenteController.java
│   ├── InscripcionController.java
│   ├── ProgresoController.java
│   └── VideoController.java
├── dto/
│   ├── request/
│   └── response/
├── entity/
│   ├── Calificacion.java
│   ├── Certificado.java
│   ├── Curso.java
│   ├── Docente.java
│   ├── Inscripcion.java
│   ├── ProgresoVista.java
│   ├── Usuario.java
│   └── Video.java
├── exception/
├── repository/
├── security/
│   ├── JwtAuthenticationFilter.java
│   ├── JwtService.java
│   ├── UserDetailsImpl.java
│   └── UserDetailsServiceImpl.java
├── service/
└── OmetepecGuerreroApplication.java
```

---

## Entidades Principales

### Usuario
- Roles: `admin`, `usuario`
- Plataformas: `web`, `app`, `ambas`

### Curso
- Asociado a un docente
- Tiene multiples videos
- Categorizado

### Inscripcion
- Relaciona usuario con curso
- Registra porcentaje de avance
- Marca si esta completado

### Certificado
- Se genera al completar un curso
- Codigo unico verificable

---

## Licencia

Este proyecto es de uso academico.
