package ar.com.sistema.repository;

import ar.com.sistema.entity.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDomicilioRepository extends JpaRepository<Domicilio, Long> {
}
