package br.com.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.JPAutil.JPAutil;
import br.com.entidades.Professor;
import br.com.entidades.Turma;

public class ProfessorDao<E> extends DAOgeneric<Professor> {
	
	private List<Professor> professores;
	
 	
	
	public ProfessorDao<E> removerProfDaTurma(Professor professor, Turma turma){
		if(turma.getProfessor().equals(professor)) {
			TurmaDao<Turma> daoTurma = new TurmaDao<Turma>();
			daoTurma.removerProfessorTurma(turma);
		}
		getProfessores();
		return this;
	}
	
	public void addProfTurma(Professor professor, Turma turma) {
		professor.getTurmasDoProfessor().add(turma);
		turma.setProfessor(professor);
		//atualizar
	}
	
	
	public Professor encontrarPorId(Long id) {
		EntityManager entityManager = JPAutil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		Professor profEncontrado = null;
		if(entityManager.isOpen()) {
			profEncontrado = entityManager.find(Professor.class, id);
		}
		return profEncontrado;
	}
	
	
	
	public List<Turma> turmasMinistradasID(Professor professor){
		return (List<Turma>) encontrarPorId(professor.getId()).getTurmasDoProfessor();
	}


	public List<Professor> getProfessores() {
		return professores;
	}



	
	

}
