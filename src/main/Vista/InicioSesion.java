package main.Vista;

import java.awt.EventQueue;
import java.awt.Label;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Controlador.conexionBDD;
import main.Controlador.principal;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;

import javax.swing.JPasswordField;
import java.awt.Color;

public class InicioSesion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JLabel lblMensaje;
	private JLabel lblBienvenidoAMecatall;
	private JLabel lblInicioDeSesin;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InicioSesion frame = new InicioSesion();
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
	public InicioSesion() {
		setIconImage(principal.logoMecatall(65, 65).getImage());
		setForeground(new Color(248, 208, 154));
		setTitle("Inicio de Sesión");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 335, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 208, 154));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(248, 208, 154));
		panel.setBounds(10, 11, 299, 239);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Usuario");
		lblNewLabel.setBounds(32, 85, 82, 14);
		panel.add(lblNewLabel);
		
		JLabel lblContra = new JLabel("Contraseña");
		lblContra.setBounds(32, 119, 82, 14);
		panel.add(lblContra);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(115, 82, 141, 20);
		panel.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JButton btnAceptar = new JButton("ACEPTAR");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuario = txtUsuario.getText();
				char[] passwordChars = passwordField.getPassword();
				String contra = new String(passwordChars);
				
				boolean conect = conexionBDD.validarConexion(usuario, contra);
				if(conect == true) {
					lblMensaje.setText("Conexión exitosa");
					lblMensaje.setVisible(true);
					principal.mostrarVentana("Inicio", true);
					setVisible(false);
				}else {
					lblMensaje.setText("Acceso denegado");
					lblMensaje.setVisible(true);
				}
				
			
						
			}
		});
		btnAceptar.setBounds(99, 159, 89, 23);
		panel.add(btnAceptar);
		
		lblMensaje = new JLabel("");
		lblMensaje.setVisible(false);
		lblMensaje.setBounds(71, 204, 141, 14);
		panel.add(lblMensaje);
		
		lblBienvenidoAMecatall = new JLabel("BIENVENIDO A MECATALL");
		lblBienvenidoAMecatall.setFont(new Font("Segoe Print", Font.BOLD, 18));
		lblBienvenidoAMecatall.setBounds(21, 0, 257, 30);
		panel.add(lblBienvenidoAMecatall);
		
		lblInicioDeSesin = new JLabel("Inicio de sesión");
		lblInicioDeSesin.setFont(new Font("MV Boli", Font.BOLD, 14));
		lblInicioDeSesin.setBounds(82, 41, 128, 20);
		panel.add(lblInicioDeSesin);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(115, 116, 141, 20);
		panel.add(passwordField);
		
		ImageIcon logo = new ImageIcon(getClass().getResource("/Imagenes/LogoMecatall.png"));
		Image rescalada = logo.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon anchonew = new ImageIcon(rescalada);
	}
}
