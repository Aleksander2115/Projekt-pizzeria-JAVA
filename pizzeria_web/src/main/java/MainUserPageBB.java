import javax.inject.Inject;
import javax.inject.Named;

import pizzeria.entities.Uzytkownik;
import pizzeriaDAO.UzytkownikDAO;
import pizzeriaDAO.ZamowienieDAO;
import pizzeria.entities.Dodatek;
import pizzeria.entities.Pizza;
import pizzeriaDAO.MainUserPageDAO;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@ViewScoped
public class MainUserPageBB implements Serializable{
	private static final long serialVersionUID = 1L;

	private Uzytkownik uzytkownik = new Uzytkownik();
	
	private Pizza pizza = new Pizza();
	
	@EJB
	UzytkownikDAO uzytkownikDAO;
	
	@EJB
	MainUserPageDAO mainUserPageDAO;
	
	@EJB
	ZamowienieDAO zamowienieDAO;

	@Inject
	FacesContext context;
	
	@Inject
	ExternalContext extctx;
	
	@Inject
	Flash flash;

	public Uzytkownik getUzytkownik() {
		return uzytkownik;
	}

	public List<Pizza> getPizzaList(){
		
		List<Pizza> listPizza = null;
		
		listPizza = mainUserPageDAO.getPizzaList();
		
		return listPizza;
	}
	
	public List<Dodatek> getDodatekList() {
		
		List<Dodatek> listDodatek = null;
		
		listDodatek = mainUserPageDAO.getDodatekList();
		
		return listDodatek;
	}	
}