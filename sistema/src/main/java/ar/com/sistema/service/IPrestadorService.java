package ar.com.sistema.service;

import ar.com.sistema.dto.PrestadorDTO;
import ar.com.sistema.exceptions.ConflictoException;
import ar.com.sistema.exceptions.NoEncontradoException;

import java.util.List;

public interface IPrestadorService {

    PrestadorDTO guardar (PrestadorDTO prestadorDTO) throws ConflictoException;
    PrestadorDTO buscar(Long id) throws NoEncontradoException;
    PrestadorDTO buscarPorMatricula(String matricula) throws NoEncontradoException;
    List<PrestadorDTO> listarTodos();
    void eliminar(Long id) throws NoEncontradoException;
    PrestadorDTO actualizar (PrestadorDTO prestadorDTO) throws NoEncontradoException, ConflictoException;

}
