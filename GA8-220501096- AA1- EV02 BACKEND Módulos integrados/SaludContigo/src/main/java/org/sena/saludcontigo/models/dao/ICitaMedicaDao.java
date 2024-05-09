package org.sena.saludcontigo.models.dao;

import jakarta.transaction.Transactional;
import org.sena.saludcontigo.models.entity.CitaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import java.util.List;

public interface ICitaMedicaDao extends JpaRepository<CitaMedica, Long> {
    // Consulta de Citas Médicas por número de documento del médico
    @Query("SELECT cm FROM CitaMedica cm JOIN cm.medico m WHERE m.numeroDocumento = ?1")
    List<CitaMedica> findByMedicoDocumento(String documentoMedico);

    // Consulta de Citas Médicas por número de documento del paciente
    @Query("SELECT cm FROM CitaMedica cm JOIN cm.paciente p WHERE p.numeroDocumento = ?1")
    List<CitaMedica> findByPacienteDocumento(String documentoPaciente);

    // Eliminación de Citas Médicas por número de documento del paciente
    @Modifying
    @Transactional
    @Query("DELETE FROM CitaMedica cm WHERE cm.paciente IN (SELECT p FROM Paciente p WHERE p.numeroDocumento = ?1)")
    void deleteByPacienteDocumento(String documentoPaciente);
}
