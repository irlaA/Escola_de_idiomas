package br.com.projeto;

import javax.persistence.Persistence;

public class testeJPA {

	public static void main(String[] args) {
		Persistence.createEntityManagerFactory("escola-de-idiomas");
	}
}
