package com.security.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.security.model.Cliente;


public class ClienteDAO {

 
	final private Connection con;
    
    public ClienteDAO(Connection con) {
        this.con = con;
    }
    
    public void save(Cliente cliente) {
    	
    	String sql = "INSERT INTO nombre, apellido ,dni, whatsapp clientes ()VALUES ()";
    	
    	try (PreparedStatement stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
    		stm.setString(1, cliente.getNombre());
    		stm.setString(2, cliente.getApellido());
    		stm.setString(3, cliente.getDni());
    		stm.setString(4, cliente.getWhatsapp());
    		stm.execute();
    		
    		try(ResultSet rst = stm.getGeneratedKeys()){
    			while(rst.next()) {
    				cliente.setId(1);
    			}
    		}
    		
    	}catch (SQLException e) {
			throw new RuntimeException(e);
		}
    }
}
