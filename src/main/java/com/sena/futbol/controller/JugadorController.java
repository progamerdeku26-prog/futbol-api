package com.sena.futbol.controller;

import com.sena.futbol.dto.JugadorRequest;
import com.sena.futbol.model.Jugador;
import com.sena.futbol.service.JugadorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jugadores")
public class JugadorController {

    private final JugadorService jugadorService;

    public JugadorController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    // GET /api/jugadores -> listar todos
    @GetMapping
    public List<Jugador> listar() {
        return jugadorService.listar();
    }

    // GET /api/jugadores/{id} -> buscar uno
    @GetMapping("/{id}")
    public Jugador buscar(@PathVariable Integer id) {
        return jugadorService.buscarPorId(id);
    }

    // POST /api/jugadores -> crear (responde 201 Created)
    @PostMapping
    public ResponseEntity<Jugador> crear(@Valid @RequestBody JugadorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jugadorService.crear(request));
    }

    // PUT /api/jugadores/{id} -> actualizar
    @PutMapping("/{id}")
    public Jugador actualizar(@PathVariable Integer id, @Valid @RequestBody JugadorRequest request) {
        return jugadorService.actualizar(id, request);
    }

    // DELETE /api/jugadores/{id} -> eliminar (responde 204 No Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        jugadorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
