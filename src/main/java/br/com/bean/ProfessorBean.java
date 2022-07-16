package br.com.bean;

import java.io.Serializable;
import java.util.List;

import br.com.DAO.DAOgeneric;
import br.com.DAO.ProfessorDao;
import br.com.DAO.TurmaDao;
import br.com.entidades.Genero;
import br.com.entidades.Professor;
import br.com.entidades.Turma;

public class ProfessorBean implements Serializable{
	
	private Professor professor;
	private Turma turma;
	private DAOgeneric<Professor> daoGenericP = new DAOgeneric<Professor>();
	private ProfessorDao<Professor> daoProf;
	private TurmaDao<Turma> daoTurma;
	private List<Professor> professores;
	
	private boolean existe = false;
	private boolean mostrarTurmasBoolean =  false;
	
	public void salvar() {
		daoGenericP.salvarMerge(professor);
		novoProf();
	}
	
	public void novoProf() {
		professor = new Professor();
		existe = false;
	}
	
	public void excluir() {
		if(professores.contains(professor)) {
			daoGenericP.excluirPorId(professor);
		}
		novoProf();
	}
	
	public Genero[] getGeneros() {
		return br.com.entidades.Genero.values();
	}
	
	public void addProfATurma() {
		daoProf.addProfTurma(professor, turma);
	}
	
	public List<Turma> turmasDoProfessor(){
		return (List<Turma>) professor.getTurmasDoProfessor();
	}
	
	public void removerTurma() {
		daoProf.removerProfDaTurma(professor, turma);
	}
	
	public List<Turma> turmasDisponiveis(){
		return daoTurma.turmasSemProfessor();
	}
	
	
	
	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
		existe = true;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	
	public void mostrarTurmasBollean() {
		mostrarTurmasBoolean = !mostrarTurmasBoolean;	
	}
	
	public boolean isExiste() {
		return existe;
	}

	public boolean isMostrarTurmasBoolean() {
		return mostrarTurmasBoolean;
	}

	public List<Professor> getProfessores() {
		return professores;
	}

	public void setProfessores(List<Professor> professores) {
		this.professores = professores;
	}
	
	
	

}
