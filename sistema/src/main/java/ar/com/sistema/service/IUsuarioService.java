package ar.com.sistema.service;

import ar.com.sistema.dto.UsuarioDTO;
import ar.com.sistema.exceptions.ConflictoException;
import ar.com.sistema.exceptions.DatosIncorrectosException;
import ar.com.sistema.exceptions.NoEncontradoException;

import java.util.List;

public interface IUsuarioService {

    UsuarioDTO guardar (UsuarioDTO usuarioDTO) throws ConflictoException, DatosIncorrectosException;
    UsuarioDTO buscar(Long id) throws NoEncontradoException;
    UsuarioDTO buscarPorDni(String dni) throws NoEncontradoException;
    List<UsuarioDTO> listarTodos();
    void eliminar(Long id) throws NoEncontradoException;
    UsuarioDTO actualizar (UsuarioDTO usuarioDTO) throws NoEncontradoException, ConflictoException;

}
