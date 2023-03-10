package pizzeriaDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pizzeria.entities.Rola;

@Stateless
public class RolaDAO {
	
	@PersistenceContext(unitName = "pizzeria-pizzeriaPU")
	protected EntityManager em;
	
	public void create(Rola rola) {
		em.persist(rola);
	}
	
	public Rola merge(Rola rola) {
		return em.merge(rola);
	}
	
	public void remove(Rola rola) {
		em.remove(rola);
	}
	
	public Rola find(Object id) {
		return em.find(Rola.class, id);
	}
}
