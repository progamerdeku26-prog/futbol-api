package com.sena.futbol.dto;

import jakarta.validation.constraints.*;

public record EstadisticaRequest(
        @NotNull(message = "El idJugador es obligatorio") Integer idJugador,
        @NotNull(message = "El idPartido es obligatorio") Integer idPartido,
        @Min(0) @Max(130) Integer minutosJugados,
        @Min(0) Integer goles,
        @Min(0) Integer asistencias,
        @Min(0) @Max(2) Integer tarjetasAmarillas,
        @Min(0) @Max(1) Integer tarjetasRojas
) {}
