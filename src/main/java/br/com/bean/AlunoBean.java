package br.com.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.DAO.AlunoDao;
import br.com.DAO.DAOgeneric;
import br.com.DAO.TurmaDao;
import br.com.entidades.Aluno;
import br.com.entidades.Genero;
import br.com.entidades.Turma;

@ManagedBean(name = "alunoBean")
@SessionScoped
public class AlunoBean implements Serializable{
	
	private AlunoDao<Aluno> daoAluno;
	private DAOgeneric<Aluno> daoGenericA = new DAOgeneric<Aluno>();
	private List<Aluno> listaAlunos;
	private Turma turma;
	private Aluno aluno;
	
	private boolean existe = false;
	private boolean turmasBoolean = false;
	
	
	
	public String salvar() {
		//aluno = daoAluno.salvarMerge(aluno);
		aluno = daoGenericA.salvarMerge(aluno);
		listaAlunos.add(aluno);
		listarAlunos();
		return "";
	}
	
	public void novoAluno() {
		aluno = new Aluno();
		existe = false;
	}
	
	public void excluir() {
		if(listaAlunos.contains(aluno)) {
			daoGenericA.excluirPorId(aluno);
			listaAlunos.remove(aluno);
			
		}
		novoAluno();
		
	}
	
	
	@PostConstruct
	public void listarAlunos() {
		listaAlunos = daoGenericA.getListaEndidadeId(Aluno.class);
	}
	
	public void matricularAlunoTurma() {
		daoAluno.matricularAlunoNaTurma(aluno, turma);
	}
	
	public void removerAlunoTurma() {
		daoAluno.removerAlunoTurma(turma, aluno);
	}
	
	public List<Turma> turmasDisponiveis(Aluno aluno){
		TurmaDao<Turma> daoTurma = new TurmaDao<Turma>();
		List<Turma> turmasDisponiveis = daoTurma.getTurmas();
		turmasDisponiveis.removeAll(aluno.getTurmasMatriculadas());
		return turmasDisponiveis;
	}
	
	
	public Genero[] getGeneros() {
		return br.com.entidades.Genero.values();
	}
	

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
		existe = true;
		turmasBoolean = false;
	}

	public List<Aluno> getListaAlunos() {
		return listaAlunos;
	}

	public boolean isExiste() {
		return existe;
	}


	public boolean isTurmasBoolean() {
		return turmasBoolean;
	}
	
	public void mudarTurmasBoolean() {
		turmasBoolean = !turmasBoolean;
	}


	public AlunoDao<Aluno> getDaoAluno() {
		return daoAluno;
	}

	public DAOgeneric<Aluno> getDaoGenericA() {
		return daoGenericA;
	}
	
	
	
}
