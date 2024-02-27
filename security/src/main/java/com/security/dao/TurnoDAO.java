package com.security.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class TurnoDAO {

final private Connection con;
    
    public TurnoDAO(Connection con) {
        this.con = con;
    }
    
    public void asignarTurno(Integer idCliente, Date fecha) {
        // Generar un número aleatorio del 1 al 20 para el turno
        Random random = new Random();
        int turno = random.nextInt(20) + 1;

        // Resto del código para insertar el turno en la base de datos
        String sqlTurno = "INSERT INTO turnos (clienteId, turno, fecha) VALUES (?, ?, ?)";
        try (PreparedStatement stmTurno = con.prepareStatement(sqlTurno)) {
            stmTurno.setInt(1, idCliente);
            stmTurno.setInt(2, turno);
            stmTurno.setDate(3, new java.sql.Date(fecha.getTime()));
            stmTurno.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al asignar el turno: " + e.getMessage());
        }
    }
    
    public int obtenerTurno(int idCliente, Date fecha) {
        String sql = "SELECT turno FROM turnos WHERE clienteId = ? AND fecha = ?";
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, idCliente);
            stm.setDate(2, new java.sql.Date(fecha.getTime()));
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("turno");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // O algún valor predeterminado si no se encuentra ningún turno
    }
}
