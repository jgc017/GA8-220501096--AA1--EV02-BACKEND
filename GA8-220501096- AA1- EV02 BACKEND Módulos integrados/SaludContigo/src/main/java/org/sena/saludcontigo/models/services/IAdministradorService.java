package org.sena.saludcontigo.models.services;

import org.sena.saludcontigo.models.entity.Administrador;
import java.util.List;

public interface IAdministradorService {

    public List<Administrador> findAll();

    public Administrador findById(Long id);

    public Administrador findByDocument(String document);

    public Administrador save(Administrador administrador);

    public void delete(Long id);

    public void deleteByDocument(String document);
}
