package Controlador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JFrame;

import Modelo.Cliente;
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
	
	

}
