package br.com.entidades;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity(name = "alunos")
public class Aluno implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	@Column(nullable = false, unique = true)
	private String matricula;
	
	@Enumerated(EnumType.STRING)
	private Genero generoA;
	
	@Temporal(TemporalType.DATE)
	private Date dataDeNascimento;
	
	@Embedded
	private Endereco endereço;
	
	@ManyToMany(mappedBy = "turmasAluno", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<Turma> turmasMatriculadas;
	
	
	public Aluno() {
		this.turmasMatriculadas = new ArrayList<Turma>();
	}

	public Aluno(String nome, String matricula, Genero generoA, Date dataDeNascimento) {
		super();
		this.nome = nome;
		this.matricula = matricula;
		this.generoA = generoA;
		this.dataDeNascimento = dataDeNascimento;
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

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Genero getGeneroA() {
		return generoA;
	}

	public void setGeneroA(Genero generoA) {
		this.generoA = generoA;
	}
	
	public Date getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(Date dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	public List<Turma> getTurmasMatriculadas() {
		return turmasMatriculadas;
	}

	public void setTurmasMatriculadas(List<Turma> turmasMatriculadas) {
		this.turmasMatriculadas = turmasMatriculadas;
	}

	public Endereco getEndereço() {
		return endereço;
	}

	public void setEndereço(Endereco endereço) {
		this.endereço = endereço;
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
		Aluno other = (Aluno) obj;
		return Objects.equals(id, other.id);
	}


}
