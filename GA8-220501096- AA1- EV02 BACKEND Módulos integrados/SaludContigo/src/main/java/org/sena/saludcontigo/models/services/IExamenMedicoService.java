package org.sena.saludcontigo.models.services;

import org.sena.saludcontigo.models.entity.ExamenMedico;
import java.util.List;

public interface IExamenMedicoService {

    public List<ExamenMedico> findAll();

    public ExamenMedico findById(Long id);

    public List<ExamenMedico> findByMedicoDocumento(String documento);

    public List<ExamenMedico> findByPacienteDocumento(String documento);

    public ExamenMedico save(ExamenMedico examenMedico);

    public void delete(Long id);

    public void deleteByPacienteDocumento(String documento);
}
