package com.breast.model;

import org.springframework.stereotype.Component;

@Component
public class ResultadosModel {

    private String IDPaciente;
    private String fechaHora;
    private String valorTemperatura;
    private String comentario;

    public String getIDPaciente() {
        return IDPaciente;
    }

    public void setIDPaciente(String IDPaciente) {
        this.IDPaciente = IDPaciente;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getValorTemperatura() {
        return valorTemperatura;
    }

    public void setValorTemperatura(String valorTemperatura) {
        this.valorTemperatura = valorTemperatura;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "ResultadosModel{" +
                "IDPaciente='" + IDPaciente + '\'' +
                ", fechaHora='" + fechaHora + '\'' +
                ", valorTemperatura='" + valorTemperatura + '\'' +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}