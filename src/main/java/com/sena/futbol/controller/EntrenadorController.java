package com.sena.futbol.controller;

import com.sena.futbol.dto.EntrenadorRequest;
import com.sena.futbol.model.Entrenador;
import com.sena.futbol.service.EntrenadorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entrenadores")
public class EntrenadorController {

    private final EntrenadorService entrenadorService;

    public EntrenadorController(EntrenadorService entrenadorService) {
        this.entrenadorService = entrenadorService;
    }

    // GET /api/entrenadores -> listar todos
    @GetMapping
    public List<Entrenador> listar() {
        return entrenadorService.listar();
    }

    // GET /api/entrenadores/{id} -> buscar uno
    @GetMapping("/{id}")
    public Entrenador buscar(@PathVariable Integer id) {
        return entrenadorService.buscarPorId(id);
    }

    // POST /api/entrenadores -> crear (responde 201 Created)
    @PostMapping
    public ResponseEntity<Entrenador> crear(@Valid @RequestBody EntrenadorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(entrenadorService.crear(request));
    }

    // PUT /api/entrenadores/{id} -> actualizar
    @PutMapping("/{id}")
    public Entrenador actualizar(@PathVariable Integer id, @Valid @RequestBody EntrenadorRequest request) {
        return entrenadorService.actualizar(id, request);
    }

    // DELETE /api/entrenadores/{id} -> eliminar (responde 204 No Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        entrenadorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
