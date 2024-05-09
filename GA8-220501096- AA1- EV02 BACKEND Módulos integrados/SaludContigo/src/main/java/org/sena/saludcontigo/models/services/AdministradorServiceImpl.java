package org.sena.saludcontigo.models.services;

import org.sena.saludcontigo.models.dao.IAdministradorDao;
import org.sena.saludcontigo.models.entity.Administrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdministradorServiceImpl implements IAdministradorService {

    @Autowired
    private IAdministradorDao administradorDao;

    @Override
    @Transactional(readOnly = true)
    public List<Administrador> findAll() {
        return administradorDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Administrador findById(Long id) {
        return administradorDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Administrador findByDocument(String document){
        return administradorDao.findByDocument(document);
    }

    @Override
    @Transactional
    public Administrador save(Administrador administrador) {
                return administradorDao.save(administrador);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        administradorDao.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByDocument(String document) {
        administradorDao.deleteByDocument(document);
    }
}
