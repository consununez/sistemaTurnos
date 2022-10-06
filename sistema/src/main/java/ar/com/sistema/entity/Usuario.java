package ar.com.sistema.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@Entity
@Table
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuenciaDeUsuario")
    @SequenceGenerator(name = "secuenciaDeUsuario", sequenceName = "USUARIO_SEQUENCE", allocationSize = 1)
    private Long id;

    @NotBlank
    private String apellido;

    @NotBlank
    private String nombre;

    @NotBlank
    @Column(unique = true)
    private String dni;

    @NotBlank
    private LocalDate fechaIngreso;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "domicilio_id")
    private Domicilio domicilio;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Turno> turnos;


    public Usuario() {
    }

    public Usuario(String apellido, String nombre, String dni, LocalDate fechaIngreso, Domicilio domicilio) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.dni = dni;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
    }
}
