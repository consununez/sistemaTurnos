package ar.com.sistema.service.impl;

import ar.com.sistema.dto.UsuarioDTO;
import ar.com.sistema.entity.Usuario;
import ar.com.sistema.exceptions.ConflictoException;
import ar.com.sistema.exceptions.DatosIncorrectosException;
import ar.com.sistema.exceptions.NoEncontradoException;
import ar.com.sistema.repository.IUsuarioRepository;
import ar.com.sistema.service.IUsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {

    private final Logger logger = Logger.getLogger(UsuarioService.class);

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public UsuarioDTO guardar(UsuarioDTO usuarioDTO) throws ConflictoException, DatosIncorrectosException {

        String dni = usuarioDTO.getDni();

        Optional<Usuario> encontrado = usuarioRepository.findByDni(dni);

        if (encontrado.isPresent()){
            throw new ConflictoException("Ya existe un usuario con el DNI " + dni + ".");
        }

        if (usuarioDTO.getFechaIngreso() == null) {
            throw new DatosIncorrectosException("La fecha no es correcta.");
        }

        Usuario usuario = mapper.convertValue(usuarioDTO, Usuario.class);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        UsuarioDTO usuarioDTOGuardado = mapper.convertValue(usuarioGuardado, UsuarioDTO.class);

        logger.info("Se guardó el usuario: " + usuarioDTOGuardado);

        return usuarioDTOGuardado;

    }

    public UsuarioDTO buscar(Long id) throws NoEncontradoException {

        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isEmpty()){
            throw new NoEncontradoException("Usuario con Id " + id + " no encontrado.");
        }

        UsuarioDTO usuarioDTOBuscado = mapper.convertValue(usuario,UsuarioDTO.class);

        logger.info("Se buscó por Id el usuario: " + usuarioDTOBuscado);

        return usuarioDTOBuscado;
    }

    @Override
    public UsuarioDTO buscarPorDni(String dni) throws NoEncontradoException {

        Optional<Usuario> usuario = usuarioRepository.findByDni(dni);

        if (usuario.isEmpty()){
            throw new NoEncontradoException("Usuario con DNI " + dni + " no encontrado.");
        }

        UsuarioDTO usuarioDTOBuscado = mapper.convertValue(usuario,UsuarioDTO.class);

        logger.info("Se buscó por DNI el usuario: " + usuarioDTOBuscado);

        return usuarioDTOBuscado;
    }

    @Override
    public List<UsuarioDTO> listarTodos() {

        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();

        for (Usuario usuario : usuarios){
            usuariosDTO.add(mapper.convertValue(usuario, UsuarioDTO.class));
        }

        logger.info("Se listaron todos los usuarios.");

        return usuariosDTO;
    }

    @Override
    public void eliminar(Long id) throws NoEncontradoException {

        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isEmpty()){
            throw new NoEncontradoException("No se puede eliminar porque no existe el Usuario con Id " + id + ".");
        }

        UsuarioDTO usuarioDTOAEliminar = mapper.convertValue(usuario,UsuarioDTO.class);

        usuarioRepository.deleteById(id);

        logger.info("Se eliminó el usuario: " + usuarioDTOAEliminar);

    }

    @Override
    public UsuarioDTO actualizar(UsuarioDTO usuarioDTO) throws NoEncontradoException, ConflictoException {

        Long id = usuarioDTO.getId();

        Optional<Usuario> encontrado = usuarioRepository.findById(id);

        if (encontrado.isEmpty()){
            throw new NoEncontradoException("No se puede actualizar porque no existe un usuario con Id: " + id + ".");
        }

        String dni = usuarioDTO.getDni();
        Optional<Usuario> encontradoDni = usuarioRepository.findByDni(dni);

        if (encontradoDni.isPresent() && !encontradoDni.get().getId().equals(id) ){
            throw new ConflictoException("Ya existe otro usuario con el DNI " + dni + ".");
        }


        UsuarioDTO usuarioDTOParaActualizar = mapper.convertValue(encontrado,UsuarioDTO.class);

        logger.info("Se actualizará un usuario. Datos originales: " + usuarioDTOParaActualizar);

        Usuario usuario = mapper.convertValue(usuarioDTO, Usuario.class);

        Usuario usuarioRes = usuarioRepository.save(usuario);

        UsuarioDTO usuarioDTOActualizado = mapper.convertValue(usuarioRes,UsuarioDTO.class);

        logger.info("Datos actuales: " + usuarioDTOActualizado);


        return usuarioDTOActualizado;
    }

}
