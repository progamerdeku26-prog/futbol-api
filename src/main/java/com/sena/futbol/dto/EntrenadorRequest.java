package com.sena.futbol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EntrenadorRequest(
        @NotBlank(message = "El nombre es obligatorio") @Size(max = 100) String nombre,
        @Size(max = 100) String especialidad,
        @NotNull(message = "El idEquipo es obligatorio") Integer idEquipo
) {}
