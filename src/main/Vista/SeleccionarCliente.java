package main.Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import main.Controlador.conexionBDD;
import main.Controlador.gestorClases;
import main.Controlador.principal;
import main.Modelo.Cliente;
import main.Modelo.Vehiculo;

import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SeleccionarCliente extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel tmodel;
	private int clisele = -1;


	public SeleccionarCliente(JFrame padre) {
		super(padre, true);
	
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				rellenarTablaconClientes();
				
			}
			@Override
			public void windowClosing(WindowEvent e) {
				padre.setVisible(true);
			}
		});
		setBounds(100, 100, 726, 386);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 710, 314);
		contentPanel.add(scrollPane);
		
		String [] titulo = {"NIF","NOMBRE","TELEFONO","DIRECCIÓN","CIUDAD"};
		tmodel = new DefaultTableModel(titulo,0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(tmodel);
		scrollPane.setViewportView(table);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(table.getSelectedRow()== -1) {
							System.out.println("NO selec");
						}else {
							clisele = table.getSelectedRow();
							dispose();
							padre.setVisible(true);
						}
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
						padre.setVisible(true);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public void rellenarTablaconClientes() {
		
		tmodel.setRowCount(0);
		for(int i = 0; i<gestorClases.clientes.size();i++) {
			Cliente c = gestorClases.clientes.get(i);
			Object [] fila = {c.getNif(),c.getNombre(),c.getTel(),c.getDirec(),c.getCiudad()};
			tmodel.addRow(fila);
		}
	}
	
	public int selectCli() {
		return clisele;
	}
}
