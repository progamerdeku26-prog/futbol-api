package com.sena.futbol.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record JugadorRequest(
        @NotBlank(message = "El nombre es obligatorio") @Size(max = 100) String nombre,
        @Size(max = 50) String posicion,
        @Min(value = 1, message = "El dorsal debe ser mayor a 0")
        @Max(value = 99, message = "El dorsal debe ser maximo 99") Integer dorsal,
        @Past(message = "La fecha de nacimiento debe ser pasada") LocalDate fechaNac,
        @Size(max = 100) String nacionalidad,
        @NotNull(message = "El idEquipo es obligatorio") Integer idEquipo
) {}
