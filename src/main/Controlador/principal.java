package main.Controlador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import main.Modelo.Cliente;
import main.Modelo.Vehiculo;
import main.Vista.Inicio;
import main.Vista.VentanaClientes;
import main.Vista.VentanaVehiculos;


public class principal {
	public static ArrayList<Cliente> clientes = new ArrayList<Cliente>();
	public static ArrayList<JFrame> ventanas = new ArrayList<JFrame>();
	public static String url = "jdbc:postgresql://localhost:5432/Mecatall";
	public static String usuario = "postgres";
	public static String contra = "postgres";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ventanas.add(new Inicio());
		ventanas.get(0).setVisible(true);

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
			}
		}
	}
	
	public static int buscarClientePorDNI(String dni) {
		int index = -1;
			for(int i = 0; i<clientes.size();i++) {
				if(clientes.get(i).getNif().equalsIgnoreCase(dni)) {
					index = i;
				}
			}
		
		return index;
	}
	public static int buscarClientePorNombre(String nombre) {
		int index = -1;
		for(int i = 0; i<clientes.size();i++) {
			if(clientes.get(i).getNombre().equalsIgnoreCase(nombre)) {
				index = i;
			}
		}
	
	return index;
	}
	
	public static boolean validarMatricula(String matricula) {
		for(int i = 0;i<clientes.size();i++) {
			Cliente c = clientes.get(i);
			for(int j = 0; j<c.Vehiculos.size();j++) {
				Vehiculo v = c.Vehiculos.get(j);
				if(matricula.equalsIgnoreCase(v.getMatricula())) {
					return false;
				}
			}
		}
		
		return true;
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
	
	public static void guardarTextoPlano(String ruta) {
		File f;
		FileWriter fw;
		BufferedWriter bw;
		
		
		try {
			f = new File(ruta);
			fw = new FileWriter(f);
			bw = new BufferedWriter(fw);
			for(Cliente c : clientes) {
				bw.write(c.toString());
				bw.newLine();
			}
			bw.flush();
			bw.close();
		}catch(Exception e) {
			
		}
	}
	
	public static void cargarTextoPlano(String ruta) {
		File f;
		FileReader fr;
		BufferedReader br;
		Boolean correcto = false;
		ArrayList<Cliente> clientes2 = new ArrayList<Cliente>();
		try {
			f = new File(ruta);
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			String leer = "";
			int contadorcli = -1;
			while((leer = br.readLine())!=null) {
				String [] datos = leer.split("-");
				if(datos[0].equalsIgnoreCase("Cliente")) {
					String nif = datos[1];
					String nombre = datos[2];
					int tel = Integer.parseInt(datos[3]);
					String direc = datos[4];
					String ciudad = datos[5];
					Cliente c = new Cliente(nif,nombre,tel,direc,ciudad);
					clientes2.add(c);
					contadorcli = contadorcli +1;
				}else if(datos[0].equalsIgnoreCase("Vehiculo")) {
					String matricula = datos[1];
					String color = datos[2];
					String modelo = datos[3];
					int aniomatri = Integer.parseInt(datos[4]);
					Vehiculo v = new Vehiculo(matricula,color,modelo,aniomatri);
					clientes2.get(contadorcli).Vehiculos.add(v);
				}
				
			}
			fr.close();
			br.close();
			correcto = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		if(correcto = true) {
			clientes.clear();
			clientes.addAll(clientes2);
			JOptionPane.showMessageDialog(null, "Se ha cargado correctametne","DATOS CARGADOS",JOptionPane.INFORMATION_MESSAGE);
		}else {
			System.out.println("No cargado");
		}
	}
	
	

}
