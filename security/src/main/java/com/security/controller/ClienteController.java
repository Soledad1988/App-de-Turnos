package com.security.controller;

import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import com.security.conection.Conexion;
import com.security.dao.ClienteDAO;
import com.security.model.Cliente;

public class ClienteController {

	private ClienteDAO clienteDao;
	

    public ClienteController() throws SQLException {
        Connection connection = Conexion.obtenerConexion();
        this.clienteDao = new ClienteDAO(connection);
    }
    
    public void save(Cliente cliente) {
    	this.clienteDao.save(cliente);
    }
    
    public List<Cliente> listar() {
		return this.clienteDao.listar();
	}
    
}
