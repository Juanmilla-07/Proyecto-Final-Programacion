CREATE DATABASE club_db
    DEFAULT CHARACTER SET = 'utf8mb4';

-- ────────────────────────────────────────────────────────────
-- 1. Tabla raíz de usuarios (OBLIGATORIA)
-- ────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS usuarios (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    usuario  VARCHAR(50)  NOT NULL UNIQUE,
    contraseña   VARCHAR(255) NOT NULL,
    email      VARCHAR(100) NOT NULL UNIQUE,
    nombre     VARCHAR(80)  NOT NULL,
    apellidos  VARCHAR(120) NOT NULL,
    dni        VARCHAR(20)  NOT NULL UNIQUE,
    rol        ENUM('jugador','entrenador','admin') NOT NULL DEFAULT 'jugador'
);

-- ────────────────────────────────────────────────────────────
-- 2. Tablas hija — Joined Table Inheritance
-- ────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS jugadores (
    usuario_id    INT PRIMARY KEY,
    posicion      VARCHAR(50),
    dorsal        INT,
    fecha_nac     DATE,
    CONSTRAINT fk_jug FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id) ON DELETE CASCADE
);
 
CREATE TABLE IF NOT EXISTS entrenadores (
    usuario_id    INT PRIMARY KEY,
    especialidad  VARCHAR(100),
    licencia      VARCHAR(50),
    CONSTRAINT fk_ent FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id) ON DELETE CASCADE
);
 
-- ────────────────────────────────────────────────────────────
-- 3. Entidad principal del dominio: equipos
-- ────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS equipos (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    nombre        VARCHAR(100) NOT NULL,
    categoria     VARCHAR(50)  NOT NULL,
    entrenador_id INT,
    puntos        INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_equipo_ent FOREIGN KEY (entrenador_id)
        REFERENCES entrenadores(usuario_id) ON DELETE SET NULL
);
 
-- ────────────────────────────────────────────────────────────
-- 4. Tabla N:M — partidos (equipo_local vs equipo_visitante)
-- ────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS partidos (
    id               INT AUTO_INCREMENT PRIMARY KEY,
    equipo_local_id  INT  NOT NULL,
    equipo_visit_id  INT  NOT NULL,
    fecha            DATE NOT NULL,
    goles_local      INT  NOT NULL DEFAULT 0,
    goles_visitante  INT  NOT NULL DEFAULT 0,
    jornada          INT  NOT NULL DEFAULT 1,
    CONSTRAINT fk_local FOREIGN KEY (equipo_local_id)
        REFERENCES equipos(id) ON DELETE CASCADE,
    CONSTRAINT fk_visit FOREIGN KEY (equipo_visit_id)
        REFERENCES equipos(id) ON DELETE CASCADE
);
 
-- ────────────────────────────────────────────────────────────
-- 5. Relación N:M — jugadores pertenecen a equipos
-- ────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS equipo_jugador (
    equipo_id   INT NOT NULL,
    jugador_id  INT NOT NULL,
    fecha_alta  DATE NOT NULL,
    PRIMARY KEY (equipo_id, jugador_id),
    CONSTRAINT fk_ej_equipo  FOREIGN KEY (equipo_id)
        REFERENCES equipos(id) ON DELETE CASCADE,
    CONSTRAINT fk_ej_jugador FOREIGN KEY (jugador_id)
        REFERENCES jugadores(usuario_id) ON DELETE CASCADE
);