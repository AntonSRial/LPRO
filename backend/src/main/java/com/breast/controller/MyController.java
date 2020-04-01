package com.breast.controller;

import com.breast.dao.UsuarioDAO;
import com.breast.model.UsuarioModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MyController {

    @Autowired
    UsuarioDAO usuarioDAO;

    @PostMapping("/users")
    public void insertUser(UsuarioModel user) {
        usuarioDAO.insertUser(user);
    }

    /*@GetMapping("/users")
    public void getUserById(String id) {
        UsuarioModel user = usuarioDAO.getUserById(id);
        System.out.println(user.toString());
    }*/

    @GetMapping("/users")
    public List<UsuarioModel> findAll() {
        return (List<UsuarioModel>) usuarioDAO.findAll();
    }



}
