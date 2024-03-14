package com.security.controller;

import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import com.security.conection.Conexion;
import com.security.dao.PacienteDAO;
import com.security.model.Paciente;

import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import com.security.conection.Conexion;
import com.security.dao.PacienteDAO;
import com.security.model.Paciente;

public class PacienteController {

	private PacienteDAO pacienteDao;
	

    public PacienteController() throws SQLException {
        Connection connection = Conexion.obtenerConexion();
        this.pacienteDao = new PacienteDAO(connection);
    }
    
    public void save(Paciente paciente) {
    	this.pacienteDao.save(paciente);
    }
    
    public List<Paciente> listar() {
		return this.pacienteDao.listarPacientesDeHoy();
	}
    
 
    public List<Paciente> listarPacientesDeHoy() {
        return this.pacienteDao.listarPacientesDeHoy();
     }
    
    
}