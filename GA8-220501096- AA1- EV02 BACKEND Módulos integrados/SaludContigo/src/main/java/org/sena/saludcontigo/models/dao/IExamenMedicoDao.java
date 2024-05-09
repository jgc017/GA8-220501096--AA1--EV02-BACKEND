package org.sena.saludcontigo.models.dao;

import jakarta.transaction.Transactional;
import org.sena.saludcontigo.models.entity.ExamenMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import java.util.List;

public interface IExamenMedicoDao extends JpaRepository<ExamenMedico, Long> {
    // Consulta de Exámenes Médicos por número de documento del médico
    @Query("SELECT em FROM ExamenMedico em JOIN em.medico m WHERE m.numeroDocumento = ?1")
    List<ExamenMedico> findByMedicoDocumento(String documentoMedico);

    // Consulta de Exámenes Médicos por número de documento del paciente
    @Query("SELECT em FROM ExamenMedico em JOIN em.paciente p WHERE p.numeroDocumento = ?1")
    List<ExamenMedico> findByPacienteDocumento(String documentoPaciente);

    // Eliminación de Exámenes Médicos por número de documento del paciente
    @Modifying
    @Transactional
    @Query("DELETE FROM ExamenMedico em WHERE em.paciente IN (SELECT p FROM Paciente p WHERE p.numeroDocumento = ?1)")
    void deleteByPacienteDocumento(String documentoPaciente);
}
