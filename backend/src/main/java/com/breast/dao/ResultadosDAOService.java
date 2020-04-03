package com.breast.dao;

import java.util.List;

import com.breast.model.ResultadosModel;

public interface ResultadosDAOService {

    List<ResultadosModel> obtenerResultados(String id);
    List<ResultadosModel> obtenerResultadosGeneral();
    void eliminateResults(String id, String fecha_hora);
    void eliminateAllResults(String id);
    void insertResult(ResultadosModel result);
}