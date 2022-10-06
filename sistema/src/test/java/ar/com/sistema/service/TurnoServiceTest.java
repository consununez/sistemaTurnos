package ar.com.sistema.service;

import ar.com.sistema.dto.PrestadorDTO;
import ar.com.sistema.dto.TurnoDTO;
import ar.com.sistema.dto.UsuarioDTO;
import ar.com.sistema.entity.Domicilio;
import ar.com.sistema.entity.Prestador;
import ar.com.sistema.entity.Usuario;
import ar.com.sistema.exceptions.ConflictoException;
import ar.com.sistema.exceptions.DatosIncorrectosException;
import ar.com.sistema.exceptions.NoEncontradoException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TurnoServiceTest {


    @Autowired
    ITurnoService turnoService;

    @Autowired
    IUsuarioService usuarioService;

    @Autowired
    IPrestadorService prestadorService;

    private final LocalDateTime fechaTurno =
            LocalDateTime
                    .now()
                    .withHour(15)
                    .withMinute(30)
                    .withSecond(0)
                    .withNano(0)
                    .plusMonths(1);


    public PrestadorDTO crearNuevoPrestador(){
        Random rand = new Random();
        PrestadorDTO prestador = new PrestadorDTO();
        prestador.setNombre("Prestador");
        prestador.setApellido("de Prueba");
        prestador.setMatricula(String.valueOf(rand.nextInt(9000)+1000));

        return prestador;
    }

    public UsuarioDTO crearUsuarioNuevo(){
        Random rand = new Random();

        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Don Bosco");
        domicilio.setNumero("5544");
        domicilio.setLocalidad("CABA");
        domicilio.setProvincia("CABA");

        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setNombre("Usuario");
        usuario.setApellido("de Prueba");
        usuario.setDni(String.valueOf(rand.nextInt(31000000)+2000000));
        usuario.setFechaIngreso(LocalDate.of(2002,5,5));
        usuario.setDomicilio(domicilio);

        return usuario;
    }

    public TurnoDTO crearNuevoTurno() throws ConflictoException, DatosIncorrectosException {

        TurnoDTO turno = new TurnoDTO();

        UsuarioDTO usuarioDTO = usuarioService.guardar(crearUsuarioNuevo());
        Usuario usuario = new Usuario();
        usuario.setId(usuarioDTO.getId());

        PrestadorDTO prestadorDTO = prestadorService.guardar(crearNuevoPrestador());
        Prestador prestador = new Prestador();
        prestador.setId(prestadorDTO.getId());

        turno.setUsuario(usuario);
        turno.setPrestador(prestador);

        turno.setFechaHora(fechaTurno);

        return turno;
    }

    @Test
    void turnoGuardarTest() throws ConflictoException, DatosIncorrectosException, NoEncontradoException {

        TurnoDTO turno = crearNuevoTurno();
        turno.setFechaHora(fechaTurno.plusDays(5L));

        TurnoDTO turnoGuardado = turnoService.guardar(turno);

        Long id = turnoGuardado.getId();

        TurnoDTO turnoEncontrado = turnoService.buscar(id);

        assertEquals(fechaTurno.plusDays(5L), turnoEncontrado.getFechaHora());
    }

    @Test
    void turnoBuscarTest() throws ConflictoException, DatosIncorrectosException, NoEncontradoException {

        TurnoDTO turno = crearNuevoTurno();
        turno.setFechaHora(fechaTurno.plusDays(10L));

        TurnoDTO turnoGuardado = turnoService.guardar(turno);

        TurnoDTO turnoEncontrado = turnoService.buscar(turnoGuardado.getId());

        assertEquals(fechaTurno.plusDays(10L), turnoEncontrado.getFechaHora());
    }

    @Test
    void turnoListarTodosTest() throws ConflictoException, DatosIncorrectosException, NoEncontradoException {
        TurnoDTO turno = crearNuevoTurno();
        turno.setFechaHora(fechaTurno.plusDays(15L));
        turnoService.guardar(turno);

        TurnoDTO turno2 = crearNuevoTurno();
        turno2.setFechaHora(fechaTurno.plusDays(20L));
        turnoService.guardar(turno2);

        TurnoDTO turno3 = crearNuevoTurno();
        turno3.setFechaHora(fechaTurno.plusDays(25L));
        turnoService.guardar(turno3);


        List<TurnoDTO> turnos = turnoService.listarTodos();

        assertTrue(turnos.size() >= 3);

    }

    @Test
    void turnoEliminarTest() throws ConflictoException, DatosIncorrectosException, NoEncontradoException {
        TurnoDTO turno = crearNuevoTurno();
        turno.setFechaHora(fechaTurno.plusDays(30L));

        TurnoDTO turnoGuardado = turnoService.guardar(turno);

        Long id = turnoGuardado.getId();

        TurnoDTO turnoEncontrado = turnoService.buscar(id);

        assertEquals(fechaTurno.plusDays(30L), turnoEncontrado.getFechaHora());

        turnoService.eliminar(id);


        NoEncontradoException thrown = assertThrows(NoEncontradoException.class, () -> {
            turnoService.buscar(id);
        });

        assertEquals("Turno con Id " + id + " no encontrado.", thrown.getMessage());

    }

    @Test
    void turnoActualizarTest() throws ConflictoException, DatosIncorrectosException, NoEncontradoException {

        TurnoDTO turno = crearNuevoTurno();
        turno.setFechaHora(fechaTurno.plusDays(35L));

        TurnoDTO turnoGuardado = turnoService.guardar(turno);

        Long id = turnoGuardado.getId();

        assertEquals(fechaTurno.plusDays(35L), turnoGuardado.getFechaHora());

        TurnoDTO turnoActualizar = turnoGuardado;

        PrestadorDTO prestadorDTO = prestadorService.guardar(crearNuevoPrestador());

        Long idPrestadorNuevo = prestadorDTO.getId();
        turnoActualizar.getPrestador().setId(idPrestadorNuevo);
        turnoActualizar.setFechaHora(fechaTurno.plusDays(40L));


        turnoService.actualizar(turnoActualizar);

        TurnoDTO turnoEncontrado = turnoService.buscar(id);

        assertEquals(fechaTurno.plusDays(40L), turnoEncontrado.getFechaHora());
        assertEquals(idPrestadorNuevo,turnoEncontrado.getPrestador().getId());

    }

}
