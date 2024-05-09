package org.sena.saludcontigo.models.services;

import org.sena.saludcontigo.models.dao.ICitaMedicaDao;
import org.sena.saludcontigo.models.entity.CitaMedica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CitaMedicaServiceImpl implements ICitaMedicaService {

    @Autowired
    private ICitaMedicaDao citaMedicaDao;

    @Override
    @Transactional(readOnly = true)
    public List<CitaMedica> findAll() {
        return citaMedicaDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public CitaMedica findById(Long id) {
        return citaMedicaDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaMedica> findByMedicoDocumento(String documento) {
        return citaMedicaDao.findByMedicoDocumento(documento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaMedica> findByPacienteDocumento(String documento) {
        return citaMedicaDao.findByPacienteDocumento(documento);
    }

    @Override
    @Transactional
    public CitaMedica save(CitaMedica citaMedica) {
        return citaMedicaDao.save(citaMedica);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        citaMedicaDao.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByPacienteDocumento(String documento) {
        citaMedicaDao.deleteByPacienteDocumento(documento);
    }
}
