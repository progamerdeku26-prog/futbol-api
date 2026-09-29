-- =====================================================
-- Base de datos: futbol_db
-- Plan de mejoramiento - API REST equipo de futbol
-- Ejecutar completo en MySQL Workbench (Ctrl + Shift + Enter)
-- =====================================================

DROP DATABASE IF EXISTS futbol_db;
CREATE DATABASE futbol_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE futbol_db;

-- 1. Equipo
CREATE TABLE equipo (
    id_equipo  INT AUTO_INCREMENT PRIMARY KEY,
    nombre     VARCHAR(100) NOT NULL,
    ciudad     VARCHAR(100),
    fundacion  DATE
);

-- 2. Jugador (N jugadores -> 1 equipo)
CREATE TABLE jugador (
    id_jugador   INT AUTO_INCREMENT PRIMARY KEY,
    nombre       VARCHAR(100) NOT NULL,
    posicion     VARCHAR(50),
    dorsal       INT,
    fecha_nac    DATE,
    nacionalidad VARCHAR(100),
    id_equipo    INT,
    CONSTRAINT fk_jugador_equipo FOREIGN KEY (id_equipo) REFERENCES equipo(id_equipo)
);

-- 3. Entrenador (N entrenadores -> 1 equipo)
CREATE TABLE entrenador (
    id_entrenador INT AUTO_INCREMENT PRIMARY KEY,
    nombre        VARCHAR(100) NOT NULL,
    especialidad  VARCHAR(100),
    id_equipo     INT,
    CONSTRAINT fk_entrenador_equipo FOREIGN KEY (id_equipo) REFERENCES equipo(id_equipo)
);

-- 4. Partido (equipo se relaciona dos veces: local y visitante)
CREATE TABLE partido (
    id_partido    INT AUTO_INCREMENT PRIMARY KEY,
    fecha         DATE,
    estadio       VARCHAR(100),
    equipo_local  INT NOT NULL,
    equipo_visita INT NOT NULL,
    goles_local   INT DEFAULT 0,
    goles_visita  INT DEFAULT 0,
    CONSTRAINT fk_partido_local  FOREIGN KEY (equipo_local)  REFERENCES equipo(id_equipo),
    CONSTRAINT fk_partido_visita FOREIGN KEY (equipo_visita) REFERENCES equipo(id_equipo)
);

-- 5. Estadisticas Jugador (tabla intermedia jugador <-> partido)
CREATE TABLE estadistica_jugador (
    id_estadistica    INT AUTO_INCREMENT PRIMARY KEY,
    id_jugador        INT NOT NULL,
    id_partido        INT NOT NULL,
    minutos_jugados   INT DEFAULT 0,
    goles             INT DEFAULT 0,
    asistencias       INT DEFAULT 0,
    tarjetas_amarillas INT DEFAULT 0,
    tarjetas_rojas    INT DEFAULT 0,
    CONSTRAINT fk_estadistica_jugador FOREIGN KEY (id_jugador) REFERENCES jugador(id_jugador),
    CONSTRAINT fk_estadistica_partido FOREIGN KEY (id_partido) REFERENCES partido(id_partido)
);

-- =====================================================
-- Datos de prueba
-- =====================================================
INSERT INTO equipo (nombre, ciudad, fundacion) VALUES
('Junior FC',            'Barranquilla', '1924-08-07'),
('Atletico Nacional',    'Medellin',     '1947-03-07'),
('Millonarios FC',       'Bogota',       '1946-06-18'),
('America de Cali',      'Cali',         '1927-02-13');

INSERT INTO jugador (nombre, posicion, dorsal, fecha_nac, nacionalidad, id_equipo) VALUES
('Carlos Bacca',      'Delantero',     70, '1986-09-08', 'Colombiana', 1),
('Jose Enamorado',    'Extremo',       11, '1999-01-24', 'Colombiana', 1),
('Santiago Mele',     'Portero',        1, '1997-09-06', 'Uruguaya',   1),
('Dorlan Pabon',      'Delantero',      9, '1988-01-24', 'Colombiana', 2),
('Edwin Cardona',     'Mediocampista', 10, '1992-12-08', 'Colombiana', 2),
('Leonardo Castro',   'Delantero',     23, '1992-06-14', 'Colombiana', 3),
('Daniel Cataño',     'Mediocampista', 10, '1992-06-17', 'Colombiana', 3),
('Adrian Ramos',      'Delantero',     20, '1986-01-22', 'Colombiana', 4);

INSERT INTO entrenador (nombre, especialidad, id_equipo) VALUES
('Cesar Farias',     'Principal',       1),
('Arturo Reyes',     'Asistente',       1),
('Efrain Juarez',    'Principal',       2),
('David Gonzalez',   'Principal',       3),
('Jorge Da Silva',   'Principal',       4);

INSERT INTO partido (fecha, estadio, equipo_local, equipo_visita, goles_local, goles_visita) VALUES
('2026-07-15', 'Estadio Metropolitano',     1, 2, 3, 1),
('2026-07-22', 'Estadio Atanasio Girardot', 2, 3, 2, 2),
('2026-07-29', 'Estadio El Campin',         3, 1, 1, 4),
('2026-08-05', 'Estadio Pascual Guerrero',  4, 1, 0, 5),
('2026-08-12', 'Estadio Metropolitano',     1, 3, 6, 2),
('2026-08-19', 'Estadio Atanasio Girardot', 2, 4, 3, 0),
('2026-08-26', 'Estadio Pascual Guerrero',  4, 2, 1, 4),
('2026-09-02', 'Estadio Atanasio Girardot', 2, 1, 3, 1);

-- Estadisticas (cuadran con el marcador de cada partido)
-- Bacca suma 13 goles y Pabon 11 -> sirven para probar la consulta de "mas de X goles"
INSERT INTO estadistica_jugador (id_jugador, id_partido, minutos_jugados, goles, asistencias, tarjetas_amarillas, tarjetas_rojas) VALUES
(1, 1, 90, 2, 1, 0, 0),
(2, 1, 85, 1, 1, 1, 0),
(4, 1, 90, 1, 0, 0, 0),
(4, 2, 90, 2, 0, 1, 0),
(5, 2, 90, 0, 2, 0, 0),
(6, 2, 90, 2, 0, 0, 0),
(1, 3, 90, 3, 0, 0, 0),
(2, 3, 90, 1, 2, 0, 0),
(6, 3, 90, 1, 0, 1, 0),
(1, 4, 90, 3, 1, 0, 0),
(2, 4, 70, 2, 1, 0, 0),
(1, 5, 90, 4, 0, 1, 0),
(2, 5, 90, 2, 3, 0, 0),
(6, 5, 90, 1, 0, 0, 0),
(7, 5, 90, 1, 1, 0, 1),
(4, 6, 90, 3, 0, 0, 0),
(4, 7, 90, 3, 1, 0, 0),
(5, 7, 90, 1, 2, 1, 0),
(8, 7, 90, 1, 0, 0, 0),
(4, 8, 90, 2, 0, 0, 0),
(5, 8, 80, 1, 1, 0, 0),
(1, 8, 90, 1, 0, 1, 0);
