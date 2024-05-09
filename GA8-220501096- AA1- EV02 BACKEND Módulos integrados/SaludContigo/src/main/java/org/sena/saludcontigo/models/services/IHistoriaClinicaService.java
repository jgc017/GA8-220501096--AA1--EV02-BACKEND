package org.sena.saludcontigo.models.services;

import org.sena.saludcontigo.models.entity.HistoriaClinica;
import java.util.List;

public interface IHistoriaClinicaService {

    public List<HistoriaClinica> findAll();

    public HistoriaClinica findById(Long id);

    public List<HistoriaClinica> findByMedicoDocumento(String documento);

    public List<HistoriaClinica> findByPacienteDocumento(String documento);

    public HistoriaClinica save(HistoriaClinica historiaClinica);

    public void delete(Long id);

    public void deleteByPacienteDocumento(String documento);
}
