package org.sena.saludcontigo.controllers;

import org.sena.saludcontigo.models.entity.ExamenMedico;
import org.sena.saludcontigo.models.services.IExamenMedicoService;
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
public class ExamenMedicoRestController {

    @Autowired
    private IExamenMedicoService examenMedicoService;

    /**
     * Obtiene todos los exámenes médicos
     * @return Lista de exámenes médicos
     */
    @GetMapping("/examenes")
    @ResponseStatus(HttpStatus.OK)
    public List<ExamenMedico> index() {
        return examenMedicoService.findAll();
    }

    /**
     * Busca un examen médico por su ID
     * @param id ID del examen médico a buscar
     * @return Respuesta HTTP con el examen médico encontrado
     */
    @GetMapping("/examenes/id/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        ExamenMedico examenMedico = null;
        Map<String, Object> response = new HashMap<>();

        try {
            examenMedico = examenMedicoService.findById(id);
        } catch (DataAccessException e) {
            response.put("Message", "Error al lanzar la consulta en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (examenMedico == null) {
            response.put("Message", "El examen médico ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ExamenMedico>(examenMedico, HttpStatus.OK);
    }

    /**
     * Crea un nuevo examen médico
     * @param examenMedico Objeto del examen médico a crear
     * @param result Resultados de la validación de los datos del examen médico
     * @return Respuesta HTTP con el resultado de la creación del examen médico
     */
    @PostMapping("/examenes")
    public ResponseEntity<?> create(@Valid @RequestBody ExamenMedico examenMedico, BindingResult result) {
        ExamenMedico newExamenMedico = null;
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
            newExamenMedico = examenMedicoService.save(examenMedico);
        } catch (DataAccessException e) {
            response.put("Message", "Error al realizar el insert en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Message", "El examen médico ha sido creado con éxito!!");
        response.put("ExamenMedico", newExamenMedico);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    /**
     * Actualiza un examen médico existente
     * @param examenMedico Objeto del examen médico a actualizar
     * @param result Resultados de la validación de los datos del examen médico
     * @param id ID del examen médico a actualizar
     * @return Respuesta HTTP con el resultado de la actualización del examen médico
     */
    @PutMapping("/examenes/id/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody ExamenMedico examenMedico, BindingResult result, @PathVariable Long id) {
        ExamenMedico currentExamenMedico = examenMedicoService.findById(id);
        ExamenMedico updatedExamenMedico = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("Errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (currentExamenMedico == null) {
            response.put("Message", "Error: no se pudo actualizar, el examen médico ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            currentExamenMedico.setTipoExamen(examenMedico.getTipoExamen());
            currentExamenMedico.setFechaExamen(examenMedico.getFechaExamen());
            currentExamenMedico.setHoraExamen(examenMedico.getHoraExamen());
            currentExamenMedico.setLugarExamen(examenMedico.getLugarExamen());
            currentExamenMedico.setObservacionExamen(examenMedico.getObservacionExamen());
            currentExamenMedico.setEstadoExamen(examenMedico.getEstadoExamen());
            // Actualizar otros campos aquí según sea necesario

            updatedExamenMedico = examenMedicoService.save(currentExamenMedico);

        } catch (DataAccessException e) {
            response.put("Message", "Error al actualizar el examen médico en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Message", "El examen médico ha sido actualizado con éxito!!");
        response.put("ExamenMedico", updatedExamenMedico);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    /**
     * Elimina un examen médico por su ID
     * @param id ID del examen médico a eliminar
     * @return Respuesta HTTP con el resultado de la eliminación del examen médico
     */
    @DeleteMapping("/examenes/id/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            examenMedicoService.delete(id);
        } catch (DataAccessException e) {
            response.put("Message", "Error al eliminar el examen médico en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("Message", "El examen médico ha sido eliminado con éxito!!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }


    /*
     * METODOS PERSONALIZADOS
     */

    /**
     * Obtiene todos los exámenes médicos asociados al documento de un médico específico.
     * @param doc Documento del médico para buscar exámenes médicos asociados.
     * @return ResponseEntity con la lista de exámenes médicos o un mensaje de error.
     */
    @GetMapping("/examenes/medico/doc/{doc}")
    public ResponseEntity<?> findExamenesByMedicoDocument(@PathVariable String doc) {
        List<ExamenMedico> examenesMedicos;
        Map<String, Object> response = new HashMap<>();

        try {
            examenesMedicos = examenMedicoService.findByMedicoDocumento(doc);
        } catch (DataAccessException e) {
            response.put("Message", "Error al realizar la consulta en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (examenesMedicos.isEmpty()) {
            response.put("Message", "No se encontraron exámenes médicos para el médico con documento: " + doc);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(examenesMedicos, HttpStatus.OK);
    }

    /**
     * Obtiene todos los exámenes médicos asociados al documento de un paciente específico.
     * @param doc Documento del paciente para buscar exámenes médicos asociados.
     * @return ResponseEntity con la lista de exámenes médicos o un mensaje de error.
     */
    @GetMapping("/examenes/paciente/doc/{doc}")
    public ResponseEntity<?> findExamenesByPacienteDocument(@PathVariable String doc) {
        List<ExamenMedico> examenesMedicos;
        Map<String, Object> response = new HashMap<>();

        try {
            examenesMedicos = examenMedicoService.findByPacienteDocumento(doc);
        } catch (DataAccessException e) {
            response.put("Message", "Error al realizar la consulta en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (examenesMedicos.isEmpty()) {
            response.put("Message", "No se encontraron exámenes médicos para el paciente con documento: " + doc);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(examenesMedicos, HttpStatus.OK);
    }

    /**
     * Actualiza los exámenes médicos por documento de paciente.
     * @param examenMedico Datos del examen médico actualizados.
     * @param result Resultados de la validación de los datos del examen médico.
     * @param doc Documento del paciente asociado a los exámenes médicos.
     * @return Respuesta HTTP con el resultado de la actualización.
     */
    @PutMapping("/examenes/paciente/doc/{doc}")
    public ResponseEntity<?> updateByDocument(@Valid @RequestBody ExamenMedico examenMedico, BindingResult result, @PathVariable String doc) {
        List<ExamenMedico> examenes = examenMedicoService.findByPacienteDocumento(doc);
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("Errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (examenes.isEmpty()) {
            response.put("Message", "No se encontraron exámenes médicos para el documento proporcionado.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        examenes.forEach(examen -> {
            examen.setTipoExamen(examenMedico.getTipoExamen());
            examen.setFechaExamen(examenMedico.getFechaExamen());
            examen.setHoraExamen(examenMedico.getHoraExamen());
            examen.setLugarExamen(examenMedico.getLugarExamen());
            examen.setEstadoExamen(examenMedico.getEstadoExamen());
            examen.setObservacionExamen(examenMedico.getObservacionExamen());

            examenMedicoService.save(examen);
        });

        response.put("Message", "Exámenes médicos actualizados con éxito.");
        response.put("Examenes Medicos", examenes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Elimina todos los exámenes médicos asociados al documento de un paciente.
     * @param doc Documento del paciente.
     * @return Respuesta HTTP con el resultado de la eliminación.
     */
    @DeleteMapping("/examenes/paciente/doc/{doc}")
    public ResponseEntity<?> deleteExamenesByPacienteDocument(@PathVariable String doc) {
        Map<String, Object> response = new HashMap<>();
        try {
            examenMedicoService.deleteByPacienteDocumento(doc);
        } catch (DataAccessException e) {
            response.put("Message", "Error al eliminar los exámenes médicos en la base de datos.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("Message", "Exámenes médicos eliminados con éxito.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}