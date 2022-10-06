package ar.com.sistema.service.impl;

import ar.com.sistema.dto.PrestadorDTO;
import ar.com.sistema.entity.Prestador;
import ar.com.sistema.exceptions.ConflictoException;
import ar.com.sistema.exceptions.NoEncontradoException;
import ar.com.sistema.repository.IPrestadorRepository;
import ar.com.sistema.service.IPrestadorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PrestadorService implements IPrestadorService {

    private final Logger logger = Logger.getLogger(PrestadorService.class);

    @Autowired
    private IPrestadorRepository prestadorRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public PrestadorDTO guardar(PrestadorDTO prestadorDTO) throws ConflictoException {

        String matricula = prestadorDTO.getMatricula();
        Optional<Prestador> encontrado = prestadorRepository.findByMatricula(matricula);

        if (encontrado.isPresent()){
            throw new ConflictoException("Ya existe un prestador con matrícula " + matricula + ".");
        }

        Prestador prestador = mapper.convertValue(prestadorDTO, Prestador.class);

        Prestador prestadorGuardado = prestadorRepository.save(prestador);

        PrestadorDTO prestadorDTOGuardado = mapper.convertValue(prestadorGuardado,PrestadorDTO.class);

        logger.info("Se guardó el prestador: " + prestadorDTOGuardado);

        return prestadorDTOGuardado;
    }

    @Override
    public PrestadorDTO buscar(Long id) throws NoEncontradoException {

        Optional<Prestador> prestador = prestadorRepository.findById(id);

        if (prestador.isEmpty()){
            throw new NoEncontradoException("Odontólogo con Id " + id + " no encontrado.");
        }

        PrestadorDTO prestadorDTOBuscado = mapper.convertValue(prestador,PrestadorDTO.class);

        logger.info("Se buscó por Id el prestador: " + prestadorDTOBuscado);

        return prestadorDTOBuscado;
    }

    @Override
    public PrestadorDTO buscarPorMatricula(String matricula) throws NoEncontradoException {

        Optional<Prestador> prestador = prestadorRepository.findByMatricula(matricula);

        if (prestador.isEmpty()){
            throw new NoEncontradoException("Odontólogo con Matricula " + matricula + " no encontrado.");
        }

        PrestadorDTO prestadorDTOBuscado = mapper.convertValue(prestador,PrestadorDTO.class);

        logger.info("Se buscó por Matrícula el prestador: " + prestadorDTOBuscado);

        return prestadorDTOBuscado;
    }


    @Override
    public List<PrestadorDTO> listarTodos() {

        List<Prestador> prestadors = prestadorRepository.findAll();
        List<PrestadorDTO> prestadorsDTO = new ArrayList<>();

        for (Prestador prestador : prestadors){
            prestadorsDTO.add(mapper.convertValue(prestador, PrestadorDTO.class));
        }

        logger.info("Se listaron todos los prestadors.");

        return prestadorsDTO;
    }

    @Override
    public void eliminar(Long id) throws NoEncontradoException {

        Optional<Prestador> prestador = prestadorRepository.findById(id);

        if (prestador.isEmpty()){
            throw new NoEncontradoException("No se puede eliminar porque no existe el Odontólogo con Id " + id + ",");
        }

        PrestadorDTO prestadorDTOAEliminar = mapper.convertValue(prestador,PrestadorDTO.class);

        prestadorRepository.deleteById(id);

        logger.info("Se eliminó el prestador: " + prestadorDTOAEliminar);

    }

    @Override
    public PrestadorDTO actualizar(PrestadorDTO prestadorDTO) throws NoEncontradoException, ConflictoException {

        Long id = prestadorDTO.getId();

        Optional<Prestador> encontrado = prestadorRepository.findById(id);

        if (encontrado.isEmpty()){
            throw new NoEncontradoException("No se puede actualizar porque no existe un prestador con Id: " + id + ".");
        }

        String matricula = prestadorDTO.getMatricula();
        Optional<Prestador> encontradoMat = prestadorRepository.findByMatricula(matricula);

        if (encontradoMat.isPresent() && !encontradoMat.get().getId().equals(id) ){
            throw new ConflictoException("Ya existe otro prestador con la matrícula " + matricula + ".");
        }

        PrestadorDTO prestadorDTOParaActualizar = mapper.convertValue(encontrado, PrestadorDTO.class);

        logger.info("Se actualizará un prestador. Datos originales: " + prestadorDTOParaActualizar);

        Prestador prestador = mapper.convertValue(prestadorDTO, Prestador.class);

        Prestador prestadorRes = prestadorRepository.save(prestador);

        PrestadorDTO prestadorDTOActualizado = mapper.convertValue(prestadorRes,PrestadorDTO.class);

        logger.info("Datos actuales: " + prestadorDTOActualizado);

        return prestadorDTOActualizado;
    }

}
