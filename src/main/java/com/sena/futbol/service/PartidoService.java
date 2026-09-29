package com.sena.futbol.service;

import com.sena.futbol.dto.PartidoRequest;
import com.sena.futbol.exception.ResourceNotFoundException;
import com.sena.futbol.model.Partido;
import com.sena.futbol.repository.PartidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartidoService {

    private final PartidoRepository partidoRepository;
    private final EquipoService equipoService;

    public PartidoService(PartidoRepository partidoRepository, EquipoService equipoService) {
        this.partidoRepository = partidoRepository;
        this.equipoService = equipoService;
    }

    public List<Partido> listar() {
        return partidoRepository.findAll();
    }

    public Partido buscarPorId(Integer id) {
        return partidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partido", id));
    }

    public Partido crear(PartidoRequest req) {
        Partido partido = new Partido();
        copiarDatos(req, partido);
        return partidoRepository.save(partido);
    }

    public Partido actualizar(Integer id, PartidoRequest req) {
        Partido partido = buscarPorId(id);
        copiarDatos(req, partido);
        return partidoRepository.save(partido);
    }

    public void eliminar(Integer id) {
        Partido partido = buscarPorId(id);
        partidoRepository.delete(partido);
    }

    private void copiarDatos(PartidoRequest req, Partido partido) {
        // Regla de negocio: un equipo no puede jugar contra si mismo
        if (req.equipoLocal().equals(req.equipoVisita())) {
            throw new IllegalArgumentException("El equipo local y el visitante no pueden ser el mismo");
        }
        partido.setFecha(req.fecha());
        partido.setEstadio(req.estadio());
        partido.setEquipoLocal(equipoService.buscarPorId(req.equipoLocal()));
        partido.setEquipoVisita(equipoService.buscarPorId(req.equipoVisita()));
        partido.setGolesLocal(req.golesLocal() == null ? 0 : req.golesLocal());
        partido.setGolesVisita(req.golesVisita() == null ? 0 : req.golesVisita());
    }
}
