package main.Controlador;

import java.util.ArrayList;

import main.Modelo.Cliente;
import main.Modelo.Vehiculo;

public class gestorClases {
	public static ArrayList<Cliente> clientes = new ArrayList<Cliente>();
	public static boolean clientescargados = false;
	
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
