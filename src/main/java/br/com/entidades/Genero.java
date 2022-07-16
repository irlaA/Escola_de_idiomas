package br.com.entidades;

public enum Genero {

	M("Masculino"), F("Feminino"), O("Outro");

	private String genero;

	private Genero(String genero) {
		this.genero = genero;
	}

	public String getGenero() {
		return this.genero;
	}

}
