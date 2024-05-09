package org.sena.saludcontigo.controllers;

import org.sena.saludcontigo.models.entity.HistoriaClinica;
import org.sena.saludcontigo.models.services.IHistoriaClinicaService;
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
public class HistoriaClinicaRestController {

    @Autowired
    private IHistoriaClinicaService historiaClinicaService;

    /**
     * Obtiene todas las historias clínicas
     * @return Lista de historias clínicas
     */
    @GetMapping("/historias")
    @ResponseStatus(HttpStatus.OK)
    public List<HistoriaClinica> index() {
        return historiaClinicaService.findAll();
    }

    /**
     * Busca una historia clínica por su ID
     * @param id ID de la historia clínica a buscar
     * @return Respuesta HTTP con la historia clínica encontrada
     */
    @GetMapping("/historias/id/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        HistoriaClinica historiaClinica = null;
        Map<String, Object> response = new HashMap<>();

        try {
            historiaClinica = historiaClinicaService.findById(id);
        } catch (DataAccessException e) {
            response.put("Message", "Error al lanzar la consulta en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (historiaClinica == null) {
            response.put("Message", "La historia clínica ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<HistoriaClinica>(historiaClinica, HttpStatus.OK);
    }

    /**
     * Crea una nueva historia clínica
     * @param historiaClinica Objeto de la historia clínica a crear
     * @param result Resultados de la validación de los datos de la historia clínica
     * @return Respuesta HTTP con el resultado de la creación de la historia clínica
     */
    @PostMapping("/historias")
    public ResponseEntity<?> create(@Valid @RequestBody HistoriaClinica historiaClinica, BindingResult result) {
        HistoriaClinica newHistoriaClinica = null;
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
            newHistoriaClinica = historiaClinicaService.save(historiaClinica);
        } catch (DataAccessException e) {
            response.put("Message", "Error al realizar el insert en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Message", "La historia clínica ha sido creada con éxito!!");
        response.put("HistoriaClinica", newHistoriaClinica);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    /**
     * Actualiza una historia clínica existente
     * @param historiaClinica Objeto de la historia clínica a actualizar
     * @param result Resultados de la validación de los datos de la historia clínica
     * @param id ID de la historia clínica a actualizar
     * @return Respuesta HTTP con el resultado de la actualización de la historia clínica
     */
    @PutMapping("/historias/id/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody HistoriaClinica historiaClinica, BindingResult result, @PathVariable Long id) {
        HistoriaClinica currentHistoriaClinica = historiaClinicaService.findById(id);
        HistoriaClinica updatedHistoriaClinica = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("Errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (currentHistoriaClinica == null) {
            response.put("Message", "Error: no se pudo actualizar, la historia clínica ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            currentHistoriaClinica.setFechaHistoria(historiaClinica.getFechaHistoria());
            currentHistoriaClinica.setAntecedentesHistoria(historiaClinica.getAntecedentesHistoria());
            currentHistoriaClinica.setExamenesHistoria(historiaClinica.getExamenesHistoria());
            currentHistoriaClinica.setDiagnosticoHistoria(historiaClinica.getDiagnosticoHistoria());
            currentHistoriaClinica.setTratamientoHistoria(historiaClinica.getTratamientoHistoria());
            // Actualizar otros campos aquí según sea necesario

            updatedHistoriaClinica = historiaClinicaService.save(currentHistoriaClinica);

        } catch (DataAccessException e) {
            response.put("Message", "Error al actualizar la historia clínica en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Message", "La historia clínica ha sido actualizada con éxito!!");
        response.put("HistoriaClinica", updatedHistoriaClinica);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    /**
     * Elimina una historia clínica por su ID
     * @param id ID de la historia clínica a eliminar
     * @return Respuesta HTTP con el resultado de la eliminación de la historia clínica
     */
    @DeleteMapping("/historias/id/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            historiaClinicaService.delete(id);
        } catch (DataAccessException e) {
            response.put("Message", "Error al eliminar la historia clínica en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("Message", "La historia clínica ha sido eliminada con éxito!!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }


    /*
     * METODOS PERSONALIZADOS
     */

    /**
     * Obtiene todas las historias clínicas asociadas a un documento de médico específico.
     * @param doc Documento del médico para buscar historias clínicas asociadas.
     * @return Respuesta HTTP con las historias clínicas encontradas o un mensaje de error si no se encuentra ninguna.
     */
    @GetMapping("/historias/medico/doc/{doc}")
    public ResponseEntity<?> findHistoriasByMedicoDocument(@PathVariable String doc) {
        List<HistoriaClinica> historiasClinicas;
        Map<String, Object> response = new HashMap<>();

        try {
            historiasClinicas = historiaClinicaService.findByMedicoDocumento(doc);
        } catch (DataAccessException e) {
            response.put("Message", "Error al realizar la consulta en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (historiasClinicas.isEmpty()) {
            response.put("Message", "No se encontraron historias clínicas para el médico con documento: " + doc);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(historiasClinicas, HttpStatus.OK);
    }

    /**
     * Obtiene todas las historias clínicas asociadas a un documento de paciente específico.
     * @param doc Documento del paciente para buscar historias clínicas asociadas.
     * @return Respuesta HTTP con las historias clínicas encontradas o un mensaje de error si no se encuentra ninguna.
     */
    @GetMapping("/historias/paciente/doc/{doc}")
    public ResponseEntity<?> findHistoriasByPacienteDocument(@PathVariable String doc) {
        List<HistoriaClinica> historiasClinicas;
        Map<String, Object> response = new HashMap<>();

        try {
            historiasClinicas = historiaClinicaService.findByPacienteDocumento(doc);
        } catch (DataAccessException e) {
            response.put("Message", "Error al realizar la consulta en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (historiasClinicas.isEmpty()) {
            response.put("Message", "No se encontraron historias clínicas para el paciente con documento: " + doc);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(historiasClinicas, HttpStatus.OK);
    }

    /**
     * Actualiza historias clínicas por documento de paciente.
     * @param historiaClinica Datos de la historia clínica actualizados.
     * @param result Resultados de la validación de los datos de la historia clínica.
     * @param doc Documento del paciente asociado a las historias clínicas.
     * @return Respuesta HTTP con el resultado de la actualización.
     */
    @PutMapping("/historias/paciente/doc/{doc}")
    public ResponseEntity<?> updateByDocument(@Valid @RequestBody HistoriaClinica historiaClinica, BindingResult result, @PathVariable String doc) {
        List<HistoriaClinica> historias = historiaClinicaService.findByPacienteDocumento(doc);
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("Errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (historias.isEmpty()) {
            response.put("Message", "No se encontraron historias clínicas para el documento proporcionado.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        historias.forEach(historia -> {
            historia.setFechaHistoria(historiaClinica.getFechaHistoria());
            historia.setAntecedentesHistoria(historiaClinica.getAntecedentesHistoria());
            historia.setExamenesHistoria(historiaClinica.getExamenesHistoria());
            historia.setDiagnosticoHistoria(historiaClinica.getDiagnosticoHistoria());
            historia.setTratamientoHistoria(historiaClinica.getTratamientoHistoria());

            historiaClinicaService.save(historia);
        });

        response.put("Message", "Historias clínicas actualizadas con éxito.");
        response.put("Historias Clinicas", historias);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Elimina todas las historias clínicas asociadas a un documento de paciente.
     * @param doc Documento del paciente.
     * @return Respuesta HTTP con el resultado de la eliminación.
     */
    @DeleteMapping("/historias/paciente/doc/{doc}")
    public ResponseEntity<?> deleteHistoriasByPacienteDocument(@PathVariable String doc) {
        Map<String, Object> response = new HashMap<>();
        try {
            historiaClinicaService.deleteByPacienteDocumento(doc);
        } catch (DataAccessException e) {
            response.put("Message", "Error al eliminar las historias clínicas en la base de datos.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("Message", "Historias clínicas eliminadas con éxito.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}