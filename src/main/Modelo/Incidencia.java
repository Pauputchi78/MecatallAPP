package main.Modelo;

public class Incidencia {
	
	private String cod_incidencia;
	private String descripcion;
	private String taller;
	private String matricula;
	private boolean resuelto;
	
	public Incidencia(String cod_incidencia, String descripcion, String taller, String matricula, boolean resuelto) {
		this.cod_incidencia = cod_incidencia;
		this.descripcion = descripcion;
		this.taller = taller;
		this.matricula = matricula;
		this.resuelto = resuelto;
	}

	public String getCod_incidencia() {
		return cod_incidencia;
	}

	public void setCod_incidencia(String cod_incidencia) {
		this.cod_incidencia = cod_incidencia;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTaller() {
		return taller;
	}

	public void setTaller(String taller) {
		this.taller = taller;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public boolean isResuelto() {
		return resuelto;
	}

	public void setResuelto(boolean resuelto) {
		this.resuelto = resuelto;
	}
	

}
