# ⚽ Club Deportivo — Aplicación de Gestión

Aplicación de escritorio desarrollada en Java con arquitectura MVC, persistencia mediante el patrón DAO y interfaz gráfica Swing.

---

## 📋 Descripción

Sistema de gestión para un club deportivo que permite administrar jugadores, entrenadores, equipos y partidos. La aplicación cuenta con un sistema de login con registro de usuarios diferenciado por rol.

---

## 🛠️ Tecnologías utilizadas

- **Java 17**
- **MySQL** — base de datos relacional
- **JDBC** — conexión a la base de datos
- **Swing** — interfaz gráfica de escritorio
- **Patrón DAO** — capa de persistencia
- **Patrón MVC** — arquitectura de la aplicación

---

## 🗂️ Estructura del proyecto

```
src/
├── Main.java               ← Lanzador de la aplicación
├── db/
│   └── ConexionDB.java     ← Gestión de la conexión JDBC
├── model/
│   ├── Usuario.java        ← Entidad raíz de usuarios
│   ├── Jugador.java        ← Hereda de Usuario
│   ├── Entrenador.java     ← Hereda de Usuario
│   ├── Equipo.java         ← Entidad principal del dominio
│   └── Partido.java        ← Relación N:M entre equipos
├── dao/
│   ├── IUsuarioDAO.java
│   ├── UsuarioDAOImpl.java
│   ├── IJugadorDAO.java
│   ├── JugadorDAOImpl.java
│   ├── IEntrenadorDAO.java
│   ├── EntrenadorDAOImpl.java
│   ├── IEquipoDAO.java
│   ├── EquipoDAOImpl.java
│   ├── IPartidoDAO.java
│   └── PartidoDAOImpl.java
├── dto/
│   ├── PartidoDTO.java
│   ├── JugadorEquipoDTO.java
│   └── EquipoEntrenadorDTO.java
└── view/
    ├── Login.java          ← Ventana de inicio de sesión
    ├── Registro.java       ← Ventana de registro de usuarios
    └── Principal.java      ← Dashboard principal

documentacion/
├── README.md
├── documentacion.pdf
└── documentacion.docx
```

---

## 🗄️ Base de datos

**Nombre:** `club_db`

### Diagrama de tablas

| Tabla | Descripción |
|---|---|
| `usuarios` | Tabla raíz con datos comunes de todos los usuarios |
| `jugadores` | Tabla hija de usuarios (Joined Table Inheritance) |
| `entrenadores` | Tabla hija de usuarios (Joined Table Inheritance) |
| `equipos` | Entidad principal del dominio |
| `partidos` | Relación N:M entre equipos con resultado y jornada |
| `equipo_jugador` | Relación N:M entre jugadores y equipos |

### Campos principales de `usuarios`

| Campo | Tipo | Descripción |
|---|---|---|
| id | INT AUTO_INCREMENT | Clave primaria |
| usuario | VARCHAR(50) | Nombre de usuario único |
| contraseña | VARCHAR(255) | Contraseña |
| email | VARCHAR(100) | Email único |
| nombre | VARCHAR(80) | Nombre real |
| apellidos | VARCHAR(120) | Apellidos |
| dni | VARCHAR(20) | DNI único |
| rol | ENUM | jugador / entrenador / admin |

### Relaciones

- `jugadores.usuario_id` → `usuarios.id` (ON DELETE CASCADE)
- `entrenadores.usuario_id` → `usuarios.id` (ON DELETE CASCADE)
- `equipos.entrenador_id` → `entrenadores.usuario_id` (ON DELETE SET NULL)
- `partidos.equipo_local_id` → `equipos.id` (ON DELETE CASCADE)
- `partidos.equipo_visit_id` → `equipos.id` (ON DELETE CASCADE)
- `equipo_jugador.equipo_id` → `equipos.id` (ON DELETE CASCADE)
- `equipo_jugador.jugador_id` → `jugadores.usuario_id` (ON DELETE CASCADE)

---

## ▶️ Instalación y ejecución

### 1. Requisitos previos

- Java 17 o superior
- MySQL 8 o superior
- Conector JDBC de MySQL (`mysql-connector-j`)

### 2. Configurar la base de datos

Ejecuta el script SQL incluido en el proyecto:

```sql
-- Desde MySQL Workbench o terminal:
source schema.sql;
```

### 3. Configurar la conexión

Edita el archivo `src/db/ConexionDB.java` y ajusta los datos de conexión:

```java
private static final String URL      = "jdbc:mysql://localhost:3306/club_db";
private static final String USER     = "root";
private static final String PASSWORD = "tu_contraseña";
```

### 4. Ejecutar la aplicación

Compila y ejecuta `Main.java`. La aplicación abrirá la ventana de login.

**Usuario de prueba:**
- Usuario: `admin`
- Contraseña: `admin123`

---

## 🖥️ Funcionalidades

### Login
- Validación de credenciales contra la base de datos
- Acceso a registro de nuevos usuarios

### Registro
- Formulario dinámico que cambia según el rol seleccionado (jugador / entrenador)
- Inserción atómica en `usuarios` + tabla hija con transacción y rollback

### Dashboard principal
- **Módulo Jugadores** — CRUD completo de jugadores
- **Módulo Entrenadores** — CRUD completo de entrenadores
- **Módulo Equipos** — CRUD completo de equipos
- **Módulo Partidos** — CRUD completo de partidos con resultado via JOIN
- Cambio de contraseña desde el menú
- Tema claro / oscuro desde preferencias

---

## 🏗️ Arquitectura MVC

| Capa | Paquete | Responsabilidad |
|---|---|---|
| Modelo | `model` | Entidades del dominio (POJOs) |
| Vista | `view` | Interfaz gráfica Swing |
| Controlador | `dao` | Lógica de acceso a datos |

La vista nunca accede directamente a la base de datos: siempre lo hace a través de las interfaces DAO, lo que garantiza el desacoplamiento entre capas.

---

## 📌 Patrón DAO

Cada entidad tiene su interfaz y su implementación:

```
IUsuarioDAO  ←  UsuarioDAOImpl
IJugadorDAO  ←  JugadorDAOImpl
IEntrenadorDAO ← EntrenadorDAOImpl
IEquipoDAO   ←  EquipoDAOImpl
IPartidoDAO  ←  PartidoDAOImpl
```

Todas las operaciones usan `PreparedStatement` y `try-with-resources`. Las operaciones que afectan a varias tablas usan transacciones manuales (`setAutoCommit`, `commit`, `rollback`).

---

## 👤 Autor

- **Nombre:** Juan Manuel Aguilera López
- **Módulo:** Programación
- **Curso:** 2025/2026
- **Enlace Github:** https://github.com/Juanmilla-07/Proyecto-Final-Programacion/commits/main/