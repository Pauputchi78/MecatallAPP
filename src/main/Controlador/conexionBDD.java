package main.Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import main.Modelo.Cliente;
import main.Modelo.Incidencia;
import main.Modelo.Reparar;
import main.Modelo.Vehiculo;

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
	
	public static boolean eliminarVehiculoMatricula(String matricula) {
		boolean correcto = false;
		Connection conn;
		try {
			String consulta = "DELETE FROM VEHICULO WHERE matricula = ?";
			PreparedStatement sentencia = null;
			ResultSet respuesta = null;
			conn = DriverManager.getConnection(url, usuario, contra);
			if(conn != null) {
				sentencia = conn.prepareStatement(consulta);
				sentencia.setString(1, matricula);
				int fila = sentencia.executeUpdate();
				if(fila > 0) {
					correcto = true;
				}
			}else {
				System.out.println("Error conex");
			}
			conn.close();	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return correcto;
	}
	public static boolean eliminarVehiculosCliente(String nif) {
		boolean correcto = false;
		Connection conn;
		try {
			String consulta = "DELETE FROM VEHICULO WHERE nif_cliente = ?";
			PreparedStatement sentencia = null;
			conn = DriverManager.getConnection(url, usuario, contra);
			if(conn != null) {
				sentencia = conn.prepareStatement(consulta);
				sentencia.setString(1, nif);
				int fila = sentencia.executeUpdate();
				if(fila > 0) {
					correcto = true;
				}
			}else {
				System.out.println("Error conex");
			}
			conn.close();	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return correcto;
	}
	
	public static boolean eliminarCliente(String nif) {
		boolean correcto = false;
		Connection conn;
		try {
			String consulta = "DELETE FROM CLIENTE WHERE nif = ?";
			PreparedStatement sentencia = null;
			conn = DriverManager.getConnection(url, usuario, contra);
			if(conn != null) {
				sentencia = conn.prepareStatement(consulta);
				sentencia.setString(1, nif);
				int fila = sentencia.executeUpdate();
				if(fila > 0) {
					correcto = true;
				}
			}else {
				System.out.println("Error conex");
			}
			conn.close();	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return correcto;
	}
	
	public static boolean modificarCliente(Cliente c) {
		boolean correcto = false;
		Connection conn;
		try {
			String consulta = "UPDATE CLIENTE SET nombre = ?, telefono = ?, direccion = ?, ciudad = ? WHERE nif = ?";
			PreparedStatement sentencia = null;
			conn = DriverManager.getConnection(url, usuario, contra);
			if(conn != null) {
				sentencia = conn.prepareStatement(consulta);
				sentencia.setString(1, c.getNombre());
				sentencia.setInt(2, c.getTel());
				sentencia.setString(3, c.getDirec());
				sentencia.setString(4, c.getCiudad());
				sentencia.setString(5, c.getNif());
				int fila = sentencia.executeUpdate();
				if(fila > 0) {
					correcto = true;
				}
			}else {
				System.out.println("Error conex");
			}
			conn.close();	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return correcto;
	}
	
	public static String[] cargarMatriculas() {
		String [] matriculas = null; 
		Connection conn;
		try {
			
			Statement sentencia = null;
			ResultSet respuesta = null;
			conn = DriverManager.getConnection(url, usuario, contra);
			if(conn != null) {
				sentencia = conn.createStatement();
				String consulta= "select matricula from vehiculo order by matricula ASC";
				respuesta = sentencia.executeQuery(consulta);
				ArrayList<String> matri = new ArrayList<String>();
				while(respuesta.next()) {
					String matricula = respuesta.getString(1);
					matri.add(matricula);
				}
				matriculas = matri.toArray(new String[0]);
				
			}else {
				System.out.println("Error conex");
			}
			conn.close();	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return matriculas;
	}
	
	public static String[] cargarTaller() {
		String [] cod_taller = null; 
		Connection conn;
		try {
			
			Statement sentencia = null;
			ResultSet respuesta = null;
			conn = DriverManager.getConnection(url, usuario, contra);
			if(conn != null) {
				sentencia = conn.createStatement();
				String consulta= "select cod_sucursal from taller order by cod_sucursal ASC";
				respuesta = sentencia.executeQuery(consulta);
				ArrayList<String> cod = new ArrayList<String>();
				while(respuesta.next()) {
					String codigo = respuesta.getString(1);
					cod.add(codigo);
				}
				cod_taller = cod.toArray(new String[0]);
				
			}else {
				System.out.println("Error conex");
			}
			conn.close();	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return cod_taller;
	}
	
	public static boolean insertarReparacion(Reparar rep) {
		boolean correcto = false;
		Connection conn;
		String consulta= "INSERT INTO REPARAR VALUES (?,?,?)";
		java.sql.Date fechasql = java.sql.Date.valueOf(rep.getFechaparte());
		try {
			
			PreparedStatement sentencia = null;
			conn = DriverManager.getConnection(url, usuario, contra);
			if(conn != null) {
				sentencia = conn.prepareStatement(consulta);
				sentencia.setString(1, rep.getMatricula());
				sentencia.setString(2, rep.getCod_taller());
				sentencia.setDate(3, fechasql);
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
	
	public static boolean comprobarReparacion(String matricula, String taller) {
		boolean correcto = false;
		Connection conn;
		try {
			PreparedStatement sentencia = null;
			ResultSet respuesta;
			String consulta= "SELECT * FROM REPARAR WHERE matricula = ? and taller = ? ";
			conn = DriverManager.getConnection(url, usuario, contra);
			if(conn != null) {
				sentencia = conn.prepareStatement(consulta);
				sentencia.setString(1, matricula);
				sentencia.setString(2, taller);
				respuesta = sentencia.executeQuery();
				if(respuesta.next()) {
					correcto = true;
				}
				
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
	
	public static String[] cargarReparaciones() {
		String [] reparaciones = null; 
		Connection conn;
		try {
			
			Statement sentencia = null;
			ResultSet respuesta = null;
			conn = DriverManager.getConnection(url, usuario, contra);
			if(conn != null) {
				sentencia = conn.createStatement();
				String consulta= "select taller, matricula from reparar ";
				respuesta = sentencia.executeQuery(consulta);
				ArrayList<String> rep = new ArrayList<String>();
				while(respuesta.next()) {
					
					String taller = respuesta.getString(1);
					String matricula = respuesta.getString(2);
					String mensaje = "Matricula: "+matricula+" Taller: "+taller;
					rep.add(mensaje);
				}
				reparaciones = rep.toArray(new String[0]);
				
			}else {
				System.out.println("Error conex");
			}
			conn.close();	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return reparaciones;
	}
	
	public static boolean eliminarReparacionesXMatricula(String matricula) {
		boolean correcto = false;
		Connection conn;
		try {
			String consulta = "DELETE FROM REPARAR WHERE matricula = ?";
			PreparedStatement sentencia = null;
			conn = DriverManager.getConnection(url, usuario, contra);
			if(conn != null) {
				sentencia = conn.prepareStatement(consulta);
				sentencia.setString(1, matricula);
				int fila = sentencia.executeUpdate();
				if(fila > 0) {
					correcto = true;
				}
			}else {
				System.out.println("Error conex");
			}
			conn.close();	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return correcto;
	}
	
	public static Object[] cargarIncidenciasMT(String matricula, String taller) {
		Object [] incidencias = null; 
		Connection conn;
		try {
			
			PreparedStatement sentencia = null;
			ResultSet respuesta;
			String consulta= "SELECT * FROM INCIDENCIA WHERE matricula = ? and taller = ? ORDER BY cod_incidencia ASC";
			conn = DriverManager.getConnection(url, usuario, contra);
			if(conn != null) {
				sentencia = conn.prepareStatement(consulta);
				sentencia.setString(1, matricula);
				sentencia.setString(2, taller);
				respuesta = sentencia.executeQuery();
				ArrayList<Incidencia> inci = new ArrayList<Incidencia>();
				while(respuesta.next()) {
					String cod = respuesta.getString(1);
					String descripcion = respuesta.getString(2);
					boolean resuelto = respuesta.getBoolean(5);
					Incidencia i = new Incidencia(cod, descripcion, taller, matricula, resuelto);
					inci.add(i);
				}
				incidencias = inci.toArray();
				
			}else {
				System.out.println("Error conex");
			}
			conn.close();	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return incidencias;
	}
	
	public static Incidencia cargarIncidenciaCI(String codigo) {
		Incidencia incidencia = null; 
		Connection conn;
		try {
			
			PreparedStatement sentencia = null;
			ResultSet respuesta;
			String consulta= "SELECT * FROM INCIDENCIA WHERE cod_incidencia = ? ";
			conn = DriverManager.getConnection(url, usuario, contra);
			if(conn != null) {
				sentencia = conn.prepareStatement(consulta);
				sentencia.setString(1, codigo);
				respuesta = sentencia.executeQuery();
				ArrayList<Incidencia> inci = new ArrayList<Incidencia>();
				while(respuesta.next()) {
					String cod = respuesta.getString(1);
					String descripcion = respuesta.getString(2);
					String taller = respuesta.getString(3);
					String matricula = respuesta.getString(4);
					boolean resuelto = respuesta.getBoolean(5);
					Incidencia i = new Incidencia(cod, descripcion, taller, matricula, resuelto);
					incidencia = i;
				}
				
			}else {
				System.out.println("Error conex");
			}
			conn.close();	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return incidencia;
	}
	public static Object[] cargarIncidencias() {
		Object [] incidencias = null; 
		Connection conn;
		try {
			
			PreparedStatement sentencia = null;
			ResultSet respuesta;
			String consulta= "SELECT * FROM INCIDENCIA ORDER BY cod_incidencia ASC";
			conn = DriverManager.getConnection(url, usuario, contra);
			if(conn != null) {
				sentencia = conn.prepareStatement(consulta);
				respuesta = sentencia.executeQuery();
				ArrayList<Incidencia> inci = new ArrayList<Incidencia>();
				while(respuesta.next()) {
					String cod = respuesta.getString(1);
					String descripcion = respuesta.getString(2);
					String taller = respuesta.getString(3);
					String matricula = respuesta.getString(4);
					boolean resuelto = respuesta.getBoolean(5);
					Incidencia i = new Incidencia(cod, descripcion, taller, matricula, resuelto);
					inci.add(i);
				}
				incidencias = inci.toArray();
				
			}else {
				System.out.println("Error conex");
			}
			conn.close();	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return incidencias;
	}
	
	public static boolean eliminarIncidenciasXMatricula(String matricula) {
		boolean correcto = false;
		Connection conn;
		try {
			String consulta = "DELETE FROM INCIDENCIA WHERE matricula = ?";
			PreparedStatement sentencia = null;
			conn = DriverManager.getConnection(url, usuario, contra);
			if(conn != null) {
				sentencia = conn.prepareStatement(consulta);
				sentencia.setString(1, matricula);
				int fila = sentencia.executeUpdate();
				if(fila > 0) {
					correcto = true;
				}
			}else {
				System.out.println("Error conex");
			}
			conn.close();	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return correcto;
	}
	
	public static boolean InsertarIncidencia(String mensaje, String taller, String matricula) {
		boolean correcto = false;
		Connection conn;
		try {
			String consulta = "SELECT insertarIncidencia(?, ?, ?)";
			PreparedStatement sentencia = null;
			conn = DriverManager.getConnection(url, usuario, contra);
			if(conn != null) {
				sentencia = conn.prepareStatement(consulta);
				sentencia.setString(1, mensaje);
				sentencia.setString(2, taller);
				sentencia.setString(3, matricula);
				correcto = sentencia.execute();
				
			}else {
				System.out.println("Error conex");
			}
			conn.close();	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return correcto;
	}
	
	public static boolean modificarIncidencia(Incidencia i) {
		boolean correcto = false;
		Connection conn;
		try {
			String consulta = "UPDATE INCIDENCIA SET descripcion = ?, taller = ?, matricula = ?, resuelto = ? WHERE cod_incidencia = ?";
			PreparedStatement sentencia = null;
			conn = DriverManager.getConnection(url, usuario, contra);
			if(conn != null) {
				sentencia = conn.prepareStatement(consulta);
				sentencia.setString(1, i.getDescripcion());
				sentencia.setString(2, i.getTaller());
				sentencia.setString(3, i.getMatricula());
				sentencia.setBoolean(4, i.isResuelto());
				sentencia.setString(5, i.getCod_incidencia());
				int fila = sentencia.executeUpdate();
				if(fila > 0) {
					correcto = true;
				}
			}else {
				System.out.println("Error conex");
			}
			conn.close();	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return correcto;
	}
	
	
	
	
//Eliminaremos las tablas que no vamos a utilizar ya que hay claves FK que impiden eliminaciones en clinetes y Vehiculos	
	public static void eliminarSuminsitros() {
		Connection conn;
		try {
			String consulta = "DELETE FROM SUMINISTRA";
			PreparedStatement sentencia = null;
			conn = DriverManager.getConnection(url, usuario, contra);
			if(conn != null) {
				sentencia = conn.prepareStatement(consulta);
				int fila = sentencia.executeUpdate();
			}else {
				System.out.println("Error conex");
			}
			conn.close();	
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void eliminarTrabajaCon() {
		Connection conn;
		try {
			String consulta = "DELETE FROM TRABAJA_CON";
			PreparedStatement sentencia = null;
			conn = DriverManager.getConnection(url, usuario, contra);
			if(conn != null) {
				sentencia = conn.prepareStatement(consulta);
				int fila = sentencia.executeUpdate();
			}else {
				System.out.println("Error conex");
			}
			conn.close();	
		}catch(SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	
}
