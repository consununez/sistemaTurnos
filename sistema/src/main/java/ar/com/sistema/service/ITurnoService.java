package ar.com.sistema.service;

import ar.com.sistema.dto.TurnoDTO;
import ar.com.sistema.exceptions.ConflictoException;
import ar.com.sistema.exceptions.DatosIncorrectosException;
import ar.com.sistema.exceptions.NoEncontradoException;

import java.util.List;

public interface ITurnoService {

    TurnoDTO guardar (TurnoDTO turnoDTO) throws ConflictoException, DatosIncorrectosException, NoEncontradoException;
    TurnoDTO buscar(Long id) throws NoEncontradoException;
    List<TurnoDTO> listarTodos();
    void eliminar(Long id) throws NoEncontradoException;
    TurnoDTO actualizar (TurnoDTO turnoDTO) throws NoEncontradoException, DatosIncorrectosException, ConflictoException;

}
