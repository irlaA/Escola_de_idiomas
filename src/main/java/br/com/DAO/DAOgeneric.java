package br.com.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import br.com.JPAutil.JPAutil;

public class DAOgeneric<E> {
	
	
	private EntityManager entityManager = JPAutil.getEntityManager();

	public void salvar(E entidade) {
		EntityManager entityManager = JPAutil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		entityManager.persist(entidade);
		
		entityTransaction.commit();
		entityManager.close();
	}
	
	public E salvarMerge(E entidade) {
		EntityManager entityManager = JPAutil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		E retorno = entityManager.merge(entidade);
		
		entityTransaction.commit();;
		entityManager.close();
		return retorno;
	}
	
	public void excluir(E entidade) {
		EntityManager entityManager = JPAutil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		entityManager.remove(entidade);
		
		entityTransaction.commit();;
		entityManager.close();
	}
	
	public void excluirPorId(E entidade) {
		EntityManager entityManager = JPAutil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		Object id = JPAutil.getPrimaryKey(entidade);
		
		entityManager
		.createNativeQuery(
				"delete from " + entidade.getClass(). 
				getSimpleName().toLowerCase() + " where id =" + id)
		.executeUpdate();

		
		entityTransaction.commit();;
		entityManager.close();
	}
	
	public List<E> getListaEndidadeId(Class<E> entidade){
		EntityManager em = JPAutil.getEntityManager();
		EntityTransaction entityT = em.getTransaction();
		entityT.begin();
		
		Object id = JPAutil.getPrimaryKey(entidade);
		List<E> retornaPorId = em.createQuery("SELECT "+ id + "FROM " + entidade.getName()).getResultList();
		
		entityT.commit();
		em.close();
		return retornaPorId;
	}
	
	public List<E> getListaEnditade(Class<E> entidade){
		EntityManager entityManager = JPAutil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		List<E> retorno = entityManager.createQuery("from " + entidade.getName()).getResultList();
		
		entityTransaction.commit();
		entityManager.close();
		
		return retorno;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

}
