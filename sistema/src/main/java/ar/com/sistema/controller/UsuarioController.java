package ar.com.sistema.controller;

import ar.com.sistema.dto.UsuarioDTO;
import ar.com.sistema.exceptions.ConflictoException;
import ar.com.sistema.exceptions.DatosIncorrectosException;
import ar.com.sistema.exceptions.NoEncontradoException;
import ar.com.sistema.service.IUsuarioService;
import ar.com.sistema.util.Jsons;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {


    @Autowired
    private IUsuarioService usuarioService;

    @Operation(summary = "Buscar un usuario por su Id")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscar(@PathVariable Long id) throws NoEncontradoException {

        return new ResponseEntity<>(usuarioService.buscar(id), HttpStatus.OK);

    }

    @Operation(summary = "Listar todos los usuarios")
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos(){

        return new ResponseEntity<>(usuarioService.listarTodos(), HttpStatus.OK);

    }

    @Operation(summary = "Dar de alta un usuario")
    @PostMapping
    public ResponseEntity<UsuarioDTO> guardar(@RequestBody UsuarioDTO usuarioDTO) throws ConflictoException, DatosIncorrectosException {

        return new ResponseEntity<>(usuarioService.guardar(usuarioDTO), HttpStatus.OK);

    }

    @Operation(summary = "Eliminar el usuario con el Id indicado")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) throws NoEncontradoException {

        usuarioService.eliminar(id);
        String mensaje = "El usuario con ID " + id + " se eliminó correctamente.";
        String mensajeJSON = Jsons.asJsonString(mensaje);
        return new ResponseEntity<>(mensajeJSON, HttpStatus.OK);

    }

    @Operation(summary = "Actualizar los datos de un usuario")
    @PutMapping
    public ResponseEntity<UsuarioDTO> actualizar(@RequestBody UsuarioDTO usuarioDTO) throws NoEncontradoException, ConflictoException {

        return new ResponseEntity<>(usuarioService.actualizar(usuarioDTO), HttpStatus.OK);

    }

}
