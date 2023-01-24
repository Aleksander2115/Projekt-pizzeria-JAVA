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
	
	private int ID_Uzytkownik;
	private int ID_Zamowienie;
	private int ID_Pizza;
	private int ID_Dodatek;

	private Uzytkownik uzytkownik = new Uzytkownik();
	
	@EJB
	UzytkownikDAO uzytkownikDAO;
	
	@EJB
	ZamowienieDAO zamowienieDAO;

	@Inject
	FacesContext context;
	
	@Inject
	ExternalContext extctx;
	
	@Inject
	@ManagedProperty("#{txtMUPErr}")
	private ResourceBundle txtMUPErr;

	public Uzytkownik getUzytkownik() {
		return uzytkownik;
	}

	
	public List<Zamowienie> getOrderList(){
		
		List<Zamowienie> list = null;
		
		HttpSession sessionZam = (HttpSession) extctx.getSession(false);
		ID_Uzytkownik = (Integer) sessionZam.getAttribute("ID_Uzytkownik");
		
		list = zamowienieDAO.getOrderList(ID_Uzytkownik);
		
		return list;
	}
	
	
//	public List<Pizza> orderPizzaLive(){
//		List<Pizza> list = null;
//		
//		HttpSession sessionZam = (HttpSession) extctx.getSession(false);
//		ID_Zamowienie = (Integer) sessionZam.getAttribute("ID_Zamowienie");
//		
//		list = zamowienieDAO.getOrderedPizza(ID_Zamowienie);
//		
//		return list;
//	}
	
	
	public void orderPizza(Pizza pizza) {
		
		HttpSession sessionZam = (HttpSession) extctx.getSession(false);
		ID_Zamowienie = (Integer) sessionZam.getAttribute("ID_Zamowienie");
		
		zamowienieDAO.insertPizzaOrder(pizza.getID_Pizza(), ID_Zamowienie);
		
		zamowienieDAO.changeOrderStatus(ID_Zamowienie, 1);
		
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, txtMUPErr.getString("addToOrderPizza"), null));
	}
	
	
	public void orderAddition(Dodatek dodatek) {
		
		HttpSession sessionZam = (HttpSession) extctx.getSession(false);
		ID_Zamowienie = (Integer) sessionZam.getAttribute("ID_Zamowienie");
		
		zamowienieDAO.insertAdditionOrder(dodatek.getID_Dodatek(), ID_Zamowienie);
		
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, txtMUPErr.getString("addToOrderAddition"), null));
	}
	
	
	public void deletePizzaLive() {
		
		HttpSession sessionZam = (HttpSession) extctx.getSession(false);
		ID_Zamowienie = (Integer) sessionZam.getAttribute("ID_Zamowienie");
		
		zamowienieDAO.deletePizza(ID_Zamowienie);
		
	}
	
	
	public void deleteAdditionLive() {
		
		HttpSession sessionZam = (HttpSession) extctx.getSession(false);
		ID_Zamowienie = (Integer) sessionZam.getAttribute("ID_Zamowienie");
		
		zamowienieDAO.deleteAddition(ID_Zamowienie);
		
	}
}