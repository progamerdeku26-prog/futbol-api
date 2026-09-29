package com.sena.futbol.controller;

import com.sena.futbol.dto.EquipoRequest;
import com.sena.futbol.model.Equipo;
import com.sena.futbol.service.EquipoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    // GET /api/equipos -> listar todos
    @GetMapping
    public List<Equipo> listar() {
        return equipoService.listar();
    }

    // GET /api/equipos/{id} -> buscar uno
    @GetMapping("/{id}")
    public Equipo buscar(@PathVariable Integer id) {
        return equipoService.buscarPorId(id);
    }

    // POST /api/equipos -> crear (responde 201 Created)
    @PostMapping
    public ResponseEntity<Equipo> crear(@Valid @RequestBody EquipoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(equipoService.crear(request));
    }

    // PUT /api/equipos/{id} -> actualizar
    @PutMapping("/{id}")
    public Equipo actualizar(@PathVariable Integer id, @Valid @RequestBody EquipoRequest request) {
        return equipoService.actualizar(id, request);
    }

    // DELETE /api/equipos/{id} -> eliminar (responde 204 No Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        equipoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
