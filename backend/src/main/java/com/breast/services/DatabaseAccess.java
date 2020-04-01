package com.breast.services;

import com.breast.model.UsuarioModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.breast.dao.UsuarioDAO;

@Service
public class DatabaseAccess implements DatabaseAccessInterface {
/*
    @Autowired
    UsuarioDAO usuarioDAO;

    @Override
    public void insertUser(UsuarioModel user) {
        usuarioDAO.insertUser(user);
    }

    @Override
    public void getUserById(String id) {
        UsuarioModel user = usuarioDAO.getUserById(id);
        System.out.println(user.toString());
    }

    @Override
    public void findAll(){
        List<UsuarioModel> users = usuarioDAO.findAll();
        //System.out.println(user.toString());
    }
*/
}