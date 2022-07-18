package br.com.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.JPAutil.JPAutil;
import br.com.entidades.Aluno;
import br.com.entidades.Turma;

public class AlunoDao<E> extends DAOgeneric<Aluno> {

	private DAOgeneric<Turma> daoGenericT = new DAOgeneric<Turma>();
	private List<Aluno> alunos = new ArrayList<Aluno>();

	public Aluno encontrarPorId(Long id) {
		EntityManager entityManager = JPAutil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		Aluno alunoEncontrado = entityManager.find(Aluno.class, id);

		entityTransaction.commit();
		entityManager.close();

		return alunoEncontrado;
	}
	
	

	/*
	 * public List<Turma> turmasDoAlunoSelecionadoID(long idAluno){ Aluno alunoSelec
	 * = encontrarPorId(idAluno); return (List<Turma>)
	 * alunoSelec.getTurmasMatriculadas(); }
	 * 
	 * public List<Turma> turmasDoAluno(Aluno aluno){ return
	 * turmasDoAlunoSelecionadoID(aluno.getId()); }
	 * 
	 * public List<Turma> turmasDisponiveis(Aluno aluno){ TurmaDao<Turma> daoTurma =
	 * new TurmaDao<Turma>(); List<Turma> turmasDisponiveis = daoTurma.getTurmas();
	 * turmasDisponiveis.removeAll(turmasDoAluno(aluno)); return turmasDisponiveis;
	 * }
	 */

	public Aluno removerTurmaAluno(Turma turma, Aluno aluno) {
		try {
			EntityManager entityManager = JPAutil.getEntityManager();
			EntityTransaction entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			
			aluno = entityManager.merge(aluno);
			turma = entityManager.merge(turma);
			
			aluno.getTurmasMatriculadas().remove(turma);
			turma.getTurmasAluno().remove(aluno);
			
			entityTransaction.commit();
			entityManager.close();
			
		}catch (Exception ex){
			ex.printStackTrace();
		}
		retornaAlunos();
		return aluno;
		
	/*	getEntityManager().getTransaction().begin();
		String excluirAlunoTurma = "DELETE FROM matriculados WHERE id_turma = " + turma.getId() + "AND id_aluno"
				+ turma.getId();
		getEntityManager().createNativeQuery(excluirAlunoTurma).executeUpdate();
		getEntityManager().getTransaction().commit();
		super.excluirPorId(aluno);
		daoGenericT.excluirPorId(turma);	*/
	}

	private void pegarAlunosPorNome() {
		EntityManager entityManager = JPAutil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Aluno> criteria = cb.createQuery(Aluno.class);
		Root<Aluno> aluno = criteria.from(Aluno.class);
		criteria.select(aluno).orderBy(cb.asc(aluno.get("nome")));
		alunos = entityManager.createQuery(criteria).getResultList();
		
		entityTransaction.commit();
		entityManager.close();
	}
	
	public List<Aluno> retornaAlunos(){
		pegarAlunosPorNome();
		return alunos;
	}

	public Aluno matricularAlunoNaTurma(Aluno aluno, Turma turma) {
		Aluno alunoSelecionado = encontrarPorId(aluno.getId());
		if(alunoSelecionado != null) {
			TurmaDao<Turma> daoTurma = new TurmaDao<Turma>();
			Turma turmaSelec = daoTurma.encontrarPorId(turma.getId());
			if(turmaSelec != null) {
				boolean alunoNaoEstaNaTurma = !turmaSelec.getTurmasAluno().contains(alunoSelecionado);
				boolean turmaNaoTemAlunoSelec = !alunoSelecionado.getTurmasMatriculadas().contains(turmaSelec);
				
				if(alunoNaoEstaNaTurma) {
					turmaSelec.getTurmasAluno().add(alunoSelecionado);
				}
				
				if(turmaNaoTemAlunoSelec) {
					alunoSelecionado.getTurmasMatriculadas().add(turmaSelec);
				}
				daoTurma.salvarMerge(turmaSelec);
				alunoSelecionado = salvarMerge(alunoSelecionado);
				daoTurma.getTurmas();
				retornaAlunos();
			}
		}
		
		return alunoSelecionado;
		
	}

}
