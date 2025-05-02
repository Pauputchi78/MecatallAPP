package Controlador;

import java.util.ArrayList;

import javax.swing.JFrame;

import Modelo.Cliente;
import Vista.VentanaClientes;

public class principal {
	public static ArrayList<Cliente> clientes = new ArrayList<Cliente>();
	public static ArrayList<JFrame> ventanas = new ArrayList<JFrame>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ventanas.add(new VentanaClientes());
		ventanas.get(0).setVisible(true);

	}

}
