package org.sena.saludcontigo.models.dao;

import jakarta.transaction.Transactional;
import org.sena.saludcontigo.models.entity.HistoriaClinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import java.util.List;

public interface IHistoriaClinicaDao extends JpaRepository<HistoriaClinica, Long> {
    // Consulta de Historias Clínicas por número de documento del médico
    @Query("SELECT h FROM HistoriaClinica h JOIN h.medico m WHERE m.numeroDocumento = ?1")
    List<HistoriaClinica> findByMedicoDocumento(String documentoMedico);

    // Consulta de Historias Clínicas por número de documento del paciente
    @Query("SELECT h FROM HistoriaClinica h JOIN h.paciente p WHERE p.numeroDocumento = ?1")
    List<HistoriaClinica> findByPacienteDocumento(String documentoPaciente);

    // Eliminación de Historias Clínicas por número de documento del paciente
    @Modifying
    @Transactional
    @Query("DELETE FROM HistoriaClinica h WHERE h.paciente IN (SELECT p FROM Paciente p WHERE p.numeroDocumento = ?1)")
    void deleteByPacienteDocumento(String documentoPaciente);
}
