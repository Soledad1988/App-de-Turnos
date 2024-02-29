package com.security.model;

import java.sql.Date;

public class Turno {

	private Integer IdTurno;
	private Integer IdCliente;
	private int turno;
	private Date fecha;
	
	public Turno() {
      
    }
	
	public Turno(Integer idTurno, Integer idCliente, int turno, Date fecha) {
		super();
		IdTurno = idTurno;
		IdCliente = idCliente;
		this.turno = turno;
		this.fecha = fecha;
	}


	public Integer getIdTurno() {
		return IdTurno;
	}

	public void setIdTurno(Integer idTurno) {
		IdTurno = idTurno;
	}

	public Integer getIdCliente() {
		return IdCliente;
	}

	public void setIdCliente(Integer idCliente) {
		IdCliente = idCliente;
	}

	public int getTurno() {
		return turno;
	}

	public void setTurno(int turno) {
		this.turno = turno;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
}
