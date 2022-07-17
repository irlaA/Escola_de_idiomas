package br.com.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;


@Entity(name = "turmas")
public class Turma implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String idioma;
	
	private String horario;
	
	private String sala;
	
	@ManyToOne(targetEntity = Professor.class, cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_professor")
	private Professor professor;
	
	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER)
	@JoinTable(name = "matriculados", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_turma", "id_aluno"})},
	joinColumns = {@JoinColumn(name="id_turma")}, inverseJoinColumns = {@JoinColumn(name="id_aluno")} )
	private List<Aluno> turmasAluno;
	
	
	
	public Turma() {
		this.turmasAluno = new ArrayList<Aluno>();
	}
	
	public Turma(String idioma, String horario, String sala) {
		super();
		this.idioma = idioma;
		this.horario = horario;
		this.sala = sala;
		this.professor = null;
		this.turmasAluno = new ArrayList<Aluno>();
	}
	
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Professor getProfessor() {
		return professor;
	}


	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public String getIdioma() {
		return idioma;
	}


	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}


	public String getHorario() {
		return horario;
	}


	public void setHorario(String horario) {
		this.horario = horario;
	}


	public String getSala() {
		return sala;
	}


	public void setSala(String sala) {
		this.sala = sala;
	}


	public List<Aluno> getTurmasAluno() {
		return turmasAluno;
	}


	public void setTurmasAluno(List<Aluno> turmasAluno) {
		this.turmasAluno = turmasAluno;
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
		Turma other = (Turma) obj;
		return Objects.equals(id, other.id);
	}
	


}
