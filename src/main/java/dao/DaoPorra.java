package dao;

import java.math.BigDecimal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import model.Apuesta;
import model.Porra;
import model.PorraPK;


public class DaoPorra extends BaseJPADao{
	
	public Porra buscarPorra (PorraPK porraPK) throws SQLException, Exception{
		EntityManager em = getEntityManager();
		Porra porra = em.find(Porra.class, porraPK);
		em.close();
		
		return porra;
	}
	
	public void addPorra(Porra porra) throws SQLException, Exception{
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		em.persist(porra);
		tx.commit();
		em.close();
		
	}
	
	public ArrayList<Apuesta> getResumenApuesta(long idPartido) throws SQLException, Exception{
		EntityManager em = getEntityManager();
		
		String sql = "select p.goleslocal, p.golesvisitante, count(p) "
		           + "from Porra p where p.partido.idpartido=:idpartido "
		           + "group by p.goleslocal, p.golesvisitante";
		Query q = em.createQuery(sql);
		q.setParameter("idpartido", Long.valueOf(idPartido));
		List<Object[]> resultList = q.getResultList();
		ArrayList<Apuesta> apuestas = new ArrayList<>();
		for (Object[] row : resultList) { //por cada objeto que hemos obtenido en el resultList, vamos añadiendo una nueva apuesta a mis apuestas
		apuestas.add(new Apuesta((BigDecimal) row[0], (BigDecimal) row[1], (Long) row[2]));
		}

		em.close();
		
		return apuestas;

	}

}
