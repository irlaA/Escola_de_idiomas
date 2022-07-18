package br.com.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.DAO.AlunoDao;
import br.com.DAO.DAOgeneric;
import br.com.DAO.ProfessorDao;
import br.com.DAO.TurmaDao;
import br.com.entidades.Aluno;
import br.com.entidades.Professor;
import br.com.entidades.Turma;

@ManagedBean(name = "turmaBean")
@SessionScoped
public class TurmaBean implements Serializable {

	private Turma turma = new Turma();
	private Aluno aluno;
	private Professor professor;
	private TurmaDao<Turma> daoTurma = new TurmaDao<Turma>();
	private AlunoDao<Aluno> daoAluno;
	private ProfessorDao<Professor> daoProf = new ProfessorDao<Professor>();
	private DAOgeneric<Turma> daoGenericT = new DAOgeneric<Turma>();

	private boolean existe = false;
	private boolean mostrarProfessorBoolean = false;
	private boolean mostrarAlunoBoolean = false;

	private List<Turma> listaTurmas;
	
	//turma
	public void salvar() {
		turma = daoGenericT.salvarMerge(turma);
		listaTurmas.add(turma);
		listarTurmas();
	}
	
	//turma
	public void novaTurma() {
		turma = new Turma();
		existe = false;
		mostrarAlunoBoolean =  false;
		mostrarProfessorBoolean =  false;
	}
	
	//turma
	public void excluir() {
		if (listaTurmas.contains(turma)) {
			daoGenericT.excluirPorId(turma);
			listaTurmas.remove(turma);
			
		}
		novaTurma();
		
	}
	//deixa o form limpo
	public void voltar() {
		novaTurma();
	}
	
	//turma
	@PostConstruct
	public void listarTurmas() {
		listaTurmas = daoGenericT.getListaEnditade(Turma.class);
	}
 
	
	//aluno
	public void removerAlunoTurma() {
		daoAluno.removerTurmaAluno(turma, aluno);
	}
	
	//aluno
	public void podeMostrarAlunoBoolean() {
		mostrarAlunoBoolean = !mostrarAlunoBoolean;
	}
	
	
	
	//professor
	public void podeMostrarProfBoolean() {
		mostrarProfessorBoolean = !mostrarProfessorBoolean;
	}
	
	//professor
	public void addProfessor() {
		daoTurma.addProfessorNaTurma(professor, turma);
		podeMostrarProfBoolean();
	}
	
	//professor
	public void removerProfTurma() {
		daoTurma.removerProfessorTurma(turma);
	}
	
	//professor 
	public List<Professor> professoresDisponiveis(){
		// listaProfessoresDisp =  daoGenericP.getListaEndidadeId(Professor.class);
		 //return listaProfessoresDisp;
		return daoProf.getListaProfessores();
	}
	
	
	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
		existe = true;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
		existe = true;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public List<Turma> getListaTurmas() {
		return listaTurmas;
	}

	public void setListaTurmas(List<Turma> listaTurmas) {
		this.listaTurmas = listaTurmas;
	}

	public TurmaDao<Turma> getDaoTurma() {
		return daoTurma;
	}

	public DAOgeneric<Turma> getDaoGenericT() {
		return daoGenericT;
	}

	public boolean isExiste() {
		return existe;
	}

	public boolean isMostrarProfessorBoolean() {
		return mostrarProfessorBoolean;
	}

	public boolean isMostrarAlunoBoolean() {
		return mostrarAlunoBoolean;
	}

	

}
