package main.Controlador;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.management.remote.JMXConnectorFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import main.Modelo.Cliente;
import main.Modelo.Vehiculo;
import main.Vista.*;


public class principal {
	
	public static ArrayList<JFrame> ventanas = new ArrayList<JFrame>();
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ventanas.add(new InicioSesion());
		ventanas.get(0).setVisible(true);

	}
	
	public static ImageIcon logoMecatall(int alto, int ancho) {
		URL imageUrl = principal.class.getResource("/Imagenes/LogoMecatall.png");
		ImageIcon logo = new ImageIcon(imageUrl);
		Image rescalada = logo.getImage().getScaledInstance(alto, ancho, Image.SCALE_SMOOTH);
		ImageIcon anchonew = new ImageIcon(rescalada);
		
		return anchonew;
	}
	
	public static void mostrarVentana(String nombre, boolean visibilidad) {
		boolean creada = false;
		for(JFrame v : ventanas) {
			if(v.getTitle().equalsIgnoreCase(nombre)) {
				v.setVisible(visibilidad);
				creada = true;
			}
		}
		if(creada == false) {
			if(nombre.equalsIgnoreCase("Clientes")) {
				JFrame v = new VentanaClientes();
				v.setVisible(visibilidad);
				ventanas.add(v);
			}else if(nombre.equalsIgnoreCase("Vehiculos")) {
				JFrame v = new VentanaVehiculos();
				v.setVisible(visibilidad);
				ventanas.add(v);
			}else if(nombre.equalsIgnoreCase("Inicio")) {
				JFrame v = new Inicio();
				v.setVisible(visibilidad);
				ventanas.add(v);
			}else if(nombre.equalsIgnoreCase("VistaReparaciones")) {
				JFrame v = new VistaRepararaciones();
				v.setVisible(visibilidad);
				ventanas.add(v);
			}else if(nombre.equalsIgnoreCase("Incidencias")) {
				JFrame v = new VistaIncidencias();
				v.setVisible(visibilidad);
				ventanas.add(v);
			}
		}
	}
	
	public static String obtenerRuta() {
		File f = new File(System.getProperty("user.home")+File.separator+"Desktop");
		JFileChooser j = new JFileChooser(f);
		j.setAcceptAllFileFilterUsed(false);
		j.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int respuesta = j.showOpenDialog(null);
		if(respuesta == JFileChooser.APPROVE_OPTION) {
			return j.getSelectedFile().getAbsolutePath();
		}
		return null;
	}
	
	public static void guardarTextoPlanoCliVehicu(String ruta) {
		File f;
		FileWriter fw;
		BufferedWriter bw;
		
		
		try {
			f = new File(ruta);
			fw = new FileWriter(f);
			bw = new BufferedWriter(fw);
			for(Cliente c : gestorClases.clientes) {
				bw.write(c.toString());
				bw.newLine();
			}
			bw.flush();
			bw.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void guardarTextoJSON(String ruta) {
		File f;
		FileWriter fw;
		BufferedWriter bw;
		
		
		try {
			f = new File(ruta);
			fw = new FileWriter(f);
			bw = new BufferedWriter(fw);
			StringBuilder mensaje = new StringBuilder();
			for(Cliente c : gestorClases.clientes) {
				for(int i = 0; i< c.Vehiculos.size();i++) {
					mensaje.append(c.Vehiculos.get(i).toStringJSON() +",\n");
				}
			}
			String ini = "let datosjs = [\n";
			mensaje.setLength(mensaje.length() - 2);
			ini = ini + mensaje.toString() + "\n];";
			bw.write(ini);
			bw.flush();
			bw.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void cargarTextoPlano(String ruta) {
		File f;
		FileReader fr;
		BufferedReader br;
		int contadorcli = -1;
		int contadorVehi = -1;
		try {
			f = new File(ruta);
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			String leer = "";
			while((leer = br.readLine())!=null) {
				String [] datos = leer.split("_");
				if(datos[0].equalsIgnoreCase("Cliente")) {
					String nif = datos[1];
					String nombre = datos[2];
					int tel = Integer.parseInt(datos[3]);
					String direc = datos[4];
					String ciudad = datos[5];
					Cliente c = new Cliente(nif,nombre,tel,direc,ciudad);
					boolean correct = conexionBDD.insertarCliente(c);
					if(correct == true) {
						gestorClases.clientes.add(c);
						contadorcli = contadorcli +1;
					}
				}else if(datos[0].equalsIgnoreCase("Vehiculo")) {
					String matricula = datos[1];
					String color = datos[2];
					String modelo = datos[3];
					LocalDate aniomatri = LocalDate.parse(datos[4]);
					String nifcliente = datos[5];
					
					int cli = gestorClases.buscarClientePorDNI(nifcliente);
					if(cli == -1) {
						System.out.println("No se ha encontrado el clinete asignado");
					}else {
						Vehiculo v = new Vehiculo(matricula,color,modelo,aniomatri,nifcliente);
						boolean correct = conexionBDD.insertarVehiculo(v);
						if(correct == true) {
							gestorClases.clientes.get(cli).Vehiculos.add(v);
							contadorVehi = contadorVehi +1;
							
						}
					}
					
				}
				
			}
			fr.close();
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		if(contadorcli != -1 || contadorVehi != -1) {
			JOptionPane.showMessageDialog(null, "Se han cargado correctametne Clientes: "+( contadorcli + 1) + " Vehiculos: "+( contadorVehi + 1 ),"INSERCIÓN REALIZADA", JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(null, "Estos datos ya estan en la base de datos","INSERCIÓN NO REALIZADA",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	public static void guardarIncidencia(String codigo, String descripcion, String matricula, String taller, boolean situ, String ruta) {
		File f;
		FileWriter fw;
		BufferedWriter bw;
		try {
			f = new File(ruta);
			fw = new FileWriter(f);
			bw = new BufferedWriter(fw);
			String mensaje = "MECATALL APP\n\n"
					+ "INCIDENCIA NÚMERO : "+ codigo + "\n\n"
					+ "Incidencia del taller número "+ taller + " para el vehículo "+matricula+". \n\n"
					+ "DESCRIPCIÓN DE INCIDENCIA:\n"+descripcion + "\n\n"; 
			if(situ == false) {
				mensaje = mensaje + "La incidencia está pendiente de resolver.";
			}else {
				mensaje = mensaje + "La incidencia ya ha sido solucionada con éxito.";
			}
			bw.write(mensaje);
			bw.flush();
			bw.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
