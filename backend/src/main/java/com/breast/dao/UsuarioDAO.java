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

import com.breast.dao.UsuarioDAOService;
import com.breast.model.UsuarioModel;

@Repository
public class UsuarioDAO extends JdbcDaoSupport implements UsuarioDAOService{

    // Spring Boot will create and configure DataSource and JdbcTemplate
    // To use it, just @Autowired
    @Autowired
    DataSource dataSource;
    //private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }

    @Override
    public void insertUser(UsuarioModel user) {
        String sql = "INSERT INTO usuario " +
                "(id, nombre, contrasena, correo, fecha_nacimiento, medicacion, dolencia, rol) VALUES (?, ?,?,?,?,?,?,?)";
        getJdbcTemplate().update(sql, new Object[]{user.getId(), user.getName(), user.getContrasena(), user.getCorreo(),
                user.getFechaNacimiento(), user.getMedicacion(), user.getDolenciaEstudio(), user.getRol()});
    }

    @Override
    public UsuarioModel getUserById(String id) {
        String sql = "SELECT * FROM USUARIO WHERE id= ?";
        return (UsuarioModel)getJdbcTemplate().queryForObject(sql, new Object[]{id}, new RowMapper<UsuarioModel>() {

            @Override
            public UsuarioModel mapRow(ResultSet rs, int rwNumber) throws SQLException{
                UsuarioModel user = new UsuarioModel();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("nombre"));
                user.setName(rs.getString("contrasena"));
                user.setName(rs.getString("correo"));
                user.setName(rs.getString("fecha_nacimiento"));
                user.setName(rs.getString("medicacion"));
                user.setName(rs.getString("dolencia"));
                user.setName(rs.getString("rol"));
                return user;
            }
        });
    }

    @Override
    public List<UsuarioModel> findAll() {
        String sql = "SELECT * FROM USUARIO";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

        List<UsuarioModel> result = new ArrayList<UsuarioModel>();
        for(Map<String, Object> row: rows){
                UsuarioModel user = new UsuarioModel();
                user.setId((String)row.get("id"));
                user.setName((String)row.get("nombre"));
                user.setName((String)row.get("contrasena"));
                user.setName((String)row.get("correo"));
                user.setName((String)row.get("fecha_nacimiento"));
                user.setName((String)row.get("medicacion"));
                user.setName((String)row.get("dolencia"));
                user.setName((String)row.get("rol"));
                result.add(user);
        }
        return result;
    }

    @Override
    public void eliminateUser(String id) {
        String sql = "DELETE FROM USUARIO WHERE id= ?";
        getJdbcTemplate().update(sql, id);
    }


    @Override
    public void modifyUSer(UsuarioModel user) {
        //Para modificar un usuario primero lo eliminamos de bdd
        // y después lo persistimos con los nuevos datos

        String sql = "DELETE FROM USUARIO WHERE id= ?";
        getJdbcTemplate().update(sql, user.getId());

        String sql2 = "INSERT INTO usuario " +
                "(id, nombre, contrasena, correo, fecha_nacimiento, medicacion, dolencia, rol) VALUES (?, ?,?,?,?,?,?,?)";
        getJdbcTemplate().update(sql2, new Object[]{user.getId(), user.getName(), user.getContrasena(), user.getCorreo(),
                user.getFechaNacimiento(), user.getMedicacion(), user.getDolenciaEstudio(), user.getRol()});
    }







}
