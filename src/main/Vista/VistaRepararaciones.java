package main.Vista;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.Controlador.conexionBDD;
import main.Controlador.gestorClases;
import main.Controlador.principal;
import main.Modelo.Cliente;
import main.Modelo.Reparar;
import main.Modelo.Vehiculo;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class VistaRepararaciones extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldfecha;
	String [] matriculas;
	String [] talleres;
	private JComboBox cbMatricula;
	private JComboBox cbTaller;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaRepararaciones frame = new VistaRepararaciones();
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
	public VistaRepararaciones() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				principal.mostrarVentana("Inicio", true);
				setVisible(false);
				
			}
			@Override
			public void windowActivated(WindowEvent e) {
				principal.mostrarVentana("Inicio", false);
				cargarDatos();
			}
		});
		setTitle("VistaReparaciones");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 293, 333);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 277, 294);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblMatricula = new JLabel("Matricula");
		lblMatricula.setBounds(31, 57, 60, 14);
		panel.add(lblMatricula);
		
		JLabel lblTaller = new JLabel("Taller:");
		lblTaller.setBounds(31, 90, 60, 14);
		panel.add(lblTaller);
		
		JLabel lblTel = new JLabel("Fecha:");
		lblTel.setBounds(31, 119, 60, 14);
		panel.add(lblTel);
		
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
		textFieldfecha.setColumns(10);
		textFieldfecha.setBounds(101, 116, 132, 20);
		panel.add(textFieldfecha);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 277, 22);
		panel.add(menuBar);
		
		JMenu mnNewMenu_1 = new JMenu("Inicio");
		mnNewMenu_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				principal.mostrarVentana("Inicio", true);
				setVisible(false);
			}
		});
		menuBar.add(mnNewMenu_1);
		
		JButton btnNewButton = new JButton("Añadir Reparación");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String matri = (String) cbMatricula.getSelectedItem();
				String tall = (String) cbTaller.getSelectedItem();
				boolean existe = conexionBDD.comprobarReparacion(matri, tall);
				if(existe == true) {
					JOptionPane.showMessageDialog(VistaRepararaciones.this, "La reparación ya está asignada.","VEHICULO EN TALLER",JOptionPane.INFORMATION_MESSAGE);
				}else {
					boolean correcto = false;
					LocalDate fecha = null;
					try {
			            fecha = LocalDate.parse(textFieldfecha.getText()); 
			            correcto = true;
			        } catch (DateTimeParseException error) {
			            correcto = false;
			        }
					if(correcto == true) {
						Reparar r = new Reparar(matri,tall,fecha);
						if(conexionBDD.insertarReparacion(r) == true) {
							JOptionPane.showMessageDialog(VistaRepararaciones.this, "La reparación se ha insertado correctamente", "REPARACIÓN INSERTADA",JOptionPane.INFORMATION_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(VistaRepararaciones.this, "La fecha insertada no es valida", "FECHA NO VALIDA",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnNewButton.setBounds(53, 162, 166, 23);
		panel.add(btnNewButton);
		
		cbMatricula = new JComboBox();
		
		
		
		cbMatricula.setBounds(101, 53, 132, 22);
		panel.add(cbMatricula);
		
		cbTaller = new JComboBox();
		cbTaller.setBounds(101, 86, 132, 22);
		panel.add(cbTaller);
		
		JButton btnTodasLasIncidencias = new JButton("Todas las Incidencias");
		btnTodasLasIncidencias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				principal.mostrarVentana("Incidencias", true);
				setVisible(false);
			}
		});
		btnTodasLasIncidencias.setBounds(53, 206, 166, 23);
		panel.add(btnTodasLasIncidencias);
	}
	public void cargarDatos() {
		matriculas = conexionBDD.cargarMatriculas();
		cbMatricula.setModel(new DefaultComboBoxModel(matriculas));
		talleres = conexionBDD.cargarTaller();
		cbTaller.setModel(new DefaultComboBoxModel(talleres));
	}
	
}
