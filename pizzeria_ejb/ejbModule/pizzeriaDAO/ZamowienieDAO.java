package pizzeriaDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pizzeria.entities.Pizza;
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
	
	public List<Zamowienie> getOrderList(int ID_Uzytkownik) {
		List<Zamowienie> list = null;

		Query query = em.createQuery("select z from Zamowienie z where z.uzytkownik.ID_Uzytkownik = :ID_Uzytkownik");
		
		query.setParameter("ID_Uzytkownik", ID_Uzytkownik);
			
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public void insertPizzaOrder(int ID_Pizza, int ID_Zamowienie) {
		
		Query query = em.createNativeQuery("insert into zamowienie_pizza (ID_Pizza, ID_Zamowienie) values (?, ?)");
		
		query.setParameter(1, ID_Pizza);
		query.setParameter(2, ID_Zamowienie);
		query.executeUpdate();
	}
	
	public void insertAdditionOrder(int ID_Dodatek, int ID_Zamowienie) {
		
		Query query = em.createNativeQuery("insert into zamowienie_dodatek (ID_Dodatek, ID_Zamowienie) values (?, ?)");
		
		query.setParameter(1, ID_Dodatek);
		query.setParameter(2, ID_Zamowienie);
		query.executeUpdate();
	}
	
//	public List<Pizza> getOrderedPizza(int ID_Zamowienie) {
//		
//		List<Pizza> list = null;
//		
//		Query query = em.createQuery("select p from Pizza p where ");
//		
//		list = query.getResultList();
//		
//		return list;
//	}
	
	public Zamowienie getOrder(int ID_Uzytkownik) {
		
		Zamowienie z = null;
		
		Query query = em.createQuery("select z from Zamowienie z where z.uzytkownik.ID_Uzytkownik = :ID_Uzytkownik");
		
		query.setParameter("ID_Uzytkownik", ID_Uzytkownik);
		
		z = (Zamowienie)query.getSingleResult();
		
		return z;
	}
	
	public void createOrder(int ID_Uzytkownik) {
		
		Query query = em.createNativeQuery("insert into Zamowienie (ID_Uzytkownik, cena_dostawy, koszt_calkowity, status) values (?,?,?,?)");
		
		query.setParameter(1, ID_Uzytkownik);
		query.setParameter(2, "0");
		query.setParameter(3, "0");
		query.setParameter(4, "0");
		query.executeUpdate();
	}
	
	public void changeOrderStatus(int ID_Zamowienie, int numerStatusu) {
		
		Query query = em.createQuery("update Zamowienie set Status = :Status where ID_Zamowienie = :ID_Zamowienie");
		
		query.setParameter("ID_Zamowienie", ID_Zamowienie);
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
	
	public void deletePizza(int ID_Zamowienie) {
		
		Query query = em.createNativeQuery("delete from zamowienie_pizza where ID_Zamowienie = :ID_Zamowienie");
		query.setParameter("ID_Zamowienie", ID_Zamowienie);
		
		query.executeUpdate();
	}
	
	public void deleteAddition(int ID_Zamowienie) {
		
		Query query = em.createNativeQuery("delete from zamowienie_dodatek where ID_Zamowienie = :ID_Zamowienie");
		query.setParameter("ID_Zamowienie", ID_Zamowienie);
		
		query.executeUpdate();
	}
}
