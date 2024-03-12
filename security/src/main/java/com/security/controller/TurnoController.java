package com.security.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import com.security.conection.Conexion;
import com.security.dao.TurnoDAO;

public class TurnoController {
	
private TurnoDAO turnoDao;
	

    public TurnoController() throws SQLException {
        Connection connection = Conexion.obtenerConexion();
        this.turnoDao = new TurnoDAO(connection);
    }

	
    public void asignarTurno(Integer idPaciente, Date fecha) {
        turnoDao.asignarTurno(idPaciente, fecha);
    }
    
    public int obtenerTurno(int idPaciente, Date fecha) {
        return turnoDao.obtenerTurno(idPaciente, fecha);
    }
    
    public void resetearTurnosAlInicioDelDia() {
    	turnoDao.resetearTurnosAlInicioDelDia();
    }
    
}
