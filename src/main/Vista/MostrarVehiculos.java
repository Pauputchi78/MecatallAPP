package main.Vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import main.Controlador.conexionBDD;
import main.Controlador.gestorClases;
import main.Modelo.Cliente;
import main.Modelo.Vehiculo;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MostrarVehiculos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tmodel;


	public MostrarVehiculos(JFrame padre , Cliente c) {
		String [] titulo = {"MATRICULA","COLOR","MODELO","AÑO MATRICULACIÓN","NIF CLINETE"};
		tmodel = new DefaultTableModel(titulo,0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		if(c != null) {
			rellenarTablaClinete(c);
		}else {
			rellenarTablaCompleta();
		}
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				padre.setVisible(false);
			}
			@Override
			public void windowClosing(WindowEvent e) {
				padre.setVisible(true);
			}
		});
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 414, 239);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 414, 155);
		panel.add(scrollPane);
		
		
		
		table = new JTable(tmodel);
		scrollPane.setViewportView(table);
		
		
	}
	public void rellenarTablaCompleta() {
		
		tmodel.setRowCount(0);
		for(Cliente c : gestorClases.clientes) {
			conexionBDD.cargarVehiculos(c);
			for(Vehiculo v : c.Vehiculos) {
				Object [] fila = {v.getMatricula(),v.getColor(), v.getModelo(), v.getAniomatri().toString(),c.getNif()};
				tmodel.addRow(fila);
			}
		}
	}
	
	public void rellenarTablaClinete(Cliente c) {
		for(Vehiculo v : c.Vehiculos) {
			Object [] fila = {v.getMatricula(),v.getColor(), v.getModelo(), v.getAniomatri().toString(),c.getNif()};
			tmodel.addRow(fila);
		}
	}
}
