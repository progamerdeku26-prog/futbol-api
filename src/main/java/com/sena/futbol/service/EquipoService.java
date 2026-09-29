package com.sena.futbol.service;

import com.sena.futbol.dto.EquipoRequest;
import com.sena.futbol.exception.ResourceNotFoundException;
import com.sena.futbol.model.Equipo;
import com.sena.futbol.repository.EquipoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoService {

    private final EquipoRepository equipoRepository;

    // Inyeccion de dependencias por constructor
    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    public List<Equipo> listar() {
        return equipoRepository.findAll();
    }

    public Equipo buscarPorId(Integer id) {
        return equipoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo", id));
    }

    public Equipo crear(EquipoRequest req) {
        Equipo equipo = new Equipo();
        copiarDatos(req, equipo);
        return equipoRepository.save(equipo);
    }

    public Equipo actualizar(Integer id, EquipoRequest req) {
        Equipo equipo = buscarPorId(id);
        copiarDatos(req, equipo);
        return equipoRepository.save(equipo);
    }

    public void eliminar(Integer id) {
        Equipo equipo = buscarPorId(id);
        equipoRepository.delete(equipo);
    }

    private void copiarDatos(EquipoRequest req, Equipo equipo) {
        equipo.setNombre(req.nombre());
        equipo.setCiudad(req.ciudad());
        equipo.setFundacion(req.fundacion());
    }
}
