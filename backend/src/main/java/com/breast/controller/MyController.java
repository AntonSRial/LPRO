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

    @PostMapping("/users")
    public void insertUser(UsuarioModel user) {
        usuarioDAO.insertUser(user);
    }

    /*@PostMapping("/users")
    public void modifyUser(UsuarioModel user) {
            usuarioDAO.modifyUser(user);
    }*/

    /*@PostMapping("/users")
    public void eliminateUser(String id) {
            usuarioDAO.eliminateUser(id);
    }*/

    /*@GetMapping("/users")
    public void getUserById(String id) {
        usuarioDAO.getUserById(id);
    }*/
/*
    @GetMapping("/users")
    public List<UsuarioModel> findAll() {
        return (List<UsuarioModel>) usuarioDAO.findAll();
    }

    @GetMapping("/results")
    public List<ResultadosModel> obtenerResultados(String id) {
        return (List<ResultadosModel>) resultadosDAO.obtenerResultados(id);
    }

    @GetMapping("/results")
    public List<ResultadosModel> obtenerResultadosGeneral() {
        return (List<ResultadosModel>) resultadosDAO.obtenerResultadosGeneral;
    }

    @PostMapping("/results")
    public void eliminateResults(String id, String fecha_hora) {
         resultadosDAO.eliminateResults(id);
    }

    @PostMapping("/results")
    public void eliminateAllResults(String id) {
        resultadosDAO.eliminateAllResults;
    }

    @PostMapping("/results")
    public insertResult(ResultadosModel result) {
        resultadosDAO.insertResult(result);
    }
*/


}
