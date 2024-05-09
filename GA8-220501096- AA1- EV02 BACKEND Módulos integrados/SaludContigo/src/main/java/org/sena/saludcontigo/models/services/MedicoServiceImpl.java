package org.sena.saludcontigo.models.services;

import org.sena.saludcontigo.models.dao.IMedicoDao;
import org.sena.saludcontigo.models.entity.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicoServiceImpl implements IMedicoService {

    @Autowired
    private IMedicoDao medicoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Medico> findAll() {
        return medicoDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Medico findById(Long id) {
        return medicoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Medico findByDocument(String document){
        return medicoDao.findByDocument(document);
    }

    @Override
    @Transactional
    public Medico save(Medico medico) {
        return medicoDao.save(medico);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        medicoDao.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByDocument(String document) {
        medicoDao.deleteByDocument(document);
    }
}
