package org.sena.saludcontigo.models.dao;

import org.sena.saludcontigo.models.entity.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

public interface IAdministradorDao extends JpaRepository<Administrador, Long> {
    @Query("select a from Administrador a where a.numeroDocumento = ?1")
    Administrador findByDocument(String document);

    @Modifying
    @Query("DELETE FROM Administrador a WHERE a.numeroDocumento = ?1")
    void deleteByDocument(String document);
}
