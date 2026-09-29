package com.sena.futbol.service;

import com.sena.futbol.dto.JugadorRequest;
import com.sena.futbol.exception.ResourceNotFoundException;
import com.sena.futbol.model.Jugador;
import com.sena.futbol.repository.JugadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JugadorService {

    private final JugadorRepository jugadorRepository;
    private final EquipoService equipoService;

    public JugadorService(JugadorRepository jugadorRepository, EquipoService equipoService) {
        this.jugadorRepository = jugadorRepository;
        this.equipoService = equipoService;
    }

    public List<Jugador> listar() {
        return jugadorRepository.findAll();
    }

    public Jugador buscarPorId(Integer id) {
        return jugadorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jugador", id));
    }

    public Jugador crear(JugadorRequest req) {
        Jugador jugador = new Jugador();
        copiarDatos(req, jugador);
        return jugadorRepository.save(jugador);
    }

    public Jugador actualizar(Integer id, JugadorRequest req) {
        Jugador jugador = buscarPorId(id);
        copiarDatos(req, jugador);
        return jugadorRepository.save(jugador);
    }

    public void eliminar(Integer id) {
        Jugador jugador = buscarPorId(id);
        jugadorRepository.delete(jugador);
    }

    private void copiarDatos(JugadorRequest req, Jugador jugador) {
        jugador.setNombre(req.nombre());
        jugador.setPosicion(req.posicion());
        jugador.setDorsal(req.dorsal());
        jugador.setFechaNac(req.fechaNac());
        jugador.setNacionalidad(req.nacionalidad());
        // valida que el equipo exista (si no, lanza 404)
        jugador.setEquipo(equipoService.buscarPorId(req.idEquipo()));
    }
}
