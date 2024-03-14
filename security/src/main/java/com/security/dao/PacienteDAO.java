package com.security.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.security.model.Paciente;

public class PacienteDAO {

 
	final private Connection con;
    
    public PacienteDAO(Connection con) {
        this.con = con;
    }
    
    public void save(Paciente paciente) {
        String sql = "INSERT INTO pacientes (nombre, apellido, dni, whatsapp, fecha_creacion) VALUES (?, ?, ?, ?, CURDATE())";
        
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /*
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
	}*/
    
    public List<Paciente> listarPacientesDeHoy() {
        LocalDate fechaActual = LocalDate.now();
        String sql = "SELECT id, nombre, apellido, dni, whatsapp, fecha_creacion FROM pacientes WHERE fecha_creacion >= ?";

        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setObject(1, fechaActual); // Pasar LocalDate directamente
            try (ResultSet rs = stm.executeQuery()) {
                List<Paciente> pacientesDeHoy = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    String dni = rs.getString("dni");
                    String whatsapp = rs.getString("whatsapp");
                    LocalDate fechaCreacion = rs.getObject("fecha_creacion", LocalDate.class); // Obtener LocalDate directamente
                    
                    Paciente paciente = new Paciente(id, nombre, apellido, dni, whatsapp, fechaCreacion);
                    pacientesDeHoy.add(paciente);
                }
                return pacientesDeHoy;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al listar pacientes de hoy: " + e.getMessage());
        }
    }


    private void transformarResultSetEnPaciente(List<Paciente> pacientes, ResultSet rst) {
        try {
            while (rst.next()) {
                int id = rst.getInt(1);
                String nombre = rst.getString(2);
                String apellido = rst.getString(3);
                String dni = rst.getString(4);
                String whatsapp = rst.getString(5);
                Date fechaCreacionSQL = rst.getDate(6); // Obtener java.sql.Date del ResultSet

                // Convertir java.sql.Date a LocalDate
                LocalDate fechaCreacion = ((java.sql.Date) fechaCreacionSQL).toLocalDate();

                // Crear un nuevo objeto Paciente utilizando LocalDate para la fecha de creación
                Paciente paciente = new Paciente(id, nombre, apellido, dni, whatsapp, fechaCreacion);
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            System.err.println("Error al transformar ResultSet en Paciente: " + e.getMessage());
        }
    }
    
    public Paciente buscarPacientePorId(int pacienteId) {
        String sql = "SELECT nombre, apellido, dni, whatsapp, fecha_creacion FROM pacientes WHERE id = ?";
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, pacienteId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    String dni = rs.getString("dni");
                    String whatsapp = rs.getString("whatsapp");
                    LocalDate fechaCreacion = rs.getDate("fecha_creacion").toLocalDate(); // Convertir a LocalDate
                    return new Paciente(pacienteId, nombre, apellido, dni, whatsapp, fechaCreacion);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al buscar paciente por ID: " + e.getMessage());
        }
        return null; // Devuelve null si no se encuentra el paciente con el ID especificado
    }

}