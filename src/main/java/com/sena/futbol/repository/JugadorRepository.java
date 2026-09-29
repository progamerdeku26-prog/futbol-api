package com.sena.futbol.repository;

import com.sena.futbol.dto.JugadorGolesDTO;
import com.sena.futbol.model.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Integer> {

    /**
     * Consulta nativa a) Obtener todos los jugadores de un equipo especifico.
     */
    @Query(value = """
            SELECT *
            FROM jugador
            WHERE id_equipo = :idEquipo
            ORDER BY dorsal
            """, nativeQuery = true)
    List<Jugador> findJugadoresPorEquipo(@Param("idEquipo") Integer idEquipo);

    /**
     * Consulta nativa b) Jugadores que han marcado MAS de X goles
     * (sumando sus goles en todos los partidos).
     * Se agrupa por jugador y se filtra con HAVING porque la condicion es sobre la SUMA.
     */
    @Query(value = """
            SELECT j.id_jugador AS idJugador,
                   j.nombre     AS nombre,
                   e.nombre     AS equipo,
                   CAST(SUM(ej.goles) AS SIGNED) AS totalGoles
            FROM jugador j
            INNER JOIN estadistica_jugador ej ON ej.id_jugador = j.id_jugador
            LEFT JOIN equipo e ON e.id_equipo = j.id_equipo
            GROUP BY j.id_jugador, j.nombre, e.nombre
            HAVING SUM(ej.goles) > :minGoles
            ORDER BY totalGoles DESC
            """, nativeQuery = true)
    List<JugadorGolesDTO> findJugadoresConMasDeXGoles(@Param("minGoles") Integer minGoles);
}
