package ar.com.sistema.repository;

import ar.com.sistema.entity.Prestador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPrestadorRepository extends JpaRepository<Prestador, Long> {

    Optional<Prestador> findByMatricula(String matricula);
}
