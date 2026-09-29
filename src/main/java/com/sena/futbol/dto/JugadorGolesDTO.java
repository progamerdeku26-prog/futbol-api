package com.sena.futbol.dto;

/** Resultado de la consulta nativa b) - los alias del SQL deben coincidir con los getters */
public interface JugadorGolesDTO {
    Integer getIdJugador();
    String getNombre();
    String getEquipo();
    Long getTotalGoles();
}
