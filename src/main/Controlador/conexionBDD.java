package main.Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import main.Modelo.Cliente;
import main.Modelo.Vehiculo;

public class conexionBDD {
	public static String url = "jdbc:postgresql://localhost:5432/Mecatall";
	public static String usuario = "";
	public static String contra = "";
	
	
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
				gestorClases.clientes.clear();
				while(respuesta.next()) {
					String nif = respuesta.getString(1);
					String nombre = respuesta.getString(2);
					String telefono = respuesta.getString(3);
					String direc = respuesta.getString(4);
					String ciudad = respuesta.getString(5);
					int tel = Integer.parseInt(telefono);
					Cliente c = new Cliente(nif, nombre, tel, direc, ciudad);
					gestorClases.clientes.add(c);
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
	
	public static boolean insertarCliente(Cliente c) {
		boolean correcto = false;
		String nif = c.getNif();
		String nombre = c.getNombre();
		int telefono = c.getTel();
		String direc = c.getDirec();
		String ciudad = c.getCiudad();
		Connection conn;
		try {
			PreparedStatement sentencia = null;
			conn = DriverManager.getConnection(url, usuario, contra);
			String consulta = "INSERT INTO CLIENTE VALUES(?,?,?,?,?)";
			if(conn != null) {
				sentencia = conn.prepareStatement(consulta);
				sentencia.setString(1, nif);
				sentencia.setString(2, nombre);
				sentencia.setInt(3, telefono);
				sentencia.setString(4, direc);
				sentencia.setString(5, ciudad);
				int filasinsert = sentencia.executeUpdate();
				if(filasinsert >0) {
					correcto = true;
				}
			}else {
				System.out.println("Error conex");
			}
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
			correcto = false;
		}
		return correcto;
		
	}
	
	public static void cargarVehiculos(Cliente c) {
		String nif = c.getNif();
		Connection conn;
		try {
			
			Statement sentencia = null;
			ResultSet respuesta = null;
			conn = DriverManager.getConnection(url, usuario, contra);
			if(conn != null) {
				sentencia = conn.createStatement();
				String consulta= "Select * FROM VEHICULO WHERE nif_cliente = '"+nif+"'";
				respuesta = sentencia.executeQuery(consulta);
				c.Vehiculos.clear();
				while(respuesta.next()) {
					String matricula = respuesta.getString(1);
					String color = respuesta.getString(2);
					String modelo = respuesta.getString(3);
					String anio = respuesta.getString(4);
					LocalDate fmatri = LocalDate.parse(anio);
					Vehiculo v = new Vehiculo(matricula, color, modelo, fmatri);
					c.Vehiculos.add(v);
				}
			}else {
				System.out.println("Error conex");
			}
			conn.close();	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static boolean insertarVehiculo(Vehiculo v , String nif) {
		boolean correcto = false;
		Connection conn;
		String consulta= "INSERT INTO VEHICULO VALUES (?,?,?,?,?)";
		java.sql.Date fechasql = java.sql.Date.valueOf(v.getAniomatri());
		try {
			
			PreparedStatement sentencia = null;
			conn = DriverManager.getConnection(url, usuario, contra);
			if(conn != null) {
				sentencia = conn.prepareStatement(consulta);
				sentencia.setString(1, v.getMatricula());
				sentencia.setString(2, v.getColor());
				sentencia.setString(3, v.getModelo());
				sentencia.setDate(4, fechasql);
				sentencia.setString(5, nif);
				int filasafect = sentencia.executeUpdate();
				if(filasafect > 0) {
					correcto = true;
				}
			}else {
				System.out.println("Error conex");
			}
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
			correcto = false;
		}  
		return correcto;
	}
	
	public static boolean comprobarMatricula(String matricula) {
		boolean correcto = false;
		Connection conn;
		try {
			Statement sentencia = null;
			ResultSet respuesta = null;
			conn = DriverManager.getConnection(url, usuario, contra);
			if(conn != null) {
				sentencia = conn.createStatement();
				String consulta= "Select * FROM VEHICULO WHERE matricula = '"+matricula+"'";
				respuesta = sentencia.executeQuery(consulta);
				correcto = respuesta.next();
				
			}else {
				System.out.println("Error conex");
			}
			conn.close();
			return correcto;
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			correcto = false;
		}
		return correcto;
	}

}
