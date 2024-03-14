package com.security.model;

import java.time.LocalDate;

public class Paciente {

	private Integer id;
	private String nombre;
	private String apellido;
	private String dni;
	private String whatsapp;
	private LocalDate fechaCreacion;

	    public Paciente(Integer id, String nombre, String apellido, String dni, String whatsapp, LocalDate fechaCreacion) {
	        super();
	        this.id = id;
	        this.nombre = nombre;
	        this.apellido = apellido;
	        this.dni = dni;
	        this.whatsapp = whatsapp;
	        this.fechaCreacion = fechaCreacion;
	    }

	    public Paciente(String nombre, String apellido, String dni, String whatsapp) {
	        this.nombre = nombre;
	        this.apellido = apellido;
	        this.dni = dni;
	        this.whatsapp = whatsapp;
	    }


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getWhatsapp() {
		return whatsapp;
	}

	public void setWhatsapp(String whatsapp) {
		this.whatsapp = whatsapp;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	

}
