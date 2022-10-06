package ar.com.sistema.dto;

import ar.com.sistema.entity.Prestador;
import ar.com.sistema.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnoDTO {

    private Long id;
    private Usuario usuario;
    private Prestador prestador;
    private LocalDateTime fechaHora;

}
