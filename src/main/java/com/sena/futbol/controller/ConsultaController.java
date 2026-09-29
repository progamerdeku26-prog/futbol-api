package com.sena.futbol.controller;

import com.sena.futbol.dto.GolesEquipoDTO;
import com.sena.futbol.dto.JugadorGolesDTO;
import com.sena.futbol.dto.ResultadoPartidoDTO;
import com.sena.futbol.model.Jugador;
import com.sena.futbol.service.ConsultaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Expone las 4 consultas nativas del plan de mejoramiento.
 */
@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    // a) GET /api/consultas/equipos/1/jugadores  (parametro por PATH)
    @GetMapping("/equipos/{idEquipo}/jugadores")
    public List<Jugador> jugadoresPorEquipo(@PathVariable Integer idEquipo) {
        return consultaService.jugadoresPorEquipo(idEquipo);
    }

    // b) GET /api/consultas/jugadores/goleadores?minGoles=10  (parametro por QUERY)
    @GetMapping("/jugadores/goleadores")
    public List<JugadorGolesDTO> goleadores(@RequestParam(defaultValue = "10") Integer minGoles) {
        return consultaService.jugadoresConMasDeXGoles(minGoles);
    }

    // c) GET /api/consultas/equipos/1/total-goles
    @GetMapping("/equipos/{idEquipo}/total-goles")
    public GolesEquipoDTO totalGoles(@PathVariable Integer idEquipo) {
        return consultaService.totalGolesPorEquipo(idEquipo);
    }

    // d) GET /api/consultas/partidos/resultados
    @GetMapping("/partidos/resultados")
    public List<ResultadoPartidoDTO> resultados() {
        return consultaService.resultadosPartidos();
    }
}
