
package com.security.vista;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.security.controller.ClienteController;
import com.security.controller.TurnoController;
import com.security.model.Cliente;


public class AltaClientesView extends JFrame {

    private static final long serialVersionUID = 1L;

    private JLabel labelNombre, labelApellido, labelDni;
    private JTextField textoNombre, textoApellido, textoDni;
    private JButton botonAAsignarTurno;
    private JTable tabla;
    private DefaultTableModel modelo;
    private ClienteController clienteController;
    private TurnoController turnoController;
    
    private JTextField textoWatsapp;
    
    
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AltaClientesView frame = new AltaClientesView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    public AltaClientesView() throws SQLException {
        super("Clientes");

        this.clienteController = new ClienteController();
        this.turnoController = new TurnoController();

        Container container = getContentPane();
        getContentPane().setLayout(null);

        configurarCamposDelFormulario(container);

        configurarTablaDeContenido(container);
        
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
        scrollPane.setBounds(10, 184, 760, 182);
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
    	

    }

    private List<Cliente> ListarClientes() {
		return this.clienteController.listar();
   }
     
    private void cargarTabla() {			       
        // Limpiar la tabla antes de cargar los datos
        modelo.setRowCount(0);

        // Llenar la tabla con los clientes y sus turnos
        List<Cliente> clientes = ListarClientes();
        for (Cliente cliente : clientes) {
            // Obtener el turno del cliente para la fecha actual
            Date fecha = new Date(System.currentTimeMillis());
            int turno = this.turnoController.obtenerTurno(cliente.getId(), fecha);
            
            // Agregar los datos del cliente y su turno a la tabla
            modelo.addRow(new Object[] { 
                cliente.getId(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getDni(),
                cliente.getWhatsapp(),
                turno // Agregar el turno del cliente a la tabla
            });
        }
    }

    
    private void guardar() {
        // Crear un nuevo objeto Cliente con los datos ingresados en la vista
        Cliente cliente = new Cliente(
                textoNombre.getText(),
                textoApellido.getText(),
                textoDni.getText(),
                textoWatsapp.getText());

        // Guardar el cliente en la base de datos
        this.clienteController.save(cliente);

        // Obtener el ID del cliente recién insertado en la base de datos
        int idCliente = cliente.getId();

        // Obtener la fecha actual
        Date fecha = new Date(System.currentTimeMillis());

        // Obtener el turno actual del cliente
        int turno = this.turnoController.obtenerTurno(idCliente, fecha);

        if (turno <= 20) {
            // Asignar el turno al cliente en la base de datos
            this.turnoController.asignarTurno(idCliente, fecha);
            JOptionPane.showMessageDialog(this, "Turno asignado con éxito! Su turno es el número: " + turno);
        } else {
            JOptionPane.showMessageDialog(this, "Se han asignado todos los turnos para hoy. Intente mañana.");
        }

        // Después de asignar el turno, cargar nuevamente la tabla para reflejar los cambios
        cargarTabla();
    }

	
}
