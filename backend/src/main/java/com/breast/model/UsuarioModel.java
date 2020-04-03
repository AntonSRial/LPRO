package com.breast.model;

import org.springframework.stereotype.Component;

@Component
public class UsuarioModel {

    private String name;
    private String id;
    private String contrasena;
    private String correo;
    private String medicacion;
    private String dolenciaEstudio;
    private String fechaNacimiento;
    private String rol;


    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getMedicacion() {
        return medicacion;
    }

    public void setMedicacion(String medicacion) {
        this.medicacion = medicacion;
    }

    public String getDolenciaEstudio() {
        return dolenciaEstudio;
    }

    public void setDolenciaEstudio(String dolenciaEstudio) {
        this.dolenciaEstudio = dolenciaEstudio;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

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

    @java.lang.Override
    public java.lang.String toString() {
        return "UsuarioModel{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", correo='" + correo + '\'' +
                ", medicacion='" + medicacion + '\'' +
                ", dolenciaEstudio='" + dolenciaEstudio + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}
