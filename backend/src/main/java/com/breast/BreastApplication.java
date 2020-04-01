package com.breast;

import com.breast.controller.MyController;
import com.breast.services.DatabaseAccessInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.breast.model.UsuarioModel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BreastApplication {

    /*
    @Autowired
    DatabaseAccessInterface user;
*/
    public static void main(String[] args) {
        SpringApplication.run(BreastApplication.class, args);
    }

    @Bean
    CommandLineRunner init(MyController myController){
        //Creo unos usuarios para ver si se insertan en bdd
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId("12");
        usuario.setName("Manolita Dominguez");

        myController.insertUser(usuario);
        //myController.getUserById("12");
        myController.findAll();
        return null;
    }



}
