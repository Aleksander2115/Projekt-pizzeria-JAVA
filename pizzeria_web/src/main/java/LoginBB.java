import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Session;
import javax.naming.ldap.ManageReferralControl;
import javax.persistence.EntityManager;

import pizzeria.entities.Uzytkownik;
import pizzeriaDAO.UzytkownikDAO;
import pizzeria.entities.Zamowienie;
import pizzeriaDAO.ZamowienieDAO;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.simplesecurity.RemoteClient;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Named
@ViewScoped
public class LoginBB implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String login;
	private String haslo;
	private int ID_Zamowienie;
	private int ID_Uzytkownik;
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getHaslo() {
		return haslo;
	}

	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}

	private Uzytkownik uzytkownik = new Uzytkownik();
	
	private Zamowienie zamowienie = new Zamowienie();
	
	@EJB
	UzytkownikDAO uzytkownikDAO;
	
	@EJB
	ZamowienieDAO zamowienieDAO;

	@Inject
	FacesContext context;
	
	@Inject
	ExternalContext extctx;

	public Uzytkownik getUzytkownik() {
		return uzytkownik;
	}
	
	public Zamowienie getZamowienie() {
		return zamowienie;
	}

	public String login() {	
		
		context.getCurrentInstance();
		RemoteClient<Uzytkownik> client = new RemoteClient<Uzytkownik>();
		HttpSession sessionZam = (HttpSession) extctx.getSession(true);
		
		if (!uzytkownik.getLogin().equals("Mod"))
			uzytkownik = uzytkownikDAO.getUserFromDB(uzytkownik.getLogin(), uzytkownik.getHaslo());
		
		if (uzytkownik == null) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Użytkownik nie istnieje, zarejestruj się", null));
			logout();
			return null;
		} else if (uzytkownik.getLogin().equals("Admin") && uzytkownik.getHaslo().equals("Admin")) {
			
			uzytkownik.setID_Uzytkownik(-1);
			uzytkownik.setImie("Admin");
			uzytkownik.setNazwisko("Admin");
			uzytkownik.setNr_telefonu(111111111);
			uzytkownik.setMiejscowosc("Admin");
			uzytkownik.setKod_pocztowy(11111);
			uzytkownik.setUlica("Admin");
			uzytkownik.setNr_domu("Admin");
			
			client.getRoles().add("Admin");
			
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			client.store(request);
			
			sessionZam.setAttribute("ID_Uzytkownik", uzytkownik.getID_Uzytkownik());
			
			return "Admin?faces-redirect=true";
		} else if (uzytkownik.getLogin().equals("Mod") && uzytkownik.getHaslo().equals("Mod")) {
			
			uzytkownik.setID_Uzytkownik(-1);
			uzytkownik.setImie("Mod");
			uzytkownik.setNazwisko("Mod");
			uzytkownik.setNr_telefonu(222222222);
			uzytkownik.setMiejscowosc("Mod");
			uzytkownik.setKod_pocztowy(22222);
			uzytkownik.setUlica("Mod");
			uzytkownik.setNr_domu("Mod");
			
			client.getRoles().add("Mod");
			
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			client.store(request);
			
			sessionZam.setAttribute("ID_Uzytkownik", uzytkownik.getID_Uzytkownik());
			
			return "Mod?faces-redirect=true";
		}
		
			client.setDetails(uzytkownik);
		
		//List<String> roles = uzytkownikDAO.getRolesFromDB(uzytkownik);
		
		client.getRoles().add("User");
		
//		if (roles != null) {
//			for (String role: roles) {
//				r.getRoles().add(role);
//			}
//		} else {
//			context.addMessage(null,
//					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Puste roles", null));
//			return null;
//		}
		
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		client.store(request);
		
		zamowienieDAO.createOrder(uzytkownik.getID_Uzytkownik());

		zamowienie = zamowienieDAO.getOrder(uzytkownik.getID_Uzytkownik());
		
		sessionZam.setAttribute("ID_Zamowienie", zamowienie.getID_Zamowienie());
		sessionZam.setAttribute("ID_Uzytkownik", uzytkownik.getID_Uzytkownik());
		
		return "MainUserPage?faces-redirect=true";
	}
	
	
	public String logout() {
		
		HttpSession sessionZam = (HttpSession) extctx.getSession(false);
		
		ID_Uzytkownik = (Integer)sessionZam.getAttribute("ID_Uzytkownik"); 
		
		if (ID_Uzytkownik != -1) {
			
			ID_Zamowienie = (Integer) sessionZam.getAttribute("ID_Zamowienie");
			
			zamowienieDAO.deleteOrder(ID_Zamowienie);
		}
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
				.getSession(true);
		
		session.invalidate();
		
		return "Login?faces-redirect=true";
	}


	public String registrationScreen() {
		return "Registration?faces-redirect=true";
	}
}