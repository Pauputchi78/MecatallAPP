package main.Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.Modelo.Cliente;

public class conexionBDD {
	public static String url = "jdbc:postgresql://localhost:5432/Mecatall";
	public static String usuario = "postgres";
	public static String contra = "postgres";
	
	
	public static void cargarClinetesBDD() {
		Connection conn;
		try {
			Statement sentencia = null;
			ResultSet respuesta = null;
			conn = DriverManager.getConnection(url, usuario, contra);
			if(conn != null) {
				sentencia = conn.createStatement();
				String consulta = "SELECT * FROM CLIENTE";
				respuesta = sentencia.executeQuery(consulta);
				principal.clientes.clear();
				while(respuesta.next()) {
					String nif = respuesta.getString(1);
					String nombre = respuesta.getString(2);
					String telefono = respuesta.getString(3);
					String direc = respuesta.getString(4);
					String ciudad = respuesta.getString(5);
					int tel = Integer.parseInt(telefono);
					Cliente c = new Cliente(nif, nombre, tel, direc, ciudad);
					principal.clientes.add(c);
				}
				System.out.println("Clientes cargados");
				conn.close();
			}else {
				System.out.println("Conexión no realizada");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void cargarVehiculos(Cliente c) {
		String nif = "B23456789";
		Connection conn;
		try {
			
			Statement sentencia = null;
			ResultSet respuesta = null;
			conn = DriverManager.getConnection(url, usuario, contra);
			if(conn != null) {
				sentencia = conn.createStatement();
				String consulta= "Select * FROM VEHICULO WHERE nif_cliente = '"+nif+"'";
				System.out.println("Sentencia creada");
				respuesta = sentencia.executeQuery(consulta);
				while(respuesta.next()) {
					String matricula = respuesta.getString(1);
					String color = respuesta.getString(2);
					String modelo = respuesta.getString(3);
					String anio = respuesta.getString(4);
					System.out.println(matricula + color + modelo + anio);
				}
				System.out.println("Fin vehiculos");
			}else {
				System.out.println("Error conex");
			}
			
			
			
		}catch(SQLException e) {
			
		}
		
	}

}
