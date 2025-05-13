package main.Vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DateFormatter;

import main.Controlador.conexionBDD;
import main.Controlador.gestorClases;
import main.Controlador.principal;
import main.Modelo.Cliente;
import main.Modelo.Vehiculo;

import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSpinner;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VentanaVehiculos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNIF;
	private JTextField textFieldNom;
	private JTextField textFieldMatricula;
	private JTextField textFieldModelo;
	private JTextField textFieldColor;
	private JTextField textFieldfecha;

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
		
		JMenu mnNewMenu_1 = new JMenu("Insertar Vehiculo");
		mnNewMenu_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int posi = gestorClases.buscarClientePorDNI(textFieldNIF.getText());
				if(posi != -1) {
					String matricula = textFieldMatricula.getText();
					String modelo = textFieldModelo.getText();
					String color = textFieldColor.getText();
					String fechamatri = textFieldfecha.getText().trim();
					LocalDate fecham = null;
					if(matricula.isEmpty() || modelo.isEmpty()|| color.isEmpty()|| fechamatri.isEmpty()) {
						JOptionPane.showMessageDialog(VentanaVehiculos.this, "Rellene todos los campos", "CAMPOS  VACIOS",JOptionPane.ERROR_MESSAGE);
					}else {
						boolean correcto = false;
						try {
				            fecham = LocalDate.parse(fechamatri); 
				            correcto = true;
				        } catch (DateTimeParseException error) {
				            correcto = false;
				        }
						if(correcto == true) {
							if(conexionBDD.comprobarMatricula(matricula) == false) {
								Cliente c = gestorClases.clientes.get(posi);
								Vehiculo v = new Vehiculo(matricula,color,modelo,fecham);
								c.Vehiculos.add(v);
								conexionBDD.insertarVehiculo(v, c.getNif());
								JOptionPane.showMessageDialog(VentanaVehiculos.this, "El vehiculo ha sido insertado correctamente", "Vehiculo Insertado",JOptionPane.INFORMATION_MESSAGE);
								vaciarCampos();
							}else {
								JOptionPane.showMessageDialog(VentanaVehiculos.this, "La matricula ya esta asignada a una clinete", "Matricula Registrada",JOptionPane.ERROR_MESSAGE);
							}
						}else {
							System.out.println("Fecha no valida");
						}
						
						
					}
				}else {
					JOptionPane.showMessageDialog(VentanaVehiculos.this,"No se ha encontrado el NIF "+ textFieldNIF.getText(),"NO ENCONTRADO",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		menuBar.add(mnNewMenu_1);
		
		JMenu mnNewMenu_3 = new JMenu("Mostrar Vehiculos");
		menuBar.add(mnNewMenu_3);
		
		textFieldNIF = new JTextField();
		textFieldNIF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(textFieldNIF.getText().length() >= 9) {
					e.consume();
				}
			}
		});
		textFieldNIF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int posi = gestorClases.buscarClientePorDNI(textFieldNIF.getText());
				if(posi != -1) {
					Cliente c = gestorClases.clientes.get(posi);
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
					Cliente c = gestorClases.clientes.get(clisele);
					textFieldNIF.setText(c.getNif());
					textFieldNom.setText(c.getNombre());
				}
			}
		});
		btnNewButton.setBounds(263, 50, 124, 23);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("MATRICULA");
		lblNewLabel_1.setBounds(105, 100, 77, 14);
		panel.add(lblNewLabel_1);
		
		textFieldMatricula = new JTextField();
		textFieldMatricula.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String texto = textFieldMatricula.getText();
				if(texto.length() >=7) {
					e.consume();
				}
			}
		});
		textFieldMatricula.setColumns(10);
		textFieldMatricula.setBounds(192, 100, 124, 20);
		panel.add(textFieldMatricula);
		
		JLabel lblNewLabel_1_1 = new JLabel("MODELO");
		lblNewLabel_1_1.setBounds(105, 125, 60, 14);
		panel.add(lblNewLabel_1_1);
		
		textFieldModelo = new JTextField();
		textFieldModelo.setColumns(10);
		textFieldModelo.setBounds(192, 125, 124, 20);
		panel.add(textFieldModelo);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("COLOR");
		lblNewLabel_1_1_1.setBounds(105, 150, 46, 14);
		panel.add(lblNewLabel_1_1_1);
		
		textFieldColor = new JTextField();
		textFieldColor.setColumns(10);
		textFieldColor.setBounds(192, 150, 124, 20);
		panel.add(textFieldColor);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("AÑO MATRICULACIÓN");
		lblNewLabel_1_1_1_1.setBounds(144, 185, 133, 14);
		panel.add(lblNewLabel_1_1_1_1);
		
		textFieldfecha = new JTextField();
		textFieldfecha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
			    String fecha = textFieldfecha.getText();

			    if (Character.isDigit(c) == false && c != '-') {
			        e.consume();
			        return;
			    }

			    if (textFieldfecha.getText().length() > 9) {
			        e.consume();
			        return;
			    }

			    if (Character.isDigit(c)) {
			        if (textFieldfecha.getText().length() == 3 || textFieldfecha.getText().length() == 6) {
			            textFieldfecha.setText(fecha + c + "-");
			            e.consume();
			        }
			    }
			}
		});
		textFieldfecha.setBounds(154, 210, 86, 20);
		panel.add(textFieldfecha);
		textFieldfecha.setColumns(10);
		
		
		
	}
	
	public void vaciarCampos() {
		textFieldNIF.setText("");
		textFieldNom.setText("");
		textFieldMatricula.setText("");
		textFieldModelo.setText("");
		textFieldColor.setText("");
		textFieldfecha.setText("");
	}
	
}
