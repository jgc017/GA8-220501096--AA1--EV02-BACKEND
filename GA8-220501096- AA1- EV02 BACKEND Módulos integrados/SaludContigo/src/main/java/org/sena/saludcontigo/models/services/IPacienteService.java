package org.sena.saludcontigo.models.services;

import org.sena.saludcontigo.models.entity.Paciente;
import java.util.List;

public interface IPacienteService {

    public List<Paciente> findAll();

    public Paciente findById(Long id);

    public Paciente findByDocument(String document);

    public Paciente save(Paciente paciente);

    public void delete(Long id);

    public void deleteByDocument(String document);
}
