import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Session;
import javax.naming.ldap.ManageReferralControl;
import javax.persistence.EntityManager;

import pizzeria.entities.Rola;
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
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.annotation.ManagedProperty;
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
	
	@Inject
	@ManagedProperty("#{txtLogErr}")
	private ResourceBundle txtLogErr;
	
	@Inject
	@ManagedProperty("#{txtLog}")
	private ResourceBundle txtLog;

	public Uzytkownik getUzytkownik() {
		return uzytkownik;
	}
	
	public Zamowienie getZamowienie() {
		return zamowienie;
	}

	public String login() {	
		
		RemoteClient<Uzytkownik> client = new RemoteClient<Uzytkownik>();
		HttpSession sessionZam = (HttpSession) extctx.getSession(true);
				
		Uzytkownik uzytkownik = new Uzytkownik();
		
		uzytkownik = uzytkownikDAO.getUserFromDB(this.uzytkownik.getLogin(), this.uzytkownik.getHaslo());
		
		if (uzytkownik == null) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, txtLogErr.getString("loginUserExists"), null));
			return null;
		}  
		
		if (uzytkownik.getRolas() != null) {
			for (Rola role: uzytkownik.getRolas()) {
				client.getRoles().add(role.getNazwa_roli());
			}
		} else {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Puste roles", null));
			return null;
		}
		
		client.setDetails(uzytkownik);
		
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		client.store(request);
		
		sessionZam.setAttribute("Uzytkownik", uzytkownik);
		
		if (uzytkownik.getLogin().equals("Mod")) {	
			return "Mod?faces-redirect=true";
		}
		
		if (uzytkownik.getLogin().equals("Admin")) {
			return "Admin?faces-redirect=true";
		}
							
		zamowienie = zamowienieDAO.getCart(uzytkownik);
		
		sessionZam.setAttribute("Zamowienie", getZamowienie());
		
		return "MainUserPage?faces-redirect=true";
	}
	
	
	public String logout() {
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
				.getSession(true);
		
		session.invalidate();
		
		return "Login?faces-redirect=true";
	}


	public String registrationScreen() {
		return "Registration?faces-redirect=true";
	}
}