
package com.security.vista;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.io.FileOutputStream;
import java.io.IOException;
import com.security.model.Paciente;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.security.controller.PacienteController;
import com.security.controller.TurnoController;

import java.util.logging.Level;
import java.util.logging.Logger;


public class TurnosView extends JFrame {

    private static final long serialVersionUID = 1L;

    private JLabel labelNombre, labelApellido, labelDni;
    private JTextField textoNombre, textoApellido, textoDni;
    private JButton botonAAsignarTurno;
    private JTable tabla;
    private DefaultTableModel modelo;
    private PacienteController pacienteController;
    private TurnoController turnoController;
    private JButton botonExportarExcel; // Nuevo botón para exportar a Excel
    private JTextField textoWatsapp;
    
    
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TurnosView frame = new TurnosView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    public TurnosView() throws SQLException {
        super("Pacientes");
        
     // Inicializar el controlador de turno antes de llamar a resetearTurnosAlInicioDelDia
        this.turnoController = new TurnoController();
        
     // Llama al método para reiniciar los turnos al inicio del día después de inicializar turnoController
        this.turnoController.resetearTurnosAlInicioDelDia();

        this.pacienteController = new PacienteController();
      

        // Configurar el nivel de registro para la clase ZipPackage a WARN
        Logger.getLogger("org.apache.poi.openxml4j.opc.ZipPackage").setLevel(Level.WARNING);
        
        // Configurar la tabla antes de cargar los nuevos datos
        Container container = getContentPane();
        getContentPane().setLayout(null);
        
        configurarCamposDelFormulario(container);

        configurarTablaDeContenido(container);
        
        // Limpiar la tabla antes de cargar los nuevos datos
        modelo.setRowCount(0);
        
        cargarTabla();
        
        JLabel lblWatsapp = new JLabel("Watsapp");
        lblWatsapp.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblWatsapp.setForeground(Color.BLACK);
        lblWatsapp.setBounds(365, 96, 104, 25);
        getContentPane().add(lblWatsapp);
        
        textoWatsapp = new JTextField();
        textoWatsapp.setBounds(514, 97, 128, 20);
        getContentPane().add(textoWatsapp);
        
        JLabel lblTitulo = new JLabel("Asignación de Turnos");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
        lblTitulo.setBounds(275, 0, 281, 30);
        getContentPane().add(lblTitulo);
        
     // Configurar botón para exportar a Excel
        botonExportarExcel = new JButton("Exportar a Excel");
        botonExportarExcel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        botonExportarExcel.setBounds(633, 5, 143, 41);
        container.add(botonExportarExcel);

        configurarAccionesDelFormulario();
    }
    

    private void configurarTablaDeContenido(Container container) {
        tabla = new JTable();
        
        modelo = new DefaultTableModel();
        modelo.addColumn("Id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("DNI");
        modelo.addColumn("Whatsapp");
        modelo.addColumn("Turno");
        
        tabla.setModel(modelo);
        
        
     // Ocultar la columna de ID
        tabla.removeColumn(tabla.getColumnModel().getColumn(0));

        tabla.setBounds(10, 307, 760, 219);
        

        container.add(tabla);
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(16, 184, 760, 182);
        container.add(scrollPane);

        setSize(800, 431);
        setVisible(true);
        setLocationRelativeTo(null);
    }


    private void configurarCamposDelFormulario(Container container) {
        labelNombre = new JLabel("Nombre");
        labelNombre.setFont(new Font("Tahoma", Font.BOLD, 14));
        labelApellido = new JLabel("Apellido");
        labelApellido.setFont(new Font("Tahoma", Font.BOLD, 14));
        labelDni = new JLabel("DNI");
        labelDni.setFont(new Font("Tahoma", Font.BOLD, 14));

        labelNombre.setBounds(22, 60, 95, 25);
        labelApellido.setBounds(22, 96, 115, 19);
        labelDni.setBounds(365, 60, 115, 15);
        
        labelNombre.setForeground(Color.BLACK);
        labelApellido.setForeground(Color.BLACK);
        labelDni.setForeground(Color.BLACK);

        textoNombre = new JTextField();
        textoApellido = new JTextField();
        textoDni = new JTextField();
        
        textoNombre.setBounds(147, 57, 128, 20);
        textoApellido.setBounds(147, 97, 128, 20);
        textoDni.setBounds(514, 57, 128, 20);

        botonAAsignarTurno = new JButton("Asignar Turno");
        botonAAsignarTurno.setFont(new Font("Tahoma", Font.PLAIN, 14));
        botonAAsignarTurno.setBounds(298, 132, 171, 41);

        container.add(labelNombre);
        container.add(labelApellido);
        container.add(labelDni);
        container.add(textoNombre);
        container.add(textoApellido);
        container.add(textoDni);
        container.add(botonAAsignarTurno);
       
        
    }


    private void configurarAccionesDelFormulario() {
   
    	botonAAsignarTurno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	guardar();
            }
            
        });
    	
    	botonExportarExcel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportarAExcel();
            }
        });
    	

    }

    private List<Paciente> ListarPacientes() {
		return this.pacienteController.listar();
   }
     
    
    private void guardar() {
        // Crear un nuevo objeto Cliente con los datos ingresados en la vista
        Paciente cliente = new Paciente(
                textoNombre.getText(),
                textoApellido.getText(),
                textoDni.getText(),
                textoWatsapp.getText());

        // Guardar el cliente en la base de datos
        this.pacienteController.save(cliente);

        // Obtener el ID del cliente recién insertado en la base de datos
        int idCliente = cliente.getId();

        // Obtener la fecha actual
        Date fecha = new Date(System.currentTimeMillis());

        // Asignar el turno al cliente en la base de datos
        this.turnoController.asignarTurno(idCliente, fecha);

        // Actualizar la tabla para reflejar los cambios
        cargarTabla();

        // Obtener el turno actual del cliente DESPUÉS de asignarlo
        int turno = this.turnoController.obtenerTurno(idCliente, fecha);

        if (turno <= 20) {
            JOptionPane.showMessageDialog(this, "Turno asignado con éxito! Su turno es el número: " + turno);
            // Limpiar los campos del formulario después de asignar el turno
            textoNombre.setText("");
            textoApellido.setText("");
            textoDni.setText("");
            textoWatsapp.setText("");
        
        } else {
            JOptionPane.showMessageDialog(this, "Se han asignado todos los turnos para hoy. Intente mañana.");
        }
    }
    
    private void cargarTabla() {
        // Limpiar la tabla antes de cargar los datos
        modelo.setRowCount(0);

        // Llenar la tabla con los clientes y sus turnos
        List<Paciente> pacientes = ListarPacientes();
        for (Paciente paciente : pacientes) {
            // Obtener el turno del cliente para la fecha actual
            Date fecha = new Date(System.currentTimeMillis());
            int turno = this.turnoController.obtenerTurno(paciente.getId(), fecha);

            // Agregar los datos del cliente y su turno a la tabla
            modelo.addRow(new Object[]{
            		paciente.getId(),
            		paciente.getNombre(),
            		paciente.getApellido(),
            		paciente.getDni(),
            		paciente.getWhatsapp(),
                    turno // Agregar el turno del cliente a la tabla
            });
        }
        
        // Notificar a la tabla que los datos han cambiado y que necesita actualizarse
        modelo.fireTableDataChanged();
    }
    
 // Método para exportar a Excel
    private void exportarAExcel() {
    	 // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();
        // Formatear la fecha actual en el formato deseado para incluirla en el nombre del archivo
        String fechaFormateada = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(fechaActual);

        // Obtener los datos de la tabla
        List<Paciente> pacientes = ListarPacientes();
        
        // Crear el nombre del archivo con la fecha actual
        String nombreArchivo = "clientes_y_turnos_" + fechaFormateada + ".xlsx";

        // Definir la ruta del directorio donde se guardarán los archivos
        String rutaDirectorio = "C:\\Users\\brent\\OneDrive\\Escritorio\\turnos\\";

        // Combinar la ruta del directorio con el nombre del archivo para obtener la ruta completa
        String rutaCompleta = rutaDirectorio + nombreArchivo;

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Clientes y Turnos");
            int rowNum = 0;
            Row headerRow = sheet.createRow(rowNum++);
            String[] headers = {"ID", "Nombre", "Apellido", "DNI", "Whatsapp", "Turno"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            for (Paciente paciente : pacientes) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(paciente.getId());
                row.createCell(1).setCellValue(paciente.getNombre());
                row.createCell(2).setCellValue(paciente.getApellido());
                row.createCell(3).setCellValue(paciente.getDni());
                row.createCell(4).setCellValue(paciente.getWhatsapp());
                // Aquí debes obtener el turno del cliente y establecerlo en la celda correspondiente
                row.createCell(5).setCellValue(turnoController.obtenerTurno(paciente.getId(), new Date(System.currentTimeMillis())));
            }
            
            
            try (FileOutputStream fileOut = new FileOutputStream(rutaCompleta)) {
                workbook.write(fileOut);
                System.out.println("Archivo Excel creado correctamente.");
                JOptionPane.showMessageDialog(this, "Datos exportados a Excel correctamente.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al exportar datos a Excel: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

	
}
