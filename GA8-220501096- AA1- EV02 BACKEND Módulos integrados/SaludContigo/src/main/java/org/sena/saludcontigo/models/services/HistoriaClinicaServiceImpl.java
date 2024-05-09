package org.sena.saludcontigo.models.services;

import org.sena.saludcontigo.models.dao.IHistoriaClinicaDao;
import org.sena.saludcontigo.models.entity.HistoriaClinica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HistoriaClinicaServiceImpl implements IHistoriaClinicaService {

    @Autowired
    private IHistoriaClinicaDao historiaClinicaDao;

    @Override
    @Transactional(readOnly = true)
    public List<HistoriaClinica> findAll() {
        return historiaClinicaDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public HistoriaClinica findById(Long id) {
        return historiaClinicaDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoriaClinica> findByMedicoDocumento(String documento) {
        return historiaClinicaDao.findByMedicoDocumento(documento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoriaClinica> findByPacienteDocumento(String documento) {
        return historiaClinicaDao.findByPacienteDocumento(documento);
    }

    @Override
    @Transactional
    public HistoriaClinica save(HistoriaClinica historiaClinica) {
        return historiaClinicaDao.save(historiaClinica);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        historiaClinicaDao.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByPacienteDocumento(String documento) {
        historiaClinicaDao.deleteByPacienteDocumento(documento);
    }
}
