package br.com.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity(name = "professores")
public class Professor implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Siape não pode estar vazio!")
	private String siape;
	
	private String formacao;
	
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Genero generoP;
	
	@OneToMany(mappedBy = "professor", cascade = CascadeType.REMOVE)
	private List<Turma> turmasDoProfessor;
	
	
	public Professor() {
		this.turmasDoProfessor =  new ArrayList<Turma>();
	}
	

	public Professor(String nome, String siape, String formacao, String email, Genero generoP) {
		super();
		this.nome = nome;
		this.siape = siape;
		this.formacao = formacao;
		this.email = email;
		this.generoP = generoP;
		this.turmasDoProfessor = new ArrayList<Turma>();
	}
	
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSiape() {
		return siape;
	}

	public void setSiape(String siape) {
		this.siape = siape;
	}

	public String getFormacao() {
		return formacao;
	}

	public void setFormacao(String formacao) {
		this.formacao = formacao;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Genero getGeneroP() {
		return generoP;
	}

	public void setGeneroP(Genero generoP) {
		this.generoP = generoP;
	}

	public List<Turma> getTurmasDoProfessor() {
		return turmasDoProfessor;
	}

	public void setTurmasDoProfessor(List<Turma> turmasDoProfessor) {
		this.turmasDoProfessor = turmasDoProfessor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Professor other = (Professor) obj;
		return Objects.equals(id, other.id);
	}
	

}
