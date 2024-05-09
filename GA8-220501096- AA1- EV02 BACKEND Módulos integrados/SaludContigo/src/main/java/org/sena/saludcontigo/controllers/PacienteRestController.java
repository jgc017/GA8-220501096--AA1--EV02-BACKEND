package org.sena.saludcontigo.controllers;

import org.sena.saludcontigo.models.entity.Paciente;
import org.sena.saludcontigo.models.services.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "*"})
@RestController
@RequestMapping("/api")
public class PacienteRestController {

    @Autowired
    private IPacienteService pacienteService;

    /**
     * Obtiene todos los pacientes
     * @return Lista de pacientes
     */
    @GetMapping("/pacientes")
    @ResponseStatus(HttpStatus.OK)
    public List<Paciente> index() {
        return pacienteService.findAll();
    }

    /**
     * Busca un paciente por su ID
     * @param id ID del paciente a buscar
     * @return Respuesta HTTP con el paciente encontrado
     */
    @GetMapping("/pacientes/id/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Paciente paciente = null;
        Map<String, Object> response = new HashMap<>();

        try {
            paciente = pacienteService.findById(id);
        } catch (DataAccessException e) {
            response.put("Message", "Error al lanzar la consulta en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (paciente == null) {
            response.put("Message", "El paciente ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Paciente>(paciente, HttpStatus.OK);
    }

    /**
     * Crea un nuevo paciente
     * @param paciente Objeto del paciente a crear
     * @param result Resultados de la validación de los datos del paciente
     * @return Respuesta HTTP con el resultado de la creación del paciente
     */
    @PostMapping("/pacientes")
    public ResponseEntity<?> create(@Valid @RequestBody Paciente paciente, BindingResult result) {
        Paciente newPaciente = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("Errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            newPaciente = pacienteService.save(paciente);
        } catch (DataAccessException e) {
            response.put("Message", "Error al realizar el insert en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Message", "El paciente ha sido creado con éxito!!");
        response.put("Paciente", newPaciente);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    /**
     * Actualiza un paciente existente
     * @param paciente Objeto del paciente con los datos actualizados
     * @param result Resultados de la validación de los datos del paciente
     * @param id ID del paciente a actualizar
     * @return Respuesta HTTP con el resultado de la actualización del paciente
     */
    @PutMapping("/pacientes/id/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Paciente paciente, BindingResult result, @PathVariable Long id) {
        Paciente currentPaciente = pacienteService.findById(id);
        Paciente updatedPaciente = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("Errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (currentPaciente == null) {
            response.put("Message", "Error: no se pudo editar, el paciente ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            currentPaciente.setNombres(paciente.getNombres());
            currentPaciente.setApellidos(paciente.getApellidos());
            currentPaciente.setTipoDocumento(paciente.getTipoDocumento());
            currentPaciente.setNumeroDocumento(paciente.getNumeroDocumento());
            currentPaciente.setFechaNacimiento(paciente.getFechaNacimiento());
            currentPaciente.setSexo(paciente.getSexo());
            currentPaciente.setTelefono(paciente.getTelefono());
            currentPaciente.setCorreo(paciente.getCorreo());
            currentPaciente.setDireccion(paciente.getDireccion());
            currentPaciente.setOcupacion(paciente.getOcupacion());
            currentPaciente.setUsername(paciente.getUsername());
            currentPaciente.setPassword(paciente.getPassword());
            currentPaciente.setEstado(paciente.getEstado());
            // Actualizar otros campos según sea necesario

            updatedPaciente = pacienteService.save(currentPaciente);

        } catch (DataAccessException e) {
            response.put("Message", "Error al actualizar el paciente en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Message", "El paciente ha sido actualizado con éxito!!");
        response.put("Paciente", updatedPaciente);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    /**
     * Elimina un paciente por su ID
     * @param id ID del paciente a eliminar
     * @return Respuesta HTTP con el resultado de la eliminación del paciente
     */
    @DeleteMapping("/pacientes/id/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            pacienteService.delete(id);
        } catch (DataAccessException e) {
            response.put("Message", "Error al eliminar el paciente en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("Message", "El paciente ha sido eliminado con éxito!!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }


    /*
     * METODOS PERSONALIZADOS
     */

    /**
     * Busca un paciente por su Documento
     * @param doc DOC del paciente a buscar
     * @return Respuesta HTTP con el paciente encontrado
     */
    @GetMapping("/pacientes/doc/{doc}")
    public ResponseEntity<?> show(@PathVariable String doc) {
        Paciente paciente = null;
        Map<String, Object> response = new HashMap<>();

        try {
            paciente = pacienteService.findByDocument(doc);
        } catch (DataAccessException e) {
            response.put("Message", "Error al lanzar la consulta en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (paciente == null) {
            response.put("Message", "El paciente Documento: ".concat(doc.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Paciente>(paciente, HttpStatus.OK);
    }

    /**
     * Actualiza un paciente existente utilizando su número de documento.
     * @param paciente Objeto del paciente con los datos actualizados.
     * @param result Resultados de la validación de los datos del paciente.
     * @param doc Número de documento del paciente a actualizar.
     * @return Respuesta HTTP con el resultado de la actualización del paciente.
     */
    @PutMapping("/pacientes/doc/{doc}")
    public ResponseEntity<?> updateByDocument(@Valid @RequestBody Paciente paciente, BindingResult result, @PathVariable String doc) {
        Paciente currentPaciente = pacienteService.findByDocument(doc);
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("Errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (currentPaciente == null) {
            response.put("Message", "Error: no se pudo editar, el paciente con documento: " + doc + " no existe en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Actualizar campos
        currentPaciente.setNombres(paciente.getNombres());
        currentPaciente.setApellidos(paciente.getApellidos());
        currentPaciente.setTipoDocumento(paciente.getTipoDocumento());
        currentPaciente.setNumeroDocumento(paciente.getNumeroDocumento());
        currentPaciente.setFechaNacimiento(paciente.getFechaNacimiento());
        currentPaciente.setSexo(paciente.getSexo());
        currentPaciente.setTelefono(paciente.getTelefono());
        currentPaciente.setCorreo(paciente.getCorreo());
        currentPaciente.setDireccion(paciente.getDireccion());
        currentPaciente.setOcupacion(paciente.getOcupacion());
        currentPaciente.setUsername(paciente.getUsername());
        currentPaciente.setPassword(paciente.getPassword());
        currentPaciente.setEstado(paciente.getEstado());
        // Actualizar otros campos según sea necesario

        try {
            pacienteService.save(currentPaciente);
        } catch (DataAccessException e) {
            response.put("Message", "Error al actualizar el paciente en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Message", "El paciente ha sido actualizado con éxito!!");
        response.put("Paciente", currentPaciente);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Elimina un paciente por su número de documento.
     * @param doc Número de documento del paciente a eliminar.
     * @return Respuesta HTTP con el resultado de la eliminación del paciente.
     */
    @DeleteMapping("/pacientes/doc/{doc}")
    public ResponseEntity<?> deleteByDocument(@PathVariable String doc) {
        Map<String, Object> response = new HashMap<>();
        try {
            pacienteService.deleteByDocument(doc);
        } catch (DataAccessException e) {
            response.put("Message", "Error al eliminar el paciente en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("Message", "El paciente ha sido eliminado con éxito!!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}