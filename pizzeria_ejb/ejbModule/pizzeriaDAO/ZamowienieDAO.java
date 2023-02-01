package pizzeriaDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pizzeria.entities.Pizza;
import pizzeria.entities.Dodatek;
import pizzeria.entities.Uzytkownik;
import pizzeria.entities.Zamowienie;

@Stateless
public class ZamowienieDAO {
	
	@PersistenceContext(unitName = "pizzeria-pizzeriaPU")
	protected EntityManager em;
	
	public void create(Zamowienie zamowienie) {
		em.persist(zamowienie);
	}
	
	public Zamowienie merge(Zamowienie zamowienie) {
		return em.merge(zamowienie);
	}
	
	public void remove(Zamowienie zamowienie) {
		em.remove(zamowienie);
	}
	
	public Zamowienie find(Object id) {
		return em.find(Zamowienie.class, id);
	}
	
	public List<Zamowienie> getWholeOrderList() {
		
		List<Zamowienie> list = null;
		
		Query query = em.createQuery("select z from Zamowienie z");
		
		list = query.getResultList();
		
		return list;
	}
	
//	public List<Zamowienie> getCartOrderList(Uzytkownik uzytkownik) {
//		
//		List<Zamowienie> list = null;
//
//		Query query = em.createQuery("select z from Zamowienie z where z.uzytkownik.ID_Uzytkownik = :ID_Uzytkownik and z.status = 0");
//		
//		query.setParameter("ID_Uzytkownik", uzytkownik.getID_Uzytkownik());
//			
//		try {
//			list = query.getResultList();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return list;
//	}
	
	public Zamowienie getCart(Uzytkownik u) {
		
		Zamowienie z = null;
		
		Query query = em.createQuery("select z from Zamowienie z where z.uzytkownik.ID_Uzytkownik = :ID_Uzytkownik and z.status = 0");
		
		query.setParameter("ID_Uzytkownik", u.getID_Uzytkownik());
		
		try {
			z = (Zamowienie) query.getSingleResult();
		} catch (Exception e) {}
		
		if (z == null) {
			z = new Zamowienie();
			z.setUzytkownik(u);
			z.setStatus(0);
			em.persist(z);
			em.flush();
		}
		
		return z;
	}
	
	public Zamowienie viewCart(Uzytkownik u) {
		
		Zamowienie z = null;
		
		Query query = em.createQuery("select z from Zamowienie z where z.uzytkownik.ID_Uzytkownik = :ID_Uzytkownik and z.status = 0");
		
		query.setParameter("ID_Uzytkownik", u.getID_Uzytkownik());
		
		try {
			z = (Zamowienie) query.getSingleResult();
		} catch (Exception e) {}
		
		return z;
	}
	
//	public List<Pizza> getPizzas() {
//		
//		List<Pizza> list = null;
//		
//		Query query = em.createQuery("select p from Pizza p");
//		
//		try {
//			list = query.getResultList();
//		} catch (Exception e) {}
//		
//		return list;
//	}
	
	public void insertPizzaOrder(Pizza pizza, Zamowienie zamowienie) {
		
		//Zamowienie z = em.find(Zamowienie.class, zamowienie.getID_Zamowienie());
		
		Zamowienie z = em.merge(zamowienie);
		
		z.getPizzas().add(pizza);
		
		//em.merge(z);
	}
	
	public List<Pizza> viewPizzas(Zamowienie zamowienie) {
		
		if (zamowienie == null || zamowienie.getID_Zamowienie() == 0) {
			return null;
		}
				
		Zamowienie z = (Zamowienie) em.find(Zamowienie.class, zamowienie.getID_Zamowienie());
		
		z.getPizzas().size();
		
		return z.getPizzas();
	}
	
	public void insertAdditionOrder(Dodatek dodatek, Zamowienie zamowienie) {

		Zamowienie z = em.find(Zamowienie.class, zamowienie.getID_Zamowienie());
		
		z.getDodateks().add(dodatek);
	}
	
	public List<Dodatek> viewAdditions(Zamowienie zamowienie) {
		
		if (zamowienie == null || zamowienie.getID_Zamowienie() == 0) {
			return null;
		}
				
		Zamowienie z = (Zamowienie) em.find(Zamowienie.class, zamowienie.getID_Zamowienie());
		
		z.getDodateks().size();
		
		return z.getDodateks();
	}
	
	public void changeOrderStatus(Zamowienie zamowienie, int numerStatusu) {
		
		Query query = em.createQuery("update Zamowienie set status = :Status where ID_Zamowienie = :ID_Zamowienie");
		
		query.setParameter("ID_Zamowienie", zamowienie.getID_Zamowienie());
		query.setParameter("Status", numerStatusu);
		query.executeUpdate();
	}
	
	public void deleteOrder(int ID_Zamowienie, int Status) {
		
		Query query1 = em.createNativeQuery("delete from zamowienie_pizza where ID_Zamowienie = :ID_Zamowienie");
		query1.setParameter("ID_Zamowienie", ID_Zamowienie);
			
		query1.executeUpdate();
			
		Query query2 = em.createNativeQuery("delete from zamowienie_dodatek where ID_Zamowienie = :ID_Zamowienie");
		query2.setParameter("ID_Zamowienie", ID_Zamowienie);
	
		query2.executeUpdate();
			
		Query query = em.createNativeQuery("delete from Zamowienie where ID_Zamowienie = :ID_Zamowienie and status = :Status");
		query.setParameter("ID_Zamowienie", ID_Zamowienie);
		query.setParameter("Status", Status);
		
		query.executeUpdate();
	}
	
	public void deletePizza(Zamowienie zamowienie, Pizza pizza) {
		
		Query query = em.createNativeQuery("delete from zamowienie_pizza where ID_Zamowienie = :ID_Zamowienie and ID_Pizza = :ID_Pizza");
		query.setParameter("ID_Zamowienie", zamowienie.getID_Zamowienie());
		query.setParameter("ID_Pizza", pizza.getID_Pizza());
		
		query.executeUpdate();
		
//		Zamowienie z = em.merge(zamowienie);
//		
//		Pizza p = em.merge(pizza);
//		
//		z.getPizzas().remove(p);
	}
	
	public void deleteAddition(Zamowienie zamowienie, Dodatek dodatek) {
		
		Query query = em.createNativeQuery("delete from zamowienie_dodatek where ID_Zamowienie = :ID_Zamowienie and ID_Dodatek = :ID_Dodatek");
		query.setParameter("ID_Zamowienie", zamowienie.getID_Zamowienie());
		query.setParameter("ID_Dodatek", dodatek.getID_Dodatek());
		
		query.executeUpdate();		
	}
}
