package Controlador;

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

import Modelo.Cliente;
import Modelo.Vehiculo;
import Vista.Inicio;
import Vista.VentanaClientes;
import Vista.VentanaVehiculos;


public class principal {
	public static ArrayList<Cliente> clientes = new ArrayList<Cliente>();
	public static ArrayList<JFrame> ventanas = new ArrayList<JFrame>();

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
	
	

}
