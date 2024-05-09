package org.sena.saludcontigo.models.services;

import org.sena.saludcontigo.models.entity.CitaMedica;
import java.util.List;

public interface ICitaMedicaService {

    public List<CitaMedica> findAll();

    public CitaMedica findById(Long id);

    public List<CitaMedica> findByMedicoDocumento(String documento);

    public List<CitaMedica> findByPacienteDocumento(String documento);

    public CitaMedica save(CitaMedica citaMedica);

    public void delete(Long id);

    public void deleteByPacienteDocumento(String documento);
}
