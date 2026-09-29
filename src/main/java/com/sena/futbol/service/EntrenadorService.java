package com.sena.futbol.service;

import com.sena.futbol.dto.EntrenadorRequest;
import com.sena.futbol.exception.ResourceNotFoundException;
import com.sena.futbol.model.Entrenador;
import com.sena.futbol.repository.EntrenadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntrenadorService {

    private final EntrenadorRepository entrenadorRepository;
    private final EquipoService equipoService;

    public EntrenadorService(EntrenadorRepository entrenadorRepository, EquipoService equipoService) {
        this.entrenadorRepository = entrenadorRepository;
        this.equipoService = equipoService;
    }

    public List<Entrenador> listar() {
        return entrenadorRepository.findAll();
    }

    public Entrenador buscarPorId(Integer id) {
        return entrenadorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entrenador", id));
    }

    public Entrenador crear(EntrenadorRequest req) {
        Entrenador entrenador = new Entrenador();
        copiarDatos(req, entrenador);
        return entrenadorRepository.save(entrenador);
    }

    public Entrenador actualizar(Integer id, EntrenadorRequest req) {
        Entrenador entrenador = buscarPorId(id);
        copiarDatos(req, entrenador);
        return entrenadorRepository.save(entrenador);
    }

    public void eliminar(Integer id) {
        Entrenador entrenador = buscarPorId(id);
        entrenadorRepository.delete(entrenador);
    }

    private void copiarDatos(EntrenadorRequest req, Entrenador entrenador) {
        entrenador.setNombre(req.nombre());
        entrenador.setEspecialidad(req.especialidad());
        entrenador.setEquipo(equipoService.buscarPorId(req.idEquipo()));
    }
}
