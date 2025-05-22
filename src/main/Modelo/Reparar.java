package main.Modelo;

import java.time.LocalDate;

public class Reparar {
	
	private String matricula;
	private String cod_taller;
	private LocalDate fechaparte;
	
	public Reparar(String matricula, String cod_taller, LocalDate fechaparte) {
		this.matricula = matricula;
		this.cod_taller = cod_taller;
		this.fechaparte = fechaparte;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCod_taller() {
		return cod_taller;
	}

	public void setCod_taller(String cod_taller) {
		this.cod_taller = cod_taller;
	}

	public LocalDate getFechaparte() {
		return fechaparte;
	}

	public void setFechaparte(LocalDate fechaparte) {
		this.fechaparte = fechaparte;
	}
	
	

}
