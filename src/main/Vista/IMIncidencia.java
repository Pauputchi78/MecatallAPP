package main.Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Controlador.conexionBDD;
import main.Modelo.Incidencia;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.JCheckBox;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IMIncidencia extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextPane textPane;
	private JCheckBox chckbxNewCheckBox;
	private JLabel lblTitulo;
	private int respuesta = -1;

	/**
	 * @wbp.parser.constructor
	 */
	public IMIncidencia(JFrame padre, String codi , String matricula, String taller) {
		super(padre,true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				if(codi != null) {
					cargarDatos(codi);
				}
			}
		});
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Descripción");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel.setBounds(175, 53, 92, 19);
			contentPanel.add(lblNewLabel);
		}
		
		textPane = new JTextPane();
		textPane.setBounds(41, 84, 353, 88);
		contentPanel.add(textPane);
		
		chckbxNewCheckBox = new JCheckBox("Resuelta");
		chckbxNewCheckBox.setBounds(175, 179, 92, 23);
		contentPanel.add(chckbxNewCheckBox);
		chckbxNewCheckBox.setVisible(false);
		{
			lblTitulo = new JLabel("Insertar Incidencia");
			lblTitulo.setFont(new Font("Monotype Corsiva", Font.BOLD, 22));
			lblTitulo.setBounds(130, 17, 186, 25);
			contentPanel.add(lblTitulo);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String mensaje = textPane.getText();
						if(mensaje.equalsIgnoreCase("") == true) {
							JOptionPane.showMessageDialog(IMIncidencia.this, "La descripción no puede quedar vacia.", "Descripción vacia", JOptionPane.ERROR_MESSAGE);
						}else {
							if(codi == null) {
								boolean correcto = conexionBDD.InsertarIncidencia(mensaje, taller, matricula);
								if(correcto == true) {
									JOptionPane.showMessageDialog(IMIncidencia.this, "Se ha insertado incidencia correctamente", "Insertado con exito", JOptionPane.INFORMATION_MESSAGE);
									respuesta = 1;
								}else {
									JOptionPane.showMessageDialog(IMIncidencia.this, "Fallo al realizar la inserción.", "Error en Inserción", JOptionPane.ERROR_MESSAGE);
								}
							}else {
								boolean resuelto = chckbxNewCheckBox.isSelected();
								Incidencia i = new Incidencia(codi, mensaje, taller, matricula , resuelto);
								boolean correcto = conexionBDD.modificarIncidencia(i);
								if(correcto == true) {
									JOptionPane.showMessageDialog(IMIncidencia.this, "Se ha modificado la incidencia", "Incidencia modificada", JOptionPane.INFORMATION_MESSAGE);
									respuesta = 1;
								}else {
									JOptionPane.showMessageDialog(IMIncidencia.this, "Fallo al realizar la modificación.", "Error en Modificación", JOptionPane.ERROR_MESSAGE);
								}
								
							}
							dispose();
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
						
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public void cargarDatos(String codigo) {
		Incidencia i = conexionBDD.cargarIncidenciaCI(codigo);
		textPane.setText(i.getDescripcion());
		chckbxNewCheckBox.setVisible(true);
		chckbxNewCheckBox.setSelected(i.isResuelto());
		lblTitulo.setText("Modificar Incidencia");
		
	}
	public int responder() {
		return respuesta;
	}
}
