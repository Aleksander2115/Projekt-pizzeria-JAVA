package pizzeriaDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pizzeria.entities.Rola;
import pizzeria.entities.Uzytkownik;

@Stateless
public class UzytkownikDAO {
	
	@PersistenceContext(unitName = "pizzeria-pizzeriaPU")
	protected EntityManager em;
	
	public void create(Uzytkownik uzytkownik) {
		em.persist(uzytkownik);
	}
	
	public Uzytkownik merge(Uzytkownik uzytkownik) {
		return em.merge(uzytkownik);
	}
	
	public void remove(Uzytkownik uzytkownik) {
		em.remove(uzytkownik);
	}
	
	public Uzytkownik find(Object id) {
		return em.find(Uzytkownik.class, id);
	}

	public List<Uzytkownik> getFullList() {
		List<Uzytkownik> list = null;

		Query query = em.createQuery("select u from Uzytkownik u");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}	
	
	public List<Uzytkownik> getList(Map<String, Object> searchParams) {
		List<Uzytkownik> list = null;

		// 1. Build query string with parameters
		String select = "select u ";
		String from = "from Uzytkownik u ";
		String where = "";
		String orderby = "order by u.nazwisko asc, u.imie";

		// search for surname
		String nazwisko = (String) searchParams.get("nazwisko");
		if (nazwisko != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.nazwisko like :nazwisko ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (nazwisko != null) {
			query.setParameter("nazwisko", nazwisko+"%");
		}

		// ... other parameters ... 

		// 4. Execute query and retrieve list of Person objects
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public Uzytkownik getUserFromDB(String login, String haslo) {
		
		Uzytkownik u = null;
		
		Query query = em.createQuery("select u from Uzytkownik u where u.login like :login and u.haslo like :haslo");
		
		if (login != null) {
			query.setParameter("login", login);
			query.setParameter("haslo", haslo);
		}
		
		try {
			u = (Uzytkownik)query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return u;
	}
	
	public String getLoginFromDB(String login) {
		
		String log = null;
		
		Query query = em.createQuery("select login from Uzytkownik u where u.login like :login");
		query.setParameter("login", login);
		
		try {
			log = (String)query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return log;
	}
	
	public List<String> getRolesFromDB(Uzytkownik uzytkownik) { // -> Role do ogarnięcia
		
		ArrayList<String> roles = new ArrayList<String>();
		
		if (uzytkownik.getLogin().equals("Admin") && uzytkownik.getHaslo().equals("Admin")) {
			Query query1 = em.createQuery("select nazwa_roli from Rola r where r.nazwa_roli='Admin'");
			roles.add((String)query1.getSingleResult());
		} else if (uzytkownik.getLogin().equals("Mod") && uzytkownik.getHaslo().equals("Mod")) {
			Query query2 = em.createQuery("select nazwa_roli from Rola r where r.nazwa_roli='Mod'");
			roles.add((String)query2.getSingleResult());
		} else {
			Query query3 = em.createQuery("select nazwa_roli from Rola r where r.nazwa_roli='User'");
			roles.add((String)query3.getSingleResult());
		}
		
		return roles;
	}
	
	public void insertUserRole(int ID_Uzytkownik) {
		
		Query query1 = em.createQuery("select ID_Rola from Rola r where r.ID_Rola='1'");
		
		int rola = (Integer)query1.getSingleResult();
		
		Query query = em.createNativeQuery("insert into uzytkownik_rola (ID_Rola, ID_Uzytkownik) values (?, ?)");
		
		query.setParameter(1, rola);
		query.setParameter(2, ID_Uzytkownik);
		query.executeUpdate();
	}
	
	public List<Uzytkownik> getUserWithRoles() {
		
		List<Uzytkownik> list = null;
		
		Query query = em.createQuery("select u from Uzytkownik u");
		
		list = query.getResultList();
		
		return list;
	}
	
	public void changeToMod(int ID_Uzytkownik) {
		
		Query query = em.createNativeQuery("update uzytkownik_rola u set ID_Rola = 2 where ID_Uzytkownik = :ID_Uzytkownik");
		query.setParameter("ID_Uzytkownik", ID_Uzytkownik);
		
		query.executeUpdate();
	}
	
	public void changeToAdmin(int ID_Uzytkownik) {
		
		Query query = em.createNativeQuery("update uzytkownik_rola u set ID_Rola = 3 where ID_Uzytkownik = :ID_Uzytkownik");
		query.setParameter("ID_Uzytkownik", ID_Uzytkownik);
		
		query.executeUpdate();
	}
	
	public void changeToUser(int ID_Uzytkownik) {
		
		Query query = em.createNativeQuery("update uzytkownik_rola u set ID_Rola = 1 where ID_Uzytkownik = :ID_Uzytkownik");
		query.setParameter("ID_Uzytkownik", ID_Uzytkownik);
		
		query.executeUpdate();
	}
	
	public List<Uzytkownik> getListLazy(int start, int size) {
        
		List<Uzytkownik> list = null;

        Query query = em.createQuery("select u from Uzytkownik u");

        query.setFirstResult(start);
        query.setMaxResults(size);

        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
