package main.Modelo;

public class Vehiculo {
	private String matricula;
	private String color;
	private String modelo;
	private int aniomatri;
	@Override
	public String toString() {
		return "Vehiculo-" + matricula + "-" + color + "-" + modelo + "-"+ aniomatri;
	}
	
	public Vehiculo(String matricula, String color, String modelo, int aniomatri) {
		super();
		this.matricula = matricula;
		this.color = color;
		this.modelo = modelo;
		this.aniomatri = aniomatri;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public int getAniomatri() {
		return aniomatri;
	}
	public void setAniomatri(int aniomatri) {
		this.aniomatri = aniomatri;
	}
	

}
