package br.com.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.JPAutil.JPAutil;
import br.com.entidades.Aluno;
import br.com.entidades.Professor;
import br.com.entidades.Turma;

public class TurmaDao<E> extends DAOgeneric<Turma> {

	private List<Turma> turmas;
	// private DAOgeneric<Aluno> daOgenericA = new DAOgeneric<Aluno>();

	public Turma encontrarPorId(Long id) {
		EntityManager entityManager = JPAutil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		Turma turmaEncontrada = null;
		if(entityManager.isOpen()) {
			turmaEncontrada = entityManager.find(Turma.class, id);
		}

		return turmaEncontrada;
	}

	public void removerProfessorTurma(Turma turma) {
		if(turma.getProfessor() != null) {
			ProfessorDao<Professor> daoProf = new ProfessorDao<Professor>();
			Professor profSelecionado = daoProf.encontrarPorId(turma.getProfessor().getId());
			profSelecionado.getTurmasDoProfessor().remove(turma);
			
			Turma turmaSelec = encontrarPorId(turma.getId());
			turmaSelec.setProfessor(null);
			
			daoProf.atualizar(profSelecionado);
			daoProf.retornaProf();
			atualizar(turmaSelec);
		}

	}
	
	

	private void ordenarTurmaPorIdioma() {
		EntityManager entityManager = JPAutil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Turma> criteria = cb.createQuery(Turma.class);
		Root<Turma> turma = criteria.from(Turma.class);
		criteria.select(turma).orderBy(cb.asc(turma.get("idioma")));
		turmas = entityManager.createQuery(criteria).getResultList();
		
		entityTransaction.commit();
		entityManager.close();
	}
	
	public List<Aluno> alunosMatriculadosNaTurma(Turma turma){
		return (List<Aluno>) turma.getTurmasAluno();
	}
	
	public List<Turma> getTurmas() {
		ordenarTurmaPorIdioma();
		return turmas;
	}

	public List<Turma> turmasSemProfessor() {
		EntityManager entityManager = JPAutil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		List<Turma> listaTurmaSemProf = new ArrayList<Turma>();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Turma> criteria = cb.createQuery(Turma.class);
		Root<Turma> turma = criteria.from(Turma.class);
		
		criteria.select(turma).where(cb.isNull(turma.get("professor")))
								.orderBy(cb.asc(turma.get("idioma")));
		listaTurmaSemProf.addAll(entityManager.createQuery(criteria).getResultList());
		
		entityTransaction.commit();
		entityManager.close();
		
		return listaTurmaSemProf;
	}
	
	/*
	@SuppressWarnings("unchecked")
	public List<Turma> turmaSemProf(Turma turma){
		List<Turma> retornaTurmaSemProf = new ArrayList<Turma>();
		EntityManager em = JPAutil.getEntityManager();
		EntityTransaction entityT = em.getTransaction();
		entityT.begin();
		
		retornaTurmaSemProf = (List<Turma>) em.createQuery("SELECT id_professor FROM " + turma.getProfessor().getId());
		
		entityT.commit();
		em.close();
		return retornaTurmaSemProf;
	}	*/
	
	public Turma atualizar(Turma turma) {
		EntityManager entityManager = JPAutil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try{
			entityTransaction.begin();
			turma = entityManager.merge(turma);
			entityManager.flush();
			
			entityTransaction.commit();
			entityManager.close();
		} catch(Exception ex) {
			entityManager.getTransaction().rollback();
		}
		return turma;
	}
	
	public void addProfessorNaTurma(Professor professor, Turma turma) {
		turma.setProfessor(professor);
		professor.getTurmasDoProfessor().add(turma);
		atualizar(turma);
	}
	
	

	/*
	 * public void removerAlunoTurma(Turma turma, Aluno aluno) {
	 * getEntityManager().getTransaction().begin(); String excluirAlunoTurma =
	 * "DELETE FROM matriculados WHERE id_turma = " + turma.getId() + "AND id_aluno"
	 * + turma.getId();
	 * getEntityManager().createNativeQuery(excluirAlunoTurma).executeUpdate();
	 * getEntityManager().getTransaction().commit(); super.excluirPorId(turma);
	 * daOgenericA.excluirPorId(aluno); }
	 */

}
