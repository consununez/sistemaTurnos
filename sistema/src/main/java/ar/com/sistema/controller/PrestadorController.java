package ar.com.sistema.controller;

import ar.com.sistema.dto.PrestadorDTO;
import ar.com.sistema.exceptions.ConflictoException;
import ar.com.sistema.exceptions.NoEncontradoException;
import ar.com.sistema.service.IPrestadorService;
import ar.com.sistema.util.Jsons;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("prestadores")
public class PrestadorController {

    @Autowired
    private IPrestadorService prestadorService;

    @Operation(summary = "Buscar un prestador por su Id")
    @GetMapping("/{id}")
    public ResponseEntity<PrestadorDTO> buscar(@PathVariable Long id) throws NoEncontradoException {

        return new ResponseEntity<>(prestadorService.buscar(id), HttpStatus.OK);

    }

    @Operation(summary = "Listar todos los prestadores")
    @GetMapping
    public ResponseEntity<List<PrestadorDTO>> listarTodos(){

        return new ResponseEntity<>(prestadorService.listarTodos(), HttpStatus.OK);

    }

    @Operation(summary = "Dar de alta un prestador")
    @PostMapping
    public ResponseEntity<PrestadorDTO> guardar(@RequestBody PrestadorDTO prestadorDTO) throws ConflictoException {

        return new ResponseEntity<>(prestadorService.guardar(prestadorDTO), HttpStatus.OK);

    }

    @Operation(summary = "Eliminar el prestador con el Id indicado")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) throws NoEncontradoException {

        prestadorService.eliminar(id);
        String mensaje = "El prestador con ID " + id + " se eliminó correctamente.";
        String mensajeJSON = Jsons.asJsonString(mensaje);
        return new ResponseEntity<>(mensajeJSON, HttpStatus.OK);

    }

    @Operation(summary = "Actualizar los datos de un prestador")
    @PutMapping
    public ResponseEntity<PrestadorDTO> actualizar(@RequestBody PrestadorDTO prestadorDTO) throws NoEncontradoException, ConflictoException {

        return new ResponseEntity<>(prestadorService.actualizar(prestadorDTO), HttpStatus.OK);

    }

}
