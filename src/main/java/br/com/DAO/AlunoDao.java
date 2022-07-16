package br.com.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.JPAutil.JPAutil;
import br.com.entidades.Aluno;
import br.com.entidades.Turma;

public class AlunoDao<E> extends DAOgeneric<Aluno> {
	
	private DAOgeneric<Turma> daoGenericT = new DAOgeneric<Turma>();
	
	public Aluno encontrarPorId(Long id) {
		EntityManager entityManager = JPAutil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		Aluno alunoEncontrado = entityManager.find(Aluno.class, id);
		
		entityTransaction.commit();
		entityManager.close();
		
		return alunoEncontrado;
	}
	
	/*public List<Turma> turmasDoAlunoSelecionadoID(long idAluno){
		Aluno alunoSelec = encontrarPorId(idAluno);
		return (List<Turma>) alunoSelec.getTurmasMatriculadas();
	}
	
	public List<Turma> turmasDoAluno(Aluno aluno){
		return turmasDoAlunoSelecionadoID(aluno.getId());
	}
	
	public List<Turma> turmasDisponiveis(Aluno aluno){
		TurmaDao<Turma> daoTurma = new TurmaDao<Turma>();
		List<Turma> turmasDisponiveis = daoTurma.getTurmas();
		turmasDisponiveis.removeAll(turmasDoAluno(aluno));
		return turmasDisponiveis;
	}
	*/
	
	
	public void removerAlunoTurma(Turma turma, Aluno aluno) {
		getEntityManager().getTransaction().begin();
		String excluirAlunoTurma = "DELETE FROM matriculados WHERE id_turma = " + turma.getId() 
									+ "AND id_aluno" + turma.getId();
		getEntityManager().createNativeQuery(excluirAlunoTurma).executeUpdate();
		getEntityManager().getTransaction().commit();
		super.excluirPorId(aluno);
		daoGenericT.excluirPorId(turma);
	}
	
	public Aluno matricularAlunoNaTurma(Aluno aluno, Turma turma) {
		Aluno alunoSelecionado = encontrarPorId(aluno.getId());
		if(alunoSelecionado != null) {
			TurmaDao<Turma> daoTurma = new TurmaDao<Turma>();
			Turma turmSelec = daoTurma.encontrarPorId(turma.getId());
			if(turmSelec != null) {
				boolean alunoNaoEstaNaTurma = !turmSelec.getTurmasAluno().contains(alunoSelecionado);
				boolean turmaNaoTemAlunoSelec = !alunoSelecionado.getTurmasMatriculadas().contains(turmSelec);
				
				if(alunoNaoEstaNaTurma) {
					turmSelec.getTurmasAluno().add(alunoSelecionado);
				}
				
				if(turmaNaoTemAlunoSelec) {
					alunoSelecionado.getTurmasMatriculadas().add(turmSelec);
				}
			}
		}
		return alunoSelecionado;
		
	}

}
