package dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import model.Partido;
import model.Porra;
import model.PorraPK;

public class DaoPartido extends BaseJPADao{
	
	public Partido buscarPartido (long idPartido) throws SQLException, Exception{
		EntityManager em = getEntityManager();
		Partido partido = em.find(Partido.class, idPartido);
		em.close();
		
		return partido;
	}

	public List<Partido> getPartidoPorJornada(long idJornada) throws SQLException, Exception{
		EntityManager em = getEntityManager();
		List<Partido> listadoPartidos = null;

		String textoConsulta = "SELECT p FROM Partido p WHERE p.jornada.idjornada = :idjornada";

		TypedQuery<Partido> consulta = em.createQuery(textoConsulta,Partido.class);
		consulta.setParameter("idjornada", idJornada);
		listadoPartidos = consulta.getResultList();

		em.close();
		return listadoPartidos;

	}

}
