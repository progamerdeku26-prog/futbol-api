package com.sena.futbol.controller;

import com.sena.futbol.dto.PartidoRequest;
import com.sena.futbol.model.Partido;
import com.sena.futbol.service.PartidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partidos")
public class PartidoController {

    private final PartidoService partidoService;

    public PartidoController(PartidoService partidoService) {
        this.partidoService = partidoService;
    }

    // GET /api/partidos -> listar todos
    @GetMapping
    public List<Partido> listar() {
        return partidoService.listar();
    }

    // GET /api/partidos/{id} -> buscar uno
    @GetMapping("/{id}")
    public Partido buscar(@PathVariable Integer id) {
        return partidoService.buscarPorId(id);
    }

    // POST /api/partidos -> crear (responde 201 Created)
    @PostMapping
    public ResponseEntity<Partido> crear(@Valid @RequestBody PartidoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(partidoService.crear(request));
    }

    // PUT /api/partidos/{id} -> actualizar
    @PutMapping("/{id}")
    public Partido actualizar(@PathVariable Integer id, @Valid @RequestBody PartidoRequest request) {
        return partidoService.actualizar(id, request);
    }

    // DELETE /api/partidos/{id} -> eliminar (responde 204 No Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        partidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
