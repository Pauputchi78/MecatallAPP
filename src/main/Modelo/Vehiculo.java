package main.Modelo;

import java.time.LocalDate;

public class Vehiculo {
	private String matricula;
	private String color;
	private String modelo;
	private LocalDate aniomatri;
	private String nifcliente;
	@Override
	public String toString() {
		return "Vehiculo_" + matricula + "_" + color + "_" + modelo + "_"+ aniomatri+"_"+nifcliente;
	}
	public String toStringJSON() {
		return "{\n"
				+ "\"matricula\": \""+matricula+"\",\n"
				+ "\"color\": \""+ color +"\",\n"
				+ "\"modelo\": \""+ modelo +"\",\n"
				+ "\"fechaMatriculacion\": \""+aniomatri.toString()+"\"\n"
				+ "}";
	}
	
	public Vehiculo(String matricula, String color, String modelo, LocalDate aniomatri, String nifcliente) {
		super();
		this.matricula = matricula;
		this.color = color;
		this.modelo = modelo;
		this.aniomatri = aniomatri;
		this.nifcliente = nifcliente;
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
	public LocalDate getAniomatri() {
		return aniomatri;
	}
	public void setAniomatri(LocalDate aniomatri) {
		this.aniomatri = aniomatri;
	}
	public String getNifcliente() {
		return nifcliente;
	}
	

}
