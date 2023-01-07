import javax.inject.Inject;
import javax.inject.Named;

import pizzeria.entities.Uzytkownik;
import pizzeriaDAO.UzytkownikDAO;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;


@ViewScoped
@Named
public class RegistrationBB implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String haslo2;
	
	private Uzytkownik uzytkownik = new Uzytkownik();
	
	@EJB
	UzytkownikDAO uzytkownikDAO;

	@Inject
	FacesContext context;

	public String getHaslo2() {
		return haslo2;
	}

	public void setHaslo2(String haslo2) {
		this.haslo2 = haslo2;
	}

	public Uzytkownik getUzytkownik() {
		return uzytkownik;
	}
	
	public String register() {

		if (!uzytkownik.getHaslo().equals(haslo2)) {
			context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hasła nie są takie same", null));
			return null;
		} else if (uzytkownik.getLogin().equals("admin") && uzytkownik.getHaslo().equals("admin") || uzytkownik.getLogin().equals("mod") && uzytkownik.getHaslo().equals("mod")) {
			context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nie możesz użyć tego loginu i hasła", null));
			return null;
		} else if (uzytkownik.getLogin().equals(uzytkownikDAO.getLoginFromDB(uzytkownik.getLogin()))) {
			context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Taki login już istnieje", null));
			return null;
		}
		
		try {
			if (uzytkownik.getID_Uzytkownik() == 0) {
					uzytkownikDAO.create(uzytkownik);
			} else {
				context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Taki użytkownik już istnieje", null));
				return null;
			}
			
			uzytkownikDAO.insertUserRole(uzytkownik.getID_Uzytkownik());
	
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return null;
		}
		
		
		return "Login?faces-redirect=true";
	}
	
	public String loginScreen() {
		return "Login?faces-redirect=true";
	}
	
	public String testUserList() {
		return "userList?faces-redirect=true";
	}
}