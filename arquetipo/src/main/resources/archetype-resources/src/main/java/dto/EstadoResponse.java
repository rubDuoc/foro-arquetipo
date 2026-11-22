#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dto;

import lombok.Builder;

import java.time.LocalDateTime;

// Patron Builder: la respuesta se arma paso a paso con EstadoResponse.builder()...build()
@Builder
public record EstadoResponse(String servicio, String estado, LocalDateTime fecha) {
}
