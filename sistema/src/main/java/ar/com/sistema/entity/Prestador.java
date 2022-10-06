package ar.com.sistema.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter @Setter
@Entity
@Table
public class Prestador {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuenciaDePrestador")
    @SequenceGenerator(name = "secuenciaDePrestador", sequenceName = "PRESTADOR_SEQUENCE", allocationSize = 1)
    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @NotBlank
    @Column(unique = true)
    private String matricula;

    @OneToMany(mappedBy = "prestador", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Turno> turnos;

    public Prestador() {
    }

    public Prestador(String nombre, String apellido, String matricula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
    }

}
