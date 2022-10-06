package ar.com.sistema.util;

import ar.com.sistema.entity.Domicilio;
import ar.com.sistema.entity.Prestador;
import ar.com.sistema.entity.Turno;
import ar.com.sistema.entity.Usuario;
import ar.com.sistema.repository.IPrestadorRepository;
import ar.com.sistema.repository.ITurnoRepository;
import ar.com.sistema.repository.IUsuarioRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataLoader implements ApplicationRunner {


    private final Logger logger = Logger.getLogger(DataLoader.class);

    private final IPrestadorRepository prestadorRepository;
    private final IUsuarioRepository usuarioRepository;
    private final ITurnoRepository turnoRepository;

    @Autowired
    public DataLoader(IPrestadorRepository prestadorRepository, IUsuarioRepository usuarioRepository, ITurnoRepository turnoRepository) {
        this.prestadorRepository = prestadorRepository;
        this.usuarioRepository = usuarioRepository;
        this.turnoRepository = turnoRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        try {

            // Genero domicilios
            Domicilio d1 = new Domicilio("Dunder", "5533", "Scranton", "Pensilvania");
            Domicilio d2 = new Domicilio("Mifflin", "9988", "Scranton", "Pensilvania");
            Domicilio d3 = new Domicilio("Bandit", "2233", "Scranton", "Pensilvania");


            // Agrego usuarios
            Usuario u1 = usuarioRepository.save(new Usuario("Scott", "Michael", "20555444", LocalDate.now(), d1));
            Usuario u2 = usuarioRepository.save(new Usuario("Malone", "Kevin", "22444666", LocalDate.now(), d2));
            Usuario u3 = usuarioRepository.save(new Usuario("Martin", "Angela", "24777888", LocalDate.now(), d3));


            // Agrego prestadors
            Prestador p1 = prestadorRepository.save(new Prestador("Dwight", "Schrute", "444666"));
            Prestador p2 = prestadorRepository.save(new Prestador("Martinez", "Oscar", "323213"));
            Prestador p3 = prestadorRepository.save(new Prestador("Toby", "Flenderson", "444477"));

            // Agrego turnos
            Turno t1 = new Turno(u1, p1, LocalDateTime.of(2022, 8, 15, 15, 30));
            turnoRepository.save(t1);

            Turno t2 = new Turno(u2, p2, LocalDateTime.of(2022, 7, 20, 14, 00));
            turnoRepository.save(t2);

            Turno t3 = new Turno(u3, p3, LocalDateTime.of(2022, 7, 19, 11, 30));
            turnoRepository.save(t3);

        }
        catch (Exception e) {
            String mensajeError = e.getMessage();

            String mensajeErrorCorrecto = mensajeError.replace('\'','`');

            logger.error(mensajeErrorCorrecto);
        }
    }

}
