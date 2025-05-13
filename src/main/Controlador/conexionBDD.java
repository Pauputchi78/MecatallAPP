package main.Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
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
				System.out.println("Sentencia creada");
				respuesta = sentencia.executeQuery(consulta);
				while(respuesta.next()) {
					String matricula = respuesta.getString(1);
					String color = respuesta.getString(2);
					String modelo = respuesta.getString(3);
					String anio = respuesta.getString(4);
					LocalDate fmatri = LocalDate.parse(anio);
					Vehiculo v = new Vehiculo(matricula, color, modelo, fmatri);
					c.Vehiculos.add(v);
					System.out.println(v.toString());
				}
				System.out.println("Fin vehiculos");
			}else {
				System.out.println("Error conex");
			}
			conn.close();
			
			
			
		}catch(SQLException e) {
			
		}
		
	}
	
	public static boolean insertarVehiculo(Vehiculo v , String nif) {
		boolean correcto = false;
		String matricula = v.getMatricula();
		String color = v.getColor();
		String modelo = v.getModelo();
		String aniomatri = v.getAniomatri().toString();
		String valores = "('"+matricula+"','"+color+"','"+modelo+"','"+aniomatri+"','"+nif+"')";
		Connection conn;
		try {
			
			Statement sentencia = null;
			conn = DriverManager.getConnection(url, usuario, contra);
			if(conn != null) {
				sentencia = conn.createStatement();
				String consulta= "INSERT INTO VEHICULO VALUES "+valores;
				System.out.println("Sentencia creada");
				ResultSet respuesta = sentencia.executeQuery(consulta);
				correcto = true;
			}else {
				System.out.println("Error conex");
			}
			conn.close();
			
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}  
		return correcto;
	}
	
	public static boolean comprobarMatricula(String matricula) {
		Connection conn;
		try {
			
			Statement sentencia = null;
			ResultSet respuesta = null;
			conn = DriverManager.getConnection(url, usuario, contra);
			if(conn != null) {
				sentencia = conn.createStatement();
				String consulta= "Select * FROM VEHICULO WHERE matricula = '"+matricula+"'";
				System.out.println("Sentencia creada");
				respuesta = sentencia.executeQuery(consulta);
				return respuesta.next();
				
			}else {
				System.out.println("Error conex");
			}
			conn.close();
			
			
			
		}catch(SQLException e) {
			
		}
		return false;
	}

}
