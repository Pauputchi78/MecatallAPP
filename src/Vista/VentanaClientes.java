package Vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controlador.principal;
import Modelo.Cliente;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import java.awt.Color;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenu;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VentanaClientes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldtel;
	private JTextField textFielddire;
	private JTextField textFieldCiudad;
	private JTextField textFieldnombre;
	private JTextField textFieldnif;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaClientes frame = new VentanaClientes();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaClientes() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 367, 279);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 351, 240);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("NIF:");
		lblNewLabel.setBounds(31, 51, 60, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(31, 76, 60, 14);
		panel.add(lblNombre);
		
		JLabel lblTel = new JLabel("Telefono:");
		lblTel.setBounds(31, 101, 60, 14);
		panel.add(lblTel);
		
		JLabel lblDireccion = new JLabel("Dirección");
		lblDireccion.setBounds(31, 126, 60, 14);
		panel.add(lblDireccion);
		
		JLabel lblCiudad = new JLabel("Ciudad");
		lblCiudad.setBounds(31, 151, 60, 14);
		panel.add(lblCiudad);
		
		textFieldnif = new JTextField();
		textFieldnif.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(textFieldnif.getText().length()>8) {
					e.consume();
				}
			}
		});
		textFieldnif.setBounds(101, 48, 212, 20);
		panel.add(textFieldnif);
		textFieldnif.setColumns(10);
		
		textFieldnombre = new JTextField();
		textFieldnombre.setColumns(10);
		textFieldnombre.setBounds(101, 73, 212, 20);
		panel.add(textFieldnombre);
		
		textFieldtel = new JTextField();
		textFieldtel.setColumns(10);
		textFieldtel.setBounds(101, 98, 212, 20);
		panel.add(textFieldtel);
		
		textFielddire = new JTextField();
		textFielddire.setColumns(10);
		textFielddire.setBounds(101, 123, 212, 20);
		panel.add(textFielddire);
		
		textFieldCiudad = new JTextField();
		textFieldCiudad.setColumns(10);
		textFieldCiudad.setBounds(101, 148, 212, 20);
		panel.add(textFieldCiudad);
		
		JButton btnNewButton_1 = new JButton("Mostrar Vehículos");
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.setBounds(101, 179, 212, 23);
		panel.add(btnNewButton_1);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 351, 22);
		panel.add(menuBar);
		
		JMenu mnNewMenu_1 = new JMenu("Inicio");
		menuBar.add(mnNewMenu_1);
		
		JMenu mnNewMenu = new JMenu("Cliente");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Nuevo");
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Buscar");
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Añadir");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dni = textFieldnif.getText().trim();
				String nombre = textFieldnombre.getText();
				String tel = textFieldtel.getText().trim();
				String direc = textFielddire.getText();
				String ciudad = textFieldCiudad.getText();
				if(dni.isEmpty()|| nombre.trim().isEmpty()||tel.isEmpty()|| direc.trim().isEmpty()|| ciudad.trim().isEmpty()) {
					System.out.println("Rellene campos vacios");
				}else {
					if(verificarDNI(dni)== false) {
						System.out.println("DNI no valido");
					}else {
						int telefono = Integer.parseInt(tel);
						Cliente p = new Cliente(dni, nombre, telefono, direc, ciudad);
						principal.clientes.add(p);
					}
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Eliminar");
		mnNewMenu.add(mntmNewMenuItem_3);
		
		JMenu mnNewMenu_2 = new JMenu("Ver Todos");
		menuBar.add(mnNewMenu_2);
	}
	
	public boolean verificarDNI(String dni) {
		boolean correcto = true;
		for(Cliente c : principal.clientes) {
			if(c.getNif().equalsIgnoreCase(dni)== true) {
				System.out.println("Ya existe un clinete con este nif");
				correcto = false;
			}
		}
		
		if(dni.length()==9 && correcto == true) {
			try {
				int ndni = Integer.parseInt(dni.substring(0, 8));
				char[] letras = {'T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E' };
				String compro = ""+ndni + letras[ndni%23];
				if(compro.equalsIgnoreCase(dni)) {
					return true;
				}
				
			}catch(Exception e) {
				System.out.println("Dni no valido");
			}
		}
		return false;
	}
	

	
}
