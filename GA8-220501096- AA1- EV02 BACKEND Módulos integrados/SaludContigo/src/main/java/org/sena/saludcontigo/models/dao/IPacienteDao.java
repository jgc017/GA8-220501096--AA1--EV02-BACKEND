package org.sena.saludcontigo.models.dao;

import org.sena.saludcontigo.models.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

public interface IPacienteDao extends JpaRepository<Paciente, Long> {
    @Query("select p from Paciente p where p.numeroDocumento = ?1")
    Paciente findByDocument(String document);

    @Modifying
    @Query("DELETE FROM Paciente p WHERE p.numeroDocumento = ?1")
    void deleteByDocument(String document);
}
