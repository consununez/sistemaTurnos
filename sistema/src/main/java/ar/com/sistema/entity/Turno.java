package ar.com.sistema.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter @Setter
@Entity
@Table
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuenciaDeTurno")
    @SequenceGenerator(name = "secuenciaDeTurno", sequenceName = "TURNO_SEQUENCE", allocationSize = 1)
    private Long id;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "prestador_id", nullable = false)
    private Prestador prestador;

    @NotBlank
    private LocalDateTime fechaHora;

    public Turno() {
    }

    public Turno(Usuario usuario, Prestador prestador, LocalDateTime fechaHora) {
        this.usuario = usuario;
        this.prestador = prestador;
        this.fechaHora = fechaHora;
    }
}
