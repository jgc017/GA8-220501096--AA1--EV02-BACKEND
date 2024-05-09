package org.sena.saludcontigo.models.dao;

import org.sena.saludcontigo.models.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

public interface IMedicoDao extends JpaRepository<Medico, Long> {
    @Query("select m from Medico m where m.numeroDocumento = ?1")
    Medico findByDocument(String document);

    @Modifying
    @Query("DELETE FROM Medico m WHERE m.numeroDocumento = ?1")
    void deleteByDocument(String document);
}
