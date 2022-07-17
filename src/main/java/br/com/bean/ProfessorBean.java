package br.com.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.DAO.DAOgeneric;
import br.com.DAO.ProfessorDao;
import br.com.DAO.TurmaDao;
import br.com.entidades.Genero;
import br.com.entidades.Professor;
import br.com.entidades.Turma;

@ManagedBean(name = "professorBean")
@SessionScoped
public class ProfessorBean implements Serializable{
	
	private Professor professor = new Professor();
	private Turma turma;
	private DAOgeneric<Professor> daoGenericP = new DAOgeneric<Professor>();
	private ProfessorDao<Professor> daoProf = new ProfessorDao<Professor>();
	private TurmaDao<Turma> daoTurma = new TurmaDao<Turma>();
	private List<Professor> listaDeProfessores = new ArrayList<Professor>();
	
	private boolean existe = false;
	private boolean mostrarTurmasDispBoolean =  false;
	
	public void salvar() {
		professor = daoGenericP.salvarMerge(professor);
		atualizar();
		novoProf();
	}
	
	public void novoProf() {
		professor = new Professor();
		existe = false;
	}
	
	public void excluir() {
		//daoGenericP.excluirPorId(professor);
		//novoProf();
		//getListDeProfessores();
		if(listaDeProfessores.contains(professor)) {
			daoGenericP.excluirPorId(professor);
			getListDeProfessores();
		}
		novoProf();
	}
	
	public void atualizar() {
		listaDeProfessores = daoProf.getListaProfessores();
	}
	
	public void getListDeProfessores() {
		listaDeProfessores = daoGenericP.getListaEnditade(Professor.class);
	}
	
	public Genero[] getGeneros() {
		return br.com.entidades.Genero.values();
	}
	
	public void addProfATurma() {
		daoProf.addProfTurma(professor, turma);
	}
	
	public List<Turma> turmasDoProfessor(){
		//return daoProf.turmasMinistradasID(professor);
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
		mostrarTurmasDispBoolean = !mostrarTurmasDispBoolean;	
	}
	
	public boolean isExiste() {
		return existe;
	}

	public boolean isMostrarTurmasBoolean() {
		return mostrarTurmasDispBoolean;
	}

	public List<Professor> getListaDeProfessores() {
		return listaDeProfessores;
	}

	public void setListaDeProfessores(List<Professor> listaDeProfessores) {
		this.listaDeProfessores = listaDeProfessores;
	}

}
