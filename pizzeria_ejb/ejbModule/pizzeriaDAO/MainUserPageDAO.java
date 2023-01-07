package pizzeriaDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pizzeria.entities.Dodatek;
import pizzeria.entities.Pizza;

@Stateless
public class MainUserPageDAO {
	
	@PersistenceContext(unitName = "pizzeria-pizzeriaPU")
	protected EntityManager em;
	
	public void create(Pizza pizza) {
		em.persist(pizza);
	}
	
	public Pizza merge(Pizza pizza) {
		return em.merge(pizza);
	}
	
	public void remove(Pizza pizza) {
		em.remove(pizza);
	}
	
	public Pizza find(Object id) {
		return em.find(Pizza.class, id);
	}

	public List<Pizza> getPizzaList() {
		List<Pizza> listPizza = null;

		Query query = em.createQuery("select p from Pizza p");

		try {
			listPizza = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listPizza;
	}
	
	public List<Dodatek> getDodatekList() {
		List<Dodatek> listDodatek = null;

		Query query = em.createQuery("select d from Dodatek d");

		try {
			listDodatek = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listDodatek;
	}
}
