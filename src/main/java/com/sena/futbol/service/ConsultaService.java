package com.sena.futbol.service;

import com.sena.futbol.dto.GolesEquipoDTO;
import com.sena.futbol.dto.JugadorGolesDTO;
import com.sena.futbol.dto.ResultadoPartidoDTO;
import com.sena.futbol.model.Jugador;
import com.sena.futbol.repository.JugadorRepository;
import com.sena.futbol.repository.PartidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Logica de las 4 consultas nativas del plan de mejoramiento.
 */
@Service
public class ConsultaService {

    private final JugadorRepository jugadorRepository;
    private final PartidoRepository partidoRepository;
    private final EquipoService equipoService;

    public ConsultaService(JugadorRepository jugadorRepository,
                           PartidoRepository partidoRepository,
                           EquipoService equipoService) {
        this.jugadorRepository = jugadorRepository;
        this.partidoRepository = partidoRepository;
        this.equipoService = equipoService;
    }

    // a) Jugadores de un equipo
    public List<Jugador> jugadoresPorEquipo(Integer idEquipo) {
        equipoService.buscarPorId(idEquipo); // 404 si el equipo no existe
        return jugadorRepository.findJugadoresPorEquipo(idEquipo);
    }

    // b) Jugadores con mas de X goles (X de dos cifras: 10 en adelante)
    public List<JugadorGolesDTO> jugadoresConMasDeXGoles(Integer x) {
        if (x == null || x < 10 || x > 99) {
            throw new IllegalArgumentException("X debe ser un numero de dos cifras (entre 10 y 99)");
        }
        return jugadorRepository.findJugadoresConMasDeXGoles(x);
    }

    // c) Total de goles de un equipo en todos sus partidos
    public GolesEquipoDTO totalGolesPorEquipo(Integer idEquipo) {
        equipoService.buscarPorId(idEquipo);
        return partidoRepository.findTotalGolesPorEquipo(idEquipo);
    }

    // d) Resultados de todos los partidos con nombres de equipos
    public List<ResultadoPartidoDTO> resultadosPartidos() {
        return partidoRepository.findResultadosPartidos();
    }
}
