package com.sena.futbol.controller;

import com.sena.futbol.dto.EstadisticaRequest;
import com.sena.futbol.model.EstadisticaJugador;
import com.sena.futbol.service.EstadisticaJugadorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticaJugadorController {

    private final EstadisticaJugadorService estadisticaService;

    public EstadisticaJugadorController(EstadisticaJugadorService estadisticaService) {
        this.estadisticaService = estadisticaService;
    }

    // GET /api/estadisticas -> listar todos
    @GetMapping
    public List<EstadisticaJugador> listar() {
        return estadisticaService.listar();
    }

    // GET /api/estadisticas/{id} -> buscar uno
    @GetMapping("/{id}")
    public EstadisticaJugador buscar(@PathVariable Integer id) {
        return estadisticaService.buscarPorId(id);
    }

    // POST /api/estadisticas -> crear (responde 201 Created)
    @PostMapping
    public ResponseEntity<EstadisticaJugador> crear(@Valid @RequestBody EstadisticaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estadisticaService.crear(request));
    }

    // PUT /api/estadisticas/{id} -> actualizar
    @PutMapping("/{id}")
    public EstadisticaJugador actualizar(@PathVariable Integer id, @Valid @RequestBody EstadisticaRequest request) {
        return estadisticaService.actualizar(id, request);
    }

    // DELETE /api/estadisticas/{id} -> eliminar (responde 204 No Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        estadisticaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
