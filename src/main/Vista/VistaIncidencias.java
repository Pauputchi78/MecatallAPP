package main.Vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import main.Controlador.conexionBDD;
import main.Controlador.principal;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTable;
import main.Modelo.Incidencia;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VistaIncidencias extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tmodel;
	private JComboBox comboBoxrep;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaIncidencias frame = new VistaIncidencias();
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
	public VistaIncidencias() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				principal.mostrarVentana("Inicio", true);
				setVisible(false);
			}
		});
		setTitle("Incidencias");
		
		String [] titulo = {"Cod Incidencia","Descripción","Resuelto"};
		tmodel = new DefaultTableModel(titulo,0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 520, 415);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 484, 354);
		contentPane.add(panel);
		panel.setLayout(null);
		
		String [] reparaciones = conexionBDD.cargarReparaciones();
		comboBoxrep = new JComboBox();
		comboBoxrep.setModel(new DefaultComboBoxModel(reparaciones));
		comboBoxrep.setBounds(118, 11, 233, 22);
		panel.add(comboBoxrep);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 99, 464, 244);
		panel.add(scrollPane);
		
		table = new JTable(tmodel);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					int fila = table.getSelectedRow();
					String cod = (String) tmodel.getValueAt(fila, 0);
					String[] datos = ((String) comboBoxrep.getSelectedItem()).split(" ");
					IMIncidencia i = new IMIncidencia(VistaIncidencias.this, cod, datos[1], datos[3]);
					i.setVisible(true);
					int respuesta = i.responder();
					if(respuesta == 1) {
						Object[] inci = conexionBDD.cargarIncidenciasMT(datos[1], datos[3]);
						cargarTabla(inci);
					}
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Reparacion :");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(21, 12, 104, 18);
		panel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Insertar Incidencia");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] datos = ((String) comboBoxrep.getSelectedItem()).split(" ");
				IMIncidencia i = new IMIncidencia(VistaIncidencias.this, null, datos[1], datos[3]);
				i.setVisible(true);
				int respuesta = i.responder();
				if(respuesta == 1) {
					Object[] inci = conexionBDD.cargarIncidencias();
					cargarTabla(inci);
				}
				
			}
		});
		btnNewButton.setBounds(10, 51, 146, 23);
		panel.add(btnNewButton);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] datos = ((String) comboBoxrep.getSelectedItem()).split(" ");
				Object[] inci = conexionBDD.cargarIncidenciasMT(datos[1], datos[3]);
				cargarTabla(inci);
			}
		});
		btnBuscar.setBounds(172, 51, 146, 23);
		panel.add(btnBuscar);
		
		JButton btnVerTodas = new JButton("Ver Todas");
		btnVerTodas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] inci = conexionBDD.cargarIncidencias();
				cargarTabla(inci);
			}
		});
		btnVerTodas.setBounds(328, 51, 146, 23);
		panel.add(btnVerTodas);
	}
	
	public void cargarTabla(Object[] incidencias) {
		tmodel.setRowCount(0);
		
		for(Object i : incidencias) {
			if(i instanceof Incidencia a) {
				Object [] fila = {a.getCod_incidencia(), a.getDescripcion(), a.isResuelto()};
				tmodel.addRow(fila);
			}
		}
		
	}
}
