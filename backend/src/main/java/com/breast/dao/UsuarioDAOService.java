package com.breast.dao;

import java.util.List;

import com.breast.model.UsuarioModel;

public interface UsuarioDAOService {

    void insertUser(UsuarioModel user);
    UsuarioModel getUserById(String id);
    List<UsuarioModel> findAll();
    void eliminateUser(String id);
    void modifyUSer(UsuarioModel user);
}
