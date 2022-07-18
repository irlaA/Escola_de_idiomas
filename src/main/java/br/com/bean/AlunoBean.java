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
import br.com.entidades.Endereco;
import br.com.entidades.Genero;
import br.com.entidades.Turma;

@ManagedBean(name = "alunoBean")
@SessionScoped
public class AlunoBean implements Serializable{
	
	private AlunoDao<Aluno> daoAluno = new AlunoDao<Aluno>();
	private DAOgeneric<Aluno> daoGenericA = new DAOgeneric<Aluno>();
	private TurmaDao<Turma> daoTurma = new TurmaDao<Turma>();
	private List<Aluno> listaAlunos;
	private Turma turma = new Turma();
	private Aluno aluno = new Aluno();
	
	private boolean existe = false;
	private boolean turmasBoolean = false;
	
	
	
	public void salvar() {
		//aluno = daoAluno.salvarMerge(aluno);
		//aluno = daoGenericA.salvarMerge(aluno);
		daoGenericA.salvarMerge(aluno);
		listaAlunos.add(aluno);
		listarAlunos();
		
	}

	
	public void novoAluno() {
		aluno = new Aluno();
		existe = false;
		turmasBoolean = false;
	}
	
	public void excluir() {
		if(listaAlunos.contains(aluno)) {
			daoGenericA.excluirPorId(aluno);
			listaAlunos.remove(aluno);
			
		}
		novoAluno();
		
	}
	
	public void voltar() {
		novoAluno();
	}
	
	
	@PostConstruct
	public void listarAlunos() {
		listaAlunos = daoGenericA.getListaEnditade(Aluno.class);
	}
	
	public void matricularAlunoTurma() {
		daoAluno.matricularAlunoNaTurma(aluno, turma);
		
	}
	
	public void removerTurmaAluno() {
		daoAluno.removerTurmaAluno(turma, aluno);
	}
	
	public List<Turma> turmasDisponiveis(){
		List<Turma> turmasDisponiveis = daoTurma.getTurmas();
		turmasDisponiveis.removeAll(aluno.getTurmasMatriculadas());
		return turmasDisponiveis;
	}
	
	public List<Turma> turmaDoAlunoId(){
		return aluno.getTurmasMatriculadas();
	}
	
	public String cadastrarEndereco() {
			return "cEndereco.jsf";
	
		
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


	public void setListaAlunos(List<Aluno> listaAlunos) {
		this.listaAlunos = listaAlunos;
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


	public Turma getTurma() {
		return turma;
	}


	public void setTurma(Turma turma) {
		this.turma = turma;
	}


	

	
	
}
