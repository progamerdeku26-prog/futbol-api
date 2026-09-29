package com.sena.futbol.dto;

/** Resultado de la consulta nativa d) */
public interface ResultadoPartidoDTO {
    Integer getIdPartido();
    String getFecha();
    String getEstadio();
    String getEquipoLocal();
    Integer getGolesLocal();
    Integer getGolesVisita();
    String getEquipoVisita();
    String getResultado();
}
