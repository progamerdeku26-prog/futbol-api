package com.sena.futbol.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record PartidoRequest(
        @NotNull(message = "La fecha es obligatoria") LocalDate fecha,
        @Size(max = 100) String estadio,
        @NotNull(message = "El equipoLocal es obligatorio") Integer equipoLocal,
        @NotNull(message = "El equipoVisita es obligatorio") Integer equipoVisita,
        @Min(value = 0, message = "Los goles no pueden ser negativos") Integer golesLocal,
        @Min(value = 0, message = "Los goles no pueden ser negativos") Integer golesVisita
) {}
