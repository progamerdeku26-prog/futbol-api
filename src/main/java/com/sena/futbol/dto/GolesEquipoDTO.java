package com.sena.futbol.dto;

/** Resultado de la consulta nativa c) */
public interface GolesEquipoDTO {
    Integer getIdEquipo();
    String getEquipo();
    Long getPartidosJugados();
    Long getTotalGoles();
}
