package ar.com.sistema.service.impl;

import ar.com.sistema.dto.PrestadorDTO;
import ar.com.sistema.dto.TurnoDTO;
import ar.com.sistema.dto.UsuarioDTO;
import ar.com.sistema.entity.Prestador;
import ar.com.sistema.entity.Turno;
import ar.com.sistema.entity.Usuario;
import ar.com.sistema.exceptions.ConflictoException;
import ar.com.sistema.exceptions.DatosIncorrectosException;
import ar.com.sistema.exceptions.NoEncontradoException;
import ar.com.sistema.repository.ITurnoRepository;
import ar.com.sistema.service.ITurnoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {


    private final Logger logger = Logger.getLogger(TurnoService.class);
    private final ITurnoRepository turnoRepository;
    private final UsuarioService usuarioService;
    private final PrestadorService prestadorService;
    private final ObjectMapper mapper;

    @Autowired
    public TurnoService(ITurnoRepository turnoRepository, UsuarioService usuarioService, PrestadorService prestadorService, ObjectMapper mapper) {
        this.turnoRepository = turnoRepository;
        this.usuarioService = usuarioService;
        this.prestadorService = prestadorService;
        this.mapper = mapper;
    }

    @Override
    public TurnoDTO guardar(TurnoDTO turnoDTO) throws ConflictoException, DatosIncorrectosException, NoEncontradoException {

        // Obtengo prestador, usuario y fecha
        LocalDateTime fechaHora = turnoDTO.getFechaHora();
        Prestador prestador = turnoDTO.getPrestador();
        Usuario usuario = turnoDTO.getUsuario();

        // verifico que la fecha exista
        if (fechaHora == null) {
            throw new DatosIncorrectosException("La fecha del turno no se ingresó.");
        }

        // verifico que la fecha no sea anterior a ahora
        if (fechaHora.isBefore(LocalDateTime.now())){
            throw new DatosIncorrectosException("La fecha del turno no puede ser anterior a este momento.");

        }

        //verifico que el usuario exista
        UsuarioDTO usuarioEncontrado = usuarioService.buscar(usuario.getId());


        //verifico que el prestador exista
        PrestadorDTO prestadorEncontrado = prestadorService.buscar(prestador.getId());


        //Verifico que el prestador no tenga un turno previo
        Optional<Turno> prestadorFechaEncontrado = turnoRepository.findByPrestadorAndFechaHora(prestador, fechaHora);

        if (prestadorFechaEncontrado.isPresent()) {
            throw new ConflictoException("El prestador con Id " + prestador.getId() + " ya tiene un turno asignado en ese horario.");
        }

        //Verifico que el usuario no tenga un turno previo
        Optional<Turno> usuarioFechaEncontrado = turnoRepository.findByUsuarioAndFechaHora(usuario, fechaHora);

        if (usuarioFechaEncontrado.isPresent()) {
            throw new ConflictoException("El usuario con Id " + usuario.getId() + " ya tiene un turno asignado en ese horario.");
        }

        //Si llego hasta acá, la solicitud es correcta. Convierto el turno a DTO y lo guardo
        Turno turno = mapper.convertValue(turnoDTO, Turno.class);

        Turno turnoRes = turnoRepository.save(turno);

        TurnoDTO turnoDTOGuardado = buscar(turnoRes.getId());

        logger.info("Se registró el siguiente turno: " + turnoDTOGuardado);

        return turnoDTOGuardado;
    }

    @Override
    public TurnoDTO buscar(Long id) throws NoEncontradoException {

        Optional<Turno> turno = turnoRepository.findById(id);

        if (turno.isEmpty()){
            throw new NoEncontradoException("Turno con Id " + id + " no encontrado.");
        }

        TurnoDTO turnoDTO = mapper.convertValue(turno, TurnoDTO.class);

        logger.info("Se buscó por Id el turno: " + turnoDTO);

        return turnoDTO;
    }

    @Override
    public List<TurnoDTO> listarTodos() {

        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoDTO> turnosDTO = new ArrayList<>();

        for (Turno turno : turnos){
            turnosDTO.add(mapper.convertValue(turno, TurnoDTO.class));
        }

        logger.info("Se listaron todos los turnos.");

        return turnosDTO;
    }

    @Override
    public void eliminar(Long id) throws NoEncontradoException {

        Optional<Turno> turno = turnoRepository.findById(id);

        if (turno.isEmpty()){
            throw new NoEncontradoException("No se puede eliminar porque no existe el Turno con Id " + id + ".");
        }

        TurnoDTO turnoDTOAEliminar = mapper.convertValue(turno,TurnoDTO.class);

        turnoRepository.deleteById(id);

        logger.info("Se  eliminó el turno: " + turnoDTOAEliminar);

    }

    @Override
    public TurnoDTO actualizar(TurnoDTO turnoDTO) throws NoEncontradoException, DatosIncorrectosException, ConflictoException {

        Optional<Turno> encontrado = turnoRepository.findById(turnoDTO.getId());

        if (encontrado.isEmpty()){
            throw new NoEncontradoException("No se puede actualizar porque no existe un turno con Id: " + turnoDTO.getId() + ".");
        }

        // Obtengo datos de usuario, prestador y fecha.
        Prestador prestador = turnoDTO.getPrestador();
        Usuario usuario = turnoDTO.getUsuario();
        LocalDateTime fechaHora = turnoDTO.getFechaHora();

        // verifico que la fecha no sea anterior a ahora
        if (fechaHora.isBefore(LocalDateTime.now())){
            throw new DatosIncorrectosException("La fecha del turno no puede ser anterior a este momento.");

        }

        //verifico que el usuario exista
        UsuarioDTO usuarioEncontrado = usuarioService.buscar(usuario.getId());


        //verifico que el prestador exista
        PrestadorDTO prestadorEncontrado = prestadorService.buscar(prestador.getId());


        //Verifico que el prestador no tenga un turno previo (distinto al que estoy modificando)
        Optional<Turno> prestadorFechaEncontrado = turnoRepository.findByPrestadorAndFechaHora(prestador, fechaHora);

        if (prestadorFechaEncontrado.isPresent()){
            if(!prestadorFechaEncontrado.get().getId().equals(turnoDTO.getId())) {
                throw new ConflictoException("El prestador con Id " + prestador.getId() + " ya tiene un turno asignado en ese horario.");
            }
        }

        //Verifico que el usuario no tenga un turno previo (distinto al que estoy modificando)
        Optional<Turno> usuarioFechaEncontrado = turnoRepository.findByUsuarioAndFechaHora(usuario, fechaHora);

        if (usuarioFechaEncontrado.isPresent()){
            if(!usuarioFechaEncontrado.get().getId().equals(turnoDTO.getId())) {
                throw new ConflictoException("El usuario con Id " + usuario.getId() + " ya tiene un turno asignado en ese horario.");
            }
        }


        TurnoDTO turnoDTOParaActualizar = mapper.convertValue(encontrado, TurnoDTO.class);

        logger.info("Se actualizará un turno. Datos originales: " + turnoDTOParaActualizar);

        Turno turno = mapper.convertValue(turnoDTO, Turno.class);

        Turno turnoRes = turnoRepository.save(turno);

        TurnoDTO turnoDTOActualizado = mapper.convertValue(turnoRes, TurnoDTO.class);

        logger.info("Datos actuales: " + turnoDTOActualizado);

        return turnoDTOActualizado;
    }
}
