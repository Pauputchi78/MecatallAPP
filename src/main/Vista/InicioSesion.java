package main.Vista;

import java.awt.EventQueue;
import java.awt.Label;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Controlador.principal;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class InicioSesion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JTextField txtContra;
	private JLabel lblMensaje;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 284, 239);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Usuario");
		lblNewLabel.setBounds(33, 50, 65, 14);
		panel.add(lblNewLabel);
		
		JLabel lblContra = new JLabel("Contraseña");
		lblContra.setBounds(33, 84, 65, 14);
		panel.add(lblContra);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(120, 47, 117, 20);
		panel.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		txtContra = new JTextField();
		txtContra.setColumns(10);
		txtContra.setBounds(120, 81, 117, 20);
		panel.add(txtContra);
		
		JButton btnAceptar = new JButton("ACEPTAR");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuario = txtUsuario.getText();
				String pasw = txtContra.getText();
				try {
					Connection conn = DriverManager.getConnection(principal.url, usuario, pasw);
					if(conn != null) {
						lblMensaje.setText("Conexión exitosa");
					}
				}catch(SQLException error) {
					lblMensaje.setText("Usuario o contraseña no valida");
				}
						
			}
		});
		btnAceptar.setBounds(95, 135, 89, 23);
		panel.add(btnAceptar);
		
		lblMensaje = new JLabel("New label");
		lblMensaje.setBounds(73, 193, 141, 14);
		panel.add(lblMensaje);
	}
}
