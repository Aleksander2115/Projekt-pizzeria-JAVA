import javax.inject.Inject;
import javax.inject.Named;

import pizzeria.entities.Uzytkownik;
import pizzeriaDAO.UzytkownikDAO;
import pizzeria.entities.Pizza;
import pizzeria.entities.Dodatek;
import pizzeria.entities.Zamowienie;
import pizzeriaDAO.ZamowienieDAO;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.annotation.ManagedProperty;
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
public class OrderBB implements Serializable{
	private static final long serialVersionUID = 1L;

	private Uzytkownik uzytkownik = new Uzytkownik();
	
	private Zamowienie zamowienie = new Zamowienie();
	
	private Pizza pizza = new Pizza();

	@EJB
	UzytkownikDAO uzytkownikDAO;
	
	@EJB
	ZamowienieDAO zamowienieDAO;

	@Inject
	FacesContext context;
	
	@Inject
	ExternalContext extctx;
	
	@Inject
	Flash flash;
	
	@Inject
	@ManagedProperty("#{txtMUPErr}")
	private ResourceBundle txtMUPErr;

	public Uzytkownik getUzytkownik() {
		return uzytkownik;
	}
	
	public Pizza getPizza() {
		return pizza;
	}

//	public List<Zamowienie> getOrderList(){  ---> historia zamowien
//		
//		List<Zamowienie> list = null;
//		
//		HttpSession sessionZam = (HttpSession) extctx.getSession(false);
//		ID_Uzytkownik = (Integer) sessionZam.getAttribute("ID_Uzytkownik");
//		
//		list = zamowienieDAO.getOrderList(ID_Uzytkownik);
//		
//		return list;
//	}
	
	
	public Zamowienie viewCart() {
		
		Zamowienie z = null;
		
		HttpSession sessionZam = (HttpSession) extctx.getSession(false);
		uzytkownik = (Uzytkownik) sessionZam.getAttribute("Uzytkownik");
		
		z = zamowienieDAO.viewCart(uzytkownik);
		
		if (z == null) {
			zamowienieDAO.getCart(uzytkownik);
		}
		
		return z;
	}
	
	public void orderPizza(Pizza pizza) {
		
		HttpSession sessionZam = (HttpSession) extctx.getSession(false);
		zamowienie = (Zamowienie) sessionZam.getAttribute("Zamowienie");
		uzytkownik = (Uzytkownik) sessionZam.getAttribute("Uzytkownik");
		
		zamowienieDAO.insertPizzaOrder(pizza, zamowienie);
		
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, txtMUPErr.getString("addToOrderPizza"), null));
	}
	
	public List<Pizza> viewPizzaCart() {
		
		HttpSession sessionZam = (HttpSession) extctx.getSession(false);
		Uzytkownik u = (Uzytkownik) sessionZam.getAttribute("Uzytkownik");
		
		if (u != null) {
			uzytkownik = u;
		}
		
		List<Pizza> list = zamowienieDAO.viewPizzas(zamowienieDAO.viewCart(uzytkownik));
		
		return list;
	}
	
	public void orderAddition(Dodatek dodatek) {
		
		HttpSession sessionZam = (HttpSession) extctx.getSession(false);
		zamowienie = (Zamowienie) sessionZam.getAttribute("Zamowienie");
		
		zamowienieDAO.insertAdditionOrder(dodatek, zamowienie);
		
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, txtMUPErr.getString("addToOrderAddition"), null));
	}
	
	public List<Dodatek> viewAdditionCart() {
		
		HttpSession sessionZam = (HttpSession) extctx.getSession(false);
		
		Uzytkownik u = (Uzytkownik) sessionZam.getAttribute("Uzytkownik");
		
		if (u != null) {
			uzytkownik = u;
		}
		
		List<Dodatek> list = zamowienieDAO.viewAdditions(zamowienieDAO.viewCart(uzytkownik));
		
		return list;
	}
	
	public String finalizeOrder(Zamowienie zamowienie) {
		
		zamowienieDAO.changeOrderStatus(zamowienie, 1);
		
		return "MainUserPage?faces-redirect=true";
	}
	
	
	public String deletePizza(Pizza pizza) {
		
		HttpSession sessionZam = (HttpSession) extctx.getSession(false);
		zamowienie = (Zamowienie) sessionZam.getAttribute("Zamowienie");
		
		Pizza p = (Pizza) sessionZam.getAttribute("pizza");
		
		if (p != null) {
			pizza = p;
			sessionZam.removeAttribute("pizza");
		}
		
		zamowienieDAO.deletePizza(zamowienie, pizza);
		
		return "MainUserPage?faces-redirect=true";
	}
	
	
	public void deleteAddition(Dodatek dodatek) {
		
		HttpSession sessionZam = (HttpSession) extctx.getSession(false);
		zamowienie = (Zamowienie) sessionZam.getAttribute("Zamowienie");
		
		zamowienieDAO.deleteAddition(zamowienie, dodatek);
	}
}