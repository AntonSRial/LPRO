package com.breast.controller;

import com.breast.dao.UsuarioDAO;
import com.breast.model.UsuarioModel;
import com.breast.dao.ResultadosDAO;
import com.breast.model.ResultadosModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MyController {

    @Autowired
    UsuarioDAO usuarioDAO;
    ResultadosDAO resultadosDAO;


    /****USERS*****/

    @PostMapping("/users")
    public void insertUser(UsuarioModel user) {
        usuarioDAO.insertUser(user);
    }

    @PutMapping("/users")
    public void modifyUser(UsuarioModel user) {
            usuarioDAO.modifyUser(user);
    }

    @PostMapping("/usersDelete")
    public void eliminateUser(String id) {
            usuarioDAO.eliminateUser(id);
    }

    @GetMapping("/users")
    public void getUserById(String id) {
        usuarioDAO.getUserById(id);
    }

    @GetMapping("/usersAll")
    public List<UsuarioModel> findAll() {
        return (List<UsuarioModel>) usuarioDAO.findAll();
    }

    /*******RESULTS******/

    @GetMapping("/results")
    public List<ResultadosModel> obtenerResultados(String id) {
        return (List<ResultadosModel>) resultadosDAO.obtenerResultados(id);
    }

    @GetMapping("/resultsGeneral")
    public List<ResultadosModel> obtenerResultadosGeneral() {
        return (List<ResultadosModel>) resultadosDAO.obtenerResultadosGeneral();
    }

    @PostMapping("/resultsDelete")
    public void eliminateResults(String id, String fecha_hora) {
         resultadosDAO.eliminateResults(id, fecha_hora);
    }

    @DeleteMapping("/results")
    public void eliminateAllResults(String id) {
        resultadosDAO.eliminateAllResults(id);
    }

    @PostMapping("/results")
    public void insertResult(ResultadosModel result) {
        resultadosDAO.insertResult(result);
    }



}
