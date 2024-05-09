package org.sena.saludcontigo.models.services;

import org.sena.saludcontigo.models.dao.IExamenMedicoDao;
import org.sena.saludcontigo.models.entity.ExamenMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamenMedicoServiceImpl implements IExamenMedicoService {

    @Autowired
    private IExamenMedicoDao examenMedicoDao;

    @Override
    @Transactional(readOnly = true)
    public List<ExamenMedico> findAll() {
        return examenMedicoDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ExamenMedico findById(Long id) {
        return examenMedicoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamenMedico> findByMedicoDocumento(String documento) {
        return examenMedicoDao.findByMedicoDocumento(documento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamenMedico> findByPacienteDocumento(String documento) {
        return examenMedicoDao.findByPacienteDocumento(documento);
    }

    @Override
    @Transactional
    public ExamenMedico save(ExamenMedico examenMedico) {
        return examenMedicoDao.save(examenMedico);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        examenMedicoDao.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByPacienteDocumento(String documento) {
        examenMedicoDao.deleteByPacienteDocumento(documento);
    }
}
