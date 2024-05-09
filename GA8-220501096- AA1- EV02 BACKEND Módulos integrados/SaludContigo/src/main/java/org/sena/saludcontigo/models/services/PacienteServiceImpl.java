package org.sena.saludcontigo.models.services;

import org.sena.saludcontigo.models.dao.IPacienteDao;
import org.sena.saludcontigo.models.entity.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PacienteServiceImpl implements IPacienteService {

    @Autowired
    private IPacienteDao pacienteDao;

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> findAll() {
        return pacienteDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Paciente findById(Long id) {
        return pacienteDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Paciente findByDocument(String document){
        return pacienteDao.findByDocument(document);
    }

    @Override
    @Transactional
    public Paciente save(Paciente paciente) {
        return pacienteDao.save(paciente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        pacienteDao.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByDocument(String document) {
        pacienteDao.deleteByDocument(document);
    }
}
