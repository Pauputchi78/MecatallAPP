package Modelo;

import java.util.ArrayList;

public class Cliente {
	private String nif;
	private String nombre;
	private int tel;
	private String direc;
	private String ciudad;
	public ArrayList<Vehiculo> Vehiculos = new ArrayList<Vehiculo>();
	public Cliente(String nif, String nombre, int tel, String direc, String ciudad) {
		super();
		this.nif = nif;
		this.nombre = nombre;
		this.tel = tel;
		this.direc = direc;
		this.ciudad = ciudad;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getTel() {
		return tel;
	}
	public void setTel(int tel) {
		this.tel = tel;
	}
	public String getDirec() {
		return direc;
	}
	public void setDirec(String direc) {
		this.direc = direc;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	} 

	
	
}
