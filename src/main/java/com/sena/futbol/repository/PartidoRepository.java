package com.sena.futbol.repository;

import com.sena.futbol.dto.GolesEquipoDTO;
import com.sena.futbol.dto.ResultadoPartidoDTO;
import com.sena.futbol.model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Integer> {

    /**
     * Consulta nativa c) Total de goles marcados por un equipo en todos sus partidos.
     * Un equipo puede ser local o visitante, por eso el CASE:
     *  - si fue local, se suman goles_local
     *  - si fue visitante, se suman goles_visita
     */
    @Query(value = """
            SELECT e.id_equipo AS idEquipo,
                   e.nombre    AS equipo,
                   COUNT(p.id_partido) AS partidosJugados,
                   CAST(COALESCE(SUM(
                       CASE WHEN p.equipo_local = e.id_equipo THEN p.goles_local
                            ELSE p.goles_visita
                       END), 0) AS SIGNED) AS totalGoles
            FROM equipo e
            LEFT JOIN partido p
                   ON p.equipo_local = e.id_equipo OR p.equipo_visita = e.id_equipo
            WHERE e.id_equipo = :idEquipo
            GROUP BY e.id_equipo, e.nombre
            """, nativeQuery = true)
    GolesEquipoDTO findTotalGolesPorEquipo(@Param("idEquipo") Integer idEquipo);

    /**
     * Consulta nativa d) Resultados de todos los partidos con los NOMBRES de los equipos.
     * Se hace JOIN dos veces a la tabla equipo (alias el = local, ev = visitante).
     */
    @Query(value = """
            SELECT p.id_partido AS idPartido,
                   DATE_FORMAT(p.fecha, '%Y-%m-%d') AS fecha,
                   p.estadio    AS estadio,
                   el.nombre    AS equipoLocal,
                   p.goles_local  AS golesLocal,
                   p.goles_visita AS golesVisita,
                   ev.nombre    AS equipoVisita,
                   CASE
                       WHEN p.goles_local > p.goles_visita THEN CONCAT('Gana ', el.nombre)
                       WHEN p.goles_local < p.goles_visita THEN CONCAT('Gana ', ev.nombre)
                       ELSE 'Empate'
                   END AS resultado
            FROM partido p
            INNER JOIN equipo el ON el.id_equipo = p.equipo_local
            INNER JOIN equipo ev ON ev.id_equipo = p.equipo_visita
            ORDER BY p.fecha
            """, nativeQuery = true)
    List<ResultadoPartidoDTO> findResultadosPartidos();
}
