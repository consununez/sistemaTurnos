package ar.com.sistema.controller;

import ar.com.sistema.dto.TurnoDTO;
import ar.com.sistema.exceptions.ConflictoException;
import ar.com.sistema.exceptions.DatosIncorrectosException;
import ar.com.sistema.exceptions.NoEncontradoException;
import ar.com.sistema.service.ITurnoService;
import ar.com.sistema.util.Jsons;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("turnos")
public class TurnoController {

    @Autowired
    private ITurnoService turnoService;

    @Operation(summary = "Buscar un turno por su Id")
    @GetMapping("/{id}")
    public ResponseEntity<TurnoDTO> buscar(@PathVariable Long id) throws NoEncontradoException {

        return new ResponseEntity<>(turnoService.buscar(id), HttpStatus.OK);

    }

    @Operation(summary = "Listar todos los turno")
    @GetMapping
    public ResponseEntity<List<TurnoDTO>> listarTodos(){

        return new ResponseEntity<>(turnoService.listarTodos(), HttpStatus.OK);

    }

    @Operation(summary = "Dar de alta un turno")
    @PostMapping
    public ResponseEntity<TurnoDTO> guardar(@RequestBody TurnoDTO turnoDTO) throws NoEncontradoException, DatosIncorrectosException, ConflictoException {

        return new ResponseEntity<>(turnoService.guardar(turnoDTO), HttpStatus.OK);

    }

    @Operation(summary = "Eliminar el turno con el Id indicado")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) throws NoEncontradoException {

        turnoService.eliminar(id);
        String mensaje = "El turno con ID " + id + " se eliminó correctamente.";
        String mensajeJSON = Jsons.asJsonString(mensaje);
        return new ResponseEntity<>(mensajeJSON, HttpStatus.OK);

    }

    @Operation(summary = "Actualizar los datos de un turno")
    @PutMapping
    public ResponseEntity<TurnoDTO> actualizar (@RequestBody TurnoDTO turnoDTO) throws NoEncontradoException, DatosIncorrectosException, ConflictoException {

        return new ResponseEntity<>(turnoService.actualizar(turnoDTO),HttpStatus.OK);

    }

}
