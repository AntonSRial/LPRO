package com.breast.model;

import org.springframework.stereotype.Component;

@Component
public class UsuarioModel {

    private String name;
    private String id;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
