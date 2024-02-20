
package com.security.vista;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.security.controller.ClienteController;
import com.security.model.Cliente;


public class AltaClientesView extends JFrame {

    private static final long serialVersionUID = 1L;

    private JLabel labelNombre, labelApellido, labelDni;
    private JTextField textoNombre, textoApellido, textoDni;
    private JButton botonAAsignarTurno;
    private JTable tabla;
    private DefaultTableModel modelo;
    private ClienteController clienteController;

    
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

        Container container = getContentPane();
        getContentPane().setLayout(null);

        configurarCamposDelFormulario(container);

        configurarTablaDeContenido(container);
        
        
        JLabel lblWatsapp = new JLabel("Watsapp");
        lblWatsapp.setForeground(Color.BLACK);
        lblWatsapp.setBounds(22, 161, 104, 15);
        getContentPane().add(lblWatsapp);
        
        textoWatsapp = new JTextField();
        textoWatsapp.setBounds(147, 158, 128, 20);
        getContentPane().add(textoWatsapp);
        
        JLabel Logo = new JLabel("");
        Logo.setIcon(new ImageIcon("C:\\Users\\brent\\OneDrive\\Escritorio\\otros\\java\\img\\anteojos.jpg"));
        Logo.setBounds(401, 48, 281, 212);
        getContentPane().add(Logo);
        
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
        modelo.addColumn("Whatsapp");
        modelo.addColumn("Turno");
        
        tabla.setModel(modelo);
        
        
     // Ocultar la columna de ID
        tabla.removeColumn(tabla.getColumnModel().getColumn(0));

        tabla.setBounds(10, 307, 760, 219);
        

        container.add(tabla);
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(10, 271, 760, 182);
        container.add(scrollPane);

        setSize(800, 526);
        setVisible(true);
        setLocationRelativeTo(null);
    }


    private void configurarCamposDelFormulario(Container container) {
        labelNombre = new JLabel("Nombre");
        labelApellido = new JLabel("Apellido");
        labelDni = new JLabel("DNI");

        labelNombre.setBounds(22, 60, 95, 15);
        labelApellido.setBounds(22, 86, 115, 15);
        labelDni.setBounds(22, 120, 115, 15);
        
        labelNombre.setForeground(Color.BLACK);
        labelApellido.setForeground(Color.BLACK);
        labelDni.setForeground(Color.BLACK);

        textoNombre = new JTextField();
        textoApellido = new JTextField();
        textoDni = new JTextField();
        
        textoNombre.setBounds(147, 57, 128, 20);
        textoApellido.setBounds(147, 86, 128, 20);
        textoDni.setBounds(147, 117, 128, 20);

        botonAAsignarTurno = new JButton("Asignar Turno");
        botonAAsignarTurno.setBounds(59, 224, 171, 20);

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
/*
    private List<Cliente> ListarClientes() {
		return this.clienteController.listar();
   }
     
   private void cargarTabla() {			       
	    //Llenar Tabla
		List<Cliente> cliente = ListarClientes();
		try {
			for (Cliente clientes : cliente) {
				modelo.addRow(new Object[] { 
						clientes.getId(),  
						clientes.getNombre(), 
						clientes.getApellido(), 
						clientes.getWhatsapp()});
			}
		} catch (Exception e) {
			throw e;
		}
	}*/
    
   private void guardar(){
	    Cliente cliente = new Cliente(
	            textoNombre.getText(),
	            textoApellido.getText(),
	            textoDni.getText(),
	            textoWatsapp.getText());

	    this.clienteController.save(cliente);

	    JOptionPane.showMessageDialog(this, "Registrado con éxito!");

	}
    
	
}
