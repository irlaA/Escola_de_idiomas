package br.com.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.JPAutil.JPAutil;
import br.com.entidades.Aluno;
import br.com.entidades.Professor;
import br.com.entidades.Turma;

public class ProfessorDao<E> extends DAOgeneric<Professor> {
	
	private List<Professor> listaProfessores;
	private TurmaDao<Turma> daoTurma = new TurmaDao<Turma>();
	
 	
	
	public ProfessorDao<E> removerProfDaTurma(Professor professor, Turma turma){
		if(turma.getProfessor().equals(professor)) {
			daoTurma = new TurmaDao<Turma>();
			daoTurma.removerProfessorTurma(turma);
		}
		getListaProfessores();
		return this;
	}
	
	public Professor atualizar(Professor professor) {
		EntityManager entityManager = JPAutil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try{
			entityTransaction.begin();
			professor = entityManager.merge(professor);
			entityManager.flush();
			
			entityTransaction.commit();
			entityManager.close();
		} catch(Exception ex) {
			entityManager.getTransaction().rollback();
		}
		return professor;
	}
	
	public Professor cadastrarProfNaTurma(Professor professor, Turma turma) {
		Professor profSelecionado = encontrarPorId(professor.getId());
		if(profSelecionado != null) {
			daoTurma = new TurmaDao<Turma>();
			Turma turmaTeste = daoTurma.encontrarPorId(turma.getId());
			if(turmaTeste != null) {
				boolean turmaNaoTemProf = !profSelecionado.getTurmasDoProfessor().contains(turmaTeste);
				
				if(turmaNaoTemProf) {
					profSelecionado.getTurmasDoProfessor().add(turmaTeste);
				}
				daoTurma.salvarMerge(turmaTeste);
				profSelecionado = salvarMerge(profSelecionado);
				daoTurma.getTurmas();
				retornaProf();
			}
		}
		return profSelecionado;
	}
	
	private void pegarProfPorSiape() {
		EntityManager entityManager = JPAutil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Professor> criteria = cb.createQuery(Professor.class);
		Root<Professor> professor = criteria.from(Professor.class);
		criteria.select(professor).orderBy(cb.asc(professor.get("siape")));
		listaProfessores = entityManager.createQuery(criteria).getResultList();
		
		entityTransaction.commit();
		entityManager.close();
		
	}
	
	
	public void addProfTurma(Professor professor, Turma turma) {
		professor.getTurmasDoProfessor().add(turma);
		turma.setProfessor(professor);
		atualizar(professor);
		daoTurma.atualizar(turma);
		//atualizar
	}
	public List<Professor> retornaProf(){
		pegarProfPorSiape();
		return listaProfessores;
	}
	
	
	public Professor encontrarPorId(Long id) {
		EntityManager entityManager = JPAutil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		Professor profEncontrado = null;
		if(entityManager.isOpen()) {
			profEncontrado = entityManager.find(Professor.class, id);
		}
		entityTransaction.commit();
		entityManager.close();
		return profEncontrado;
	}
	
	
	
	public List<Turma> turmasMinistradasID(Professor professor){
		return (List<Turma>) encontrarPorId(professor.getId()).getTurmasDoProfessor();
	}

	public List<Professor> getListaProfessores() {
		return listaProfessores;
	}

	public void setListaProfessores(List<Professor> listaProfessores) {
		this.listaProfessores = listaProfessores;
	}


}
