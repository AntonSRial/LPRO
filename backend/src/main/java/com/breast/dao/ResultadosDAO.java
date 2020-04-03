package com.breast.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.breast.dao.ResultadosDAOService;
import com.breast.model.ResultadosModel;

@Repository
public class ResultadosDAO extends JdbcDaoSupport implements ResultadosDAOService {

    // Spring Boot will create and configure DataSource and JdbcTemplate
    // To use it, just @Autowired
    @Autowired
    DataSource dataSource;
    //private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public void insertResult(ResultadosModel result) {
        String sql = "INSERT INTO resultados " +
                "(id,fecha_hora,valor_temperatura,comentario_medico) VALUES (?,?,?,?)";
        getJdbcTemplate().update(sql, new Object[]{result.getIDPaciente(),
                result.getFechaHora(), result.getValorTemperatura(), result.getComentario()});
    }

    @Override
    public void eliminateAllResults(String id) {
        String sql = "DELETE FROM RESULTADOS WHERE id= ?";
        getJdbcTemplate().update(sql, id);
    }

    //Confirmar que se manda así la sentencia
    @Override
    public void eliminateResults(String id, String fecha_hora) {
        String sql = "DELETE FROM RESULTADOS WHERE id= ? AND fecha_hora=?";
        getJdbcTemplate().update(sql, id, fecha_hora);
    }

    //Obtiene todos los resultados recogidos para el backlog
    @Override
    public List<ResultadosModel> obtenerResultadosGeneral() {
        String sql = "SELECT * FROM PACIENTES";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

        List<ResultadosModel> results = new ArrayList<ResultadosModel>();
        for (Map<String, Object> row : rows) {
            ResultadosModel result = new ResultadosModel();
            result.setIDPaciente((String) row.get("id_paciente"));
            result.setFechaHora((String) row.get("fecha_hora"));
            result.setValorTemperatura((String) row.get("valor_temperatura"));
            result.setComentario((String) row.get("comentario"));
            results.add(result);
        }
        return results;
    }

    @Override
    public List<ResultadosModel> obtenerResultados(String id) {
        String sql = "SELECT * FROM PACIENTES WHERE id_paciente=?";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

        List<ResultadosModel> results = new ArrayList<ResultadosModel>();
        for (Map<String, Object> row : rows) {
            ResultadosModel result = new ResultadosModel();
            result.setIDPaciente((String) row.get("id_paciente"));
            result.setFechaHora((String) row.get("fecha_hora"));
            result.setValorTemperatura((String) row.get("valor_temperatura"));
            result.setComentario((String) row.get("comentario"));
            results.add(result);
        }
        return results;
    }

}