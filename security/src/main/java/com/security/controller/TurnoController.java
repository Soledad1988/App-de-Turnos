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

	
    public void asignarTurno(Integer idCliente, Date fecha) {
        turnoDao.asignarTurno(idCliente, fecha);
    }
    
    public int obtenerTurno(int idCliente, Date fecha) {
        return turnoDao.obtenerTurno(idCliente, fecha);
    }
    
}
