package Vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controlador.principal;
import Modelo.Cliente;

import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaVehiculos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNIF;
	private JTextField textFieldNom;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaVehiculos frame = new VentanaVehiculos();
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
	public VentanaVehiculos() {
		setTitle("Vehiculos");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				principal.mostrarVentana("Inicio", true);
			}
			@Override
			public void windowActivated(WindowEvent e) {
				principal.mostrarVentana("Inicio", false);
			}
		});
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 261);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("NIF");
		lblNewLabel.setBounds(29, 40, 46, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNombre = new JLabel("NOMBRE");
		lblNombre.setBounds(29, 65, 60, 14);
		panel.add(lblNombre);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 434, 22);
		panel.add(menuBar);
		
		JMenu mnNewMenu = new JMenu("Inicio");
		mnNewMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				principal.mostrarVentana("Inicio", true);
				setVisible(false);
			}
		});
		menuBar.add(mnNewMenu);
		
		textFieldNIF = new JTextField();
		textFieldNIF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int posi = principal.buscarClientePorDNI(textFieldNIF.getText());
				if(posi != -1) {
					Cliente c = principal.clientes.get(posi);
					textFieldNIF.setText(c.getNif());
					textFieldNom.setText(c.getNombre());
				}else {
					JOptionPane.showMessageDialog(VentanaVehiculos.this,"No se ha encontrado el NIF "+ textFieldNIF.getText(),"NO ENCONTRADO",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		textFieldNIF.setBounds(99, 37, 124, 20);
		panel.add(textFieldNIF);
		textFieldNIF.setColumns(10);
		
		textFieldNom = new JTextField();
		textFieldNom.setEditable(false);
		textFieldNom.setColumns(10);
		textFieldNom.setBounds(99, 62, 124, 20);
		panel.add(textFieldNom);
		
		JButton btnNewButton = new JButton("Buscar Cliente");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SeleccionarCliente sc = new SeleccionarCliente(VentanaVehiculos.this);
				sc.setVisible(true);
				int clisele = sc.selectCli();
				if(clisele != -1) {
					Cliente c = principal.clientes.get(clisele);
					textFieldNIF.setText(c.getNif());
					textFieldNom.setText(c.getNombre());
				}
			}
		});
		btnNewButton.setBounds(263, 50, 124, 23);
		panel.add(btnNewButton);
	}
}
