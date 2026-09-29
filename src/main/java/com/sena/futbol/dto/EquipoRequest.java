package com.sena.futbol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record EquipoRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100) String nombre,
        @Size(max = 100) String ciudad,
        @PastOrPresent(message = "La fecha de fundacion no puede ser futura") LocalDate fundacion
) {}
