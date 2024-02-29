package com.security.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.security.model.Paciente;


public class PacienteDAO {

 
	final private Connection con;
    
    public PacienteDAO(Connection con) {
        this.con = con;
    }
    
    public void save(Paciente paciente) {
    	
    	String sql = "INSERT INTO pacientes (nombre, apellido ,dni, whatsapp) VALUES (?, ?, ?, ?)";
    	
    	try (PreparedStatement stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
    	    stm.setString(1, paciente.getNombre());
    	    stm.setString(2, paciente.getApellido());
    	    stm.setString(3, paciente.getDni());
    	    stm.setString(4, paciente.getWhatsapp());
    	    stm.executeUpdate(); // Usar executeUpdate en lugar de execute
    		
    	    try(ResultSet rst = stm.getGeneratedKeys()){
    	        while(rst.next()) {
    	        	paciente.setId(rst.getInt(1));
    			}
    		}
    		
    	}catch (SQLException e) {
			throw new RuntimeException(e);
		}
    }
    
    public List<Paciente> listar() {
		List<Paciente> paciente = new ArrayList<Paciente>();
		try {
			String sql = "SELECT id, nombre, apellido, dni, whatsapp FROM pacientes";

			try (PreparedStatement stm = con.prepareStatement(sql)) {
				stm.execute();

				transformarResultSetEnPaciente(paciente, stm);
			}
			return paciente;
		} catch (SQLException e) {
			 e.printStackTrace(); 
			 
			throw new RuntimeException(e);
		}
	}
    
    
    private void transformarResultSetEnPaciente(List<Paciente> pacientes, PreparedStatement pstm) {
        ResultSet rst = null;
        try {
            rst = pstm.getResultSet();
            while (rst.next()) {
                Paciente paciente = new Paciente(
                		rst.getInt(1),  
                		rst.getString(2), 
                		rst.getString(3),
                        rst.getString(4), 
                        rst.getString(5));
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            System.err.println("Error al transformar ResultSet en Paciente: " + e.getMessage());
        } 
    }
    
    public Paciente buscarPacientePorId(int pacienteId) {
        String sql = "SELECT nombre, apellido, dni, whatsapp FROM pacientes WHERE id = ?";
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, pacienteId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    String dni = rs.getString("dni");
                    String whatsapp = rs.getString("whatsapp");
                    return new Paciente(pacienteId, nombre, apellido, dni, whatsapp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al buscar paciente por ID: " + e.getMessage());
        }
        return null; // Devuelve null si no se encuentra el cliente con el ID especificado
    }
}
