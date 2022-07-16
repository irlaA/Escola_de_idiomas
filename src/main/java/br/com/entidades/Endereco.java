package br.com.entidades;

import java.io.Serializable;

import javax.persistence.Embeddable;


@Embeddable
public class Endereco implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	private String cidade;
	private String bairro;
	private String rua;
	private String numero;

	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	

}
