package br.com.JPAutil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAutil {

	private static EntityManagerFactory emf = null;

	static {
		init();
	}

	private static void init() {
		try {
			if (emf == null) {
				emf = Persistence.createEntityManagerFactory("escola-de-idiomas");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public static Object getPrimaryKey(Object entity) {
		return emf.getPersistenceUnitUtil().getIdentifier(entity);
	}

}
