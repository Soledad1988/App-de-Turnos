package com.security.controller;

import java.awt.EventQueue;
import java.sql.SQLException;

import com.security.vista.TurnosView;

public class TurnoMein {
public static void main(String[] args) throws SQLException {
		
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
}}
