package com.security.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.security.model.Paciente;

public class TurnoDAO {

final private Connection con;
    
    public TurnoDAO(Connection con) {
        this.con = con;
    }
    
    public void asignarTurno(Integer idCliente, Date fecha) {
        // Obtener el número de turnos ya asignados para esa fecha
        int turnosAsignados = obtenerCantidadTurnosAsignados(fecha);

        // Si ya se asignaron los 20 turnos, mostrar un mensaje y salir
        if (turnosAsignados >= 20) {
            System.out.println("Se han asignado todos los turnos para hoy. Intente mañana.");
            return;
        }

        // Calcular el siguiente turno disponible (el siguiente número después del último asignado)
        int siguienteTurno = turnosAsignados + 1;

        // Resto del código para insertar el turno en la base de datos
        String sqlTurno = "INSERT INTO turnos (pacienteId, turno, fecha) VALUES (?, ?, ?)";
        try (PreparedStatement stmTurno = con.prepareStatement(sqlTurno)) {
            stmTurno.setInt(1, idCliente);
            stmTurno.setInt(2, siguienteTurno);
            stmTurno.setDate(3, new java.sql.Date(fecha.getTime()));
            stmTurno.executeUpdate();
            System.out.println("Turno asignado con éxito! Su turno es el número: " + siguienteTurno);
        } catch (SQLException e) {
            System.err.println("Error al asignar el turno: " + e.getMessage());
        }
    }
    
    private int obtenerCantidadTurnosAsignados(Date fecha) {
        int turnosAsignados = 0;
        String sql = "SELECT COUNT(*) AS cantidad FROM turnos WHERE fecha = ?";
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setDate(1, new java.sql.Date(fecha.getTime()));
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    turnosAsignados = rs.getInt("cantidad");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return turnosAsignados;
    }
    
    public int obtenerTurno(int idPaciente, Date fecha) {
        if (fecha == null) {
            // Manejar el caso en que fecha sea null
            return 0; // O algún valor predeterminado si no se encuentra ningún turno
        }

        String sql = "SELECT turno FROM turnos WHERE pacienteId = ? AND fecha = ?";
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, idPaciente);
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
    
    public void resetearTurnosAlInicioDelDia() {
        // Eliminar todos los registros de turnos de la base de datos
        String sql = "DELETE FROM turnos";
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al resetear los turnos al inicio del día: " + e.getMessage());
        }
    }

    public void almacenarPacientesDelDiaAnterior() {
        // Obtener los pacientes que tuvieron turno el día anterior
        // Por ejemplo, asumiendo que la fecha del día anterior es la fecha actual menos un día
        Date fechaAnterior = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
        String sql = "SELECT DISTINCT pacienteId FROM turnos WHERE fecha = ?";
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setDate(1, new java.sql.Date(fechaAnterior.getTime()));
            try (ResultSet rs = stm.executeQuery()) {
                // Almacenar los pacientes que tuvieron turno el día anterior
                PacienteDAO pacienteDAO = new PacienteDAO(con);
                while (rs.next()) {
                    int pacienteId = rs.getInt("pacienteId");
                    Paciente paciente = pacienteDAO.buscarPacientePorId(pacienteId);
                    // Guardar el cliente en la base de datos de pacientes, si no está ya almacenado
                    // Aquí debes implementar la lógica para almacenar el cliente en la base de datos de pacientes
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al almacenar los pacientes del día anterior: " + e.getMessage());
        }
    }
    
  
}
