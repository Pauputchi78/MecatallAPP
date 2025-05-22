package main.Vista;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Controlador.conexionBDD;
import main.Controlador.gestorClases;
import main.Controlador.principal;
import main.Modelo.Cliente;

import javax.swing.JToolBar;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Inicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inicio frame = new Inicio();
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
	public Inicio() {
		setIconImage(principal.logoMecatall(65, 65).getImage());
		setResizable(false);
		setTitle("Inicio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 352, 388);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(248, 208, 154));
		panel.setBounds(0, 0, 336, 349);
		contentPane.add(panel);
		panel.setLayout(null);
		
		
		
		JLabel lblLogo = new JLabel(principal.logoMecatall(200, 200));
		lblLogo.setBounds(60, 130, 206, 187);
		panel.add(lblLogo);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(255, 255, 255));
		menuBar.setBounds(0, 0, 336, 34);
		panel.add(menuBar);
		
		JMenu mnNewMenu = new JMenu("Clientes");
		mnNewMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				principal.mostrarVentana("Clientes", true);
				if(gestorClases.clientescargados == false) {
					conexionBDD.cargarClinetesBDD();
					gestorClases.clientescargados = true;
				}
			}
		});
		
		menuBar.add(mnNewMenu);
		
		JMenu mnNewMenu_1 = new JMenu("Vehiculos");
		mnNewMenu_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				principal.mostrarVentana("Vehiculos", true);
				if(gestorClases.clientescargados == false) {
					conexionBDD.cargarClinetesBDD();
					gestorClases.clientescargados = true;
				}
				
			}
		});
		menuBar.add(mnNewMenu_1);
		
		JMenu mnNewMenu_2 = new JMenu("Reparaciones");
		mnNewMenu_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				principal.mostrarVentana("VistaReparaciones", true);
				setVisible(false);
			}
		});
		menuBar.add(mnNewMenu_2);
		
		JMenu mnNewMenu_3 = new JMenu("Incidencias");
		mnNewMenu_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				principal.mostrarVentana("Incidencias", true);
				setVisible(false);
			}
		});
		menuBar.add(mnNewMenu_3);
		
		JLabel lblNewLabel = new JLabel("MECATALL APP");
		lblNewLabel.setFont(new Font("Segoe Print", Font.BOLD, 21));
		lblNewLabel.setBounds(79, 45, 196, 30);
		panel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Cargar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ruta = principal.obtenerRuta();
				if(ruta == null){
					System.out.println("Operación cancelada");
				}else {
					conexionBDD.cargarClinetesBDD();
					principal.cargarTextoPlano(ruta);
					
				}
			}
		});
		btnNewButton.setBounds(10, 96, 129, 23);
		panel.add(btnNewButton);
		
		JButton btnGuardar = new JButton("Exportar\r");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ruta = principal.obtenerRuta();
				if(ruta == null){
					System.out.println("Operación cancelada");
				}else {
					conexionBDD.cargarClinetesBDD();
					for(Cliente c : gestorClases.clientes) {
						conexionBDD.cargarVehiculos(c);
					}
					principal.guardarTextoPlanoCliVehicu(ruta);
					
				}
			}
		});
		btnGuardar.setBounds(186, 96, 129, 23);
		panel.add(btnGuardar);
	}
}
