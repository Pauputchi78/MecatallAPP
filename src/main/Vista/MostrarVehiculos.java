package main.Vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import main.Controlador.conexionBDD;
import main.Controlador.gestorClases;
import main.Controlador.principal;
import main.Modelo.Cliente;
import main.Modelo.Vehiculo;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MostrarVehiculos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tmodel;


	public MostrarVehiculos(JFrame padre , Cliente c) {
		setResizable(false);
		setTitle("Mostrar Vehiculos");
		setIconImage(principal.logoMecatall(65, 65).getImage());
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
		setBounds(100, 100, 689, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 653, 309);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 653, 257);
		panel.add(scrollPane);
		
		
		
		table = new JTable(tmodel);
		table.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(table);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() == -1) {
					System.out.println("NO seleccionado");
				}else {
					int select = table.getSelectedRow();
					Object matricula = table.getValueAt(select, 0);
					String mensaje = "Esta seguro que desa eliminar el Vehiculo "+ matricula.toString() + "? \n Se eliminaran sus reparaciones e incidencias.";
					int respuesta = JOptionPane.showConfirmDialog(MostrarVehiculos.this, mensaje,"Eliminar Vehiculo",JOptionPane.YES_NO_OPTION);
					if(respuesta == JOptionPane.YES_OPTION) {
						
						conexionBDD.eliminarSuminsitros();
						conexionBDD.eliminarIncidenciasXMatricula(matricula.toString());
						conexionBDD.eliminarReparacionesXMatricula(matricula.toString());
						boolean Elivehi = conexionBDD.eliminarVehiculoMatricula(matricula.toString());
						if(Elivehi) {
							tmodel.removeRow(select);
							
							JOptionPane.showMessageDialog(MostrarVehiculos.this, "El vehiculo se ha eliminado", "CORRECTO", JOptionPane.INFORMATION_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(MostrarVehiculos.this, "Error al eliminar el vehiculo", "ERROR", JOptionPane.ERROR_MESSAGE);
						}
					}else {
						
					}
					
				}
			}
		});
		btnEliminar.setBounds(480, 275, 101, 23);
		panel.add(btnEliminar);
		
		JButton btnNewButton = new JButton("Exportar Vehiculos JSON");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ruta = principal.obtenerRuta();
				if(ruta == null) {
					JOptionPane.showMessageDialog(MostrarVehiculos.this, "Operación cancelada", "CANCELADO", JOptionPane.INFORMATION_MESSAGE);
				}else {
					principal.guardarTextoJSON(ruta);
				}
			}
		});
		btnNewButton.setBounds(113, 275, 186, 23);
		panel.add(btnNewButton);
		
		
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
