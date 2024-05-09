package org.sena.saludcontigo.controllers;

import org.sena.saludcontigo.models.entity.CitaMedica;
import org.sena.saludcontigo.models.services.ICitaMedicaService;
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
public class CitaMedicaRestController {

    @Autowired
    private ICitaMedicaService citaMedicaService;

    /**
     * Obtiene todas las citas médicas
     * @return Lista de citas médicas
     */
    @GetMapping("/citas")
    @ResponseStatus(HttpStatus.OK)
    public List<CitaMedica> index() {
        return citaMedicaService.findAll();
    }

    /**
     * Busca una cita médica por su ID
     * @param id ID de la cita médica a buscar
     * @return Respuesta HTTP con la cita médica encontrada
     */
    @GetMapping("/citas/id/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        CitaMedica citaMedica = null;
        Map<String, Object> response = new HashMap<>();

        try {
            citaMedica = citaMedicaService.findById(id);
        } catch (DataAccessException e) {
            response.put("Message", "Error al lanzar la consulta en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (citaMedica == null) {
            response.put("Message", "La cita médica ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<CitaMedica>(citaMedica, HttpStatus.OK);
    }

    /**
     * Crea una nueva cita médica
     * @param citaMedica Objeto de la cita médica a crear
     * @param result Resultados de la validación de los datos de la cita médica
     * @return Respuesta HTTP con el resultado de la creación de la cita médica
     */
    @PostMapping("/citas")
    public ResponseEntity<?> create(@Valid @RequestBody CitaMedica citaMedica, BindingResult result) {
        CitaMedica newCitaMedica = null;
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
            newCitaMedica = citaMedicaService.save(citaMedica);
        } catch (DataAccessException e) {
            response.put("Message", "Error al realizar el insert en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Message", "La cita médica ha sido creada con éxito!!");
        response.put("CitaMedica", newCitaMedica);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    /**
     * Actualiza una cita médica existente
     * @param citaMedica Objeto de la cita médica a actualizar
     * @param result Resultados de la validación de los datos de la cita médica
     * @param id ID de la cita médica a actualizar
     * @return Respuesta HTTP con el resultado de la actualización de la cita médica
     */
    @PutMapping("/citas/id/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody CitaMedica citaMedica, BindingResult result, @PathVariable Long id) {
        CitaMedica currentCitaMedica = citaMedicaService.findById(id);
        CitaMedica updatedCitaMedica = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("Errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (currentCitaMedica == null) {
            response.put("Message", "Error: no se pudo actualizar, la cita médica ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            currentCitaMedica.setTipoCita(citaMedica.getTipoCita());
            currentCitaMedica.setFechaCita(citaMedica.getFechaCita());
            currentCitaMedica.setHoraCita(citaMedica.getHoraCita());
            currentCitaMedica.setLugarCita(citaMedica.getLugarCita());
            currentCitaMedica.setObservacionCita(citaMedica.getObservacionCita());
            currentCitaMedica.setEstadoCita(citaMedica.getEstadoCita());
            // Actualizar otros campos aquí según sea necesario

            updatedCitaMedica = citaMedicaService.save(currentCitaMedica);

        } catch (DataAccessException e) {
            response.put("Message", "Error al actualizar la cita médica en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Message", "La cita médica ha sido actualizada con éxito!!");
        response.put("CitaMedica", updatedCitaMedica);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    /**
     * Elimina una cita médica por su ID
     * @param id ID de la cita médica a eliminar
     * @return Respuesta HTTP con el resultado de la eliminación de la cita médica
     */
    @DeleteMapping("/citas/id/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            citaMedicaService.delete(id);
        } catch (DataAccessException e) {
            response.put("Message", "Error al eliminar la cita médica en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("Message", "La cita médica ha sido eliminada con éxito!!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }


    /*
     * METODOS PERSONALIZADOS
     */

    /**
     * Obtiene todas las citas médicas asociadas a un documento de médico específico.
     * @param doc Documento del médico para buscar citas médicas asociadas.
     * @return Respuesta HTTP con las citas médicas encontradas o un mensaje de error si no se encuentra ninguna.
     */
    @GetMapping("/citas/medico/doc/{doc}")
    public ResponseEntity<?> findCitasByMedicoDocument(@PathVariable String doc) {
        List<CitaMedica> citasMedicas;
        Map<String, Object> response = new HashMap<>();

        try {
            citasMedicas = citaMedicaService.findByMedicoDocumento(doc);
        } catch (DataAccessException e) {
            response.put("Message", "Error al realizar la consulta en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (citasMedicas.isEmpty()) {
            response.put("Message", "No se encontraron citas médicas para el médico con documento: " + doc);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(citasMedicas, HttpStatus.OK);
    }

    /**
     * Obtiene todas las citas médicas asociadas a un documento de paciente específico.
     * @param doc Documento del paciente para buscar citas médicas asociadas.
     * @return Respuesta HTTP con las citas médicas encontradas o un mensaje de error si no se encuentra ninguna.
     */
    @GetMapping("/citas/paciente/doc/{doc}")
    public ResponseEntity<?> findByDocument(@PathVariable String doc) {
        List<CitaMedica> citasMedicas;
        Map<String, Object> response = new HashMap<>();

        try {
            citasMedicas = citaMedicaService.findByPacienteDocumento(doc);
        } catch (DataAccessException e) {
            response.put("Message", "Error al realizar la consulta en la Base de Datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (citasMedicas.isEmpty()) {
            response.put("Message", "No se encontraron citas médicas para el documento proporcionado: " + doc);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(citasMedicas, HttpStatus.OK);
    }

    /**
     * Actualiza citas médicas por documento de paciente.
     * @param citaMedica Datos de la cita médica actualizados.
     * @param result Resultados de la validación de los datos de la cita médica.
     * @param doc Documento del paciente asociado a las citas médicas.
     * @return Respuesta HTTP con el resultado de la actualización.
     */
    @PutMapping("/citas/paciente/doc/{doc}")
    public ResponseEntity<?> updateByDocument(@Valid @RequestBody CitaMedica citaMedica, BindingResult result, @PathVariable String doc) {
        List<CitaMedica> citas = citaMedicaService.findByPacienteDocumento(doc);
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("Errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (citas.isEmpty()) {
            response.put("Message", "No se encontraron citas médicas para el documento proporcionado.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        citas.forEach(cita -> {
            cita.setTipoCita(citaMedica.getTipoCita());
            cita.setFechaCita(citaMedica.getFechaCita());
            cita.setHoraCita(citaMedica.getHoraCita());
            cita.setLugarCita(citaMedica.getLugarCita());
            cita.setEstadoCita(citaMedica.getEstadoCita());
            cita.setObservacionCita(citaMedica.getObservacionCita());

            citaMedicaService.save(cita);
        });

        response.put("Message", "Citas médicas actualizadas con éxito.");
        response.put("Citas Medicas", citas);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Elimina todas las citas médicas asociadas a un documento de paciente.
     * @param doc Documento del paciente.
     * @return Respuesta HTTP con el resultado de la eliminación.
     */
    @DeleteMapping("/citas/paciente/doc/{doc}")
    public ResponseEntity<?> deleteByDocument(@PathVariable String doc) {
        Map<String, Object> response = new HashMap<>();
        try {
            citaMedicaService.deleteByPacienteDocumento(doc);
        } catch (DataAccessException e) {
            response.put("Message", "Error al eliminar las citas médicas en la base de datos.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("Message", "Citas médicas eliminadas con éxito.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}