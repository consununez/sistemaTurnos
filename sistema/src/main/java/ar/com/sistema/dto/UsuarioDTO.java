package ar.com.sistema.dto;

import ar.com.sistema.entity.Domicilio;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioDTO {

    private Long id;
    private String apellido;
    private String nombre;
    private String dni;
    private LocalDate fechaIngreso;
    private Domicilio domicilio;

}
