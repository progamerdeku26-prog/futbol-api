package com.sena.futbol.service;

import com.sena.futbol.dto.EstadisticaRequest;
import com.sena.futbol.exception.ResourceNotFoundException;
import com.sena.futbol.model.EstadisticaJugador;
import com.sena.futbol.model.Jugador;
import com.sena.futbol.model.Partido;
import com.sena.futbol.repository.EstadisticaJugadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadisticaJugadorService {

    private final EstadisticaJugadorRepository estadisticaRepository;
    private final JugadorService jugadorService;
    private final PartidoService partidoService;

    public EstadisticaJugadorService(EstadisticaJugadorRepository estadisticaRepository,
                                     JugadorService jugadorService,
                                     PartidoService partidoService) {
        this.estadisticaRepository = estadisticaRepository;
        this.jugadorService = jugadorService;
        this.partidoService = partidoService;
    }

    public List<EstadisticaJugador> listar() {
        return estadisticaRepository.findAll();
    }

    public EstadisticaJugador buscarPorId(Integer id) {
        return estadisticaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estadistica", id));
    }

    public EstadisticaJugador crear(EstadisticaRequest req) {
        EstadisticaJugador est = new EstadisticaJugador();
        copiarDatos(req, est);
        return estadisticaRepository.save(est);
    }

    public EstadisticaJugador actualizar(Integer id, EstadisticaRequest req) {
        EstadisticaJugador est = buscarPorId(id);
        copiarDatos(req, est);
        return estadisticaRepository.save(est);
    }

    public void eliminar(Integer id) {
        EstadisticaJugador est = buscarPorId(id);
        estadisticaRepository.delete(est);
    }

    private void copiarDatos(EstadisticaRequest req, EstadisticaJugador est) {
        Jugador jugador = jugadorService.buscarPorId(req.idJugador());
        Partido partido = partidoService.buscarPorId(req.idPartido());

        // Regla de negocio: el jugador debe pertenecer a uno de los dos equipos del partido
        Integer idEquipoJugador = jugador.getEquipo() == null ? null : jugador.getEquipo().getIdEquipo();
        if (idEquipoJugador == null
                || (!idEquipoJugador.equals(partido.getEquipoLocal().getIdEquipo())
                && !idEquipoJugador.equals(partido.getEquipoVisita().getIdEquipo()))) {
            throw new IllegalArgumentException("El jugador no pertenece a ninguno de los equipos de ese partido");
        }

        est.setJugador(jugador);
        est.setPartido(partido);
        est.setMinutosJugados(valor(req.minutosJugados()));
        est.setGoles(valor(req.goles()));
        est.setAsistencias(valor(req.asistencias()));
        est.setTarjetasAmarillas(valor(req.tarjetasAmarillas()));
        est.setTarjetasRojas(valor(req.tarjetasRojas()));
    }

    private int valor(Integer n) {
        return n == null ? 0 : n;
    }
}
