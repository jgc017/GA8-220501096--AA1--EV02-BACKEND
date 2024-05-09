package org.sena.saludcontigo.models.services;

import org.sena.saludcontigo.models.entity.Medico;
import java.util.List;

public interface IMedicoService {

    public List<Medico> findAll();

    public Medico findById(Long id);

    public Medico findByDocument(String document);

    public Medico save(Medico medico);

    public void delete(Long id);

    public void deleteByDocument(String document);
}
