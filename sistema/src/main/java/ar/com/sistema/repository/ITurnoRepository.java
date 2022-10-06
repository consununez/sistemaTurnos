package ar.com.sistema.repository;

import ar.com.sistema.entity.Prestador;
import ar.com.sistema.entity.Turno;
import ar.com.sistema.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ITurnoRepository extends JpaRepository<Turno, Long> {

    Optional<Turno> findByPrestadorAndFechaHora(Prestador prestador, LocalDateTime fechaHora);

    Optional<Turno> findByUsuarioAndFechaHora(Usuario usuario, LocalDateTime fechaHora);

}
