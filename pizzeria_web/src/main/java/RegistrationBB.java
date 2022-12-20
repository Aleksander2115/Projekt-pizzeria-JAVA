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

@Named

public class RegistrationBB implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Uzytkownik uzytkownik = new Uzytkownik();
	
	@EJB
	UzytkownikDAO uzytkownikDAO;

	@Inject
	FacesContext context;
	
	public Uzytkownik getUzytkownik() {
		return uzytkownik;
	}
	
	public String register() {
		
		try {
			if (uzytkownik.getID_Uzytkownik() == 0) {
					uzytkownikDAO.create(uzytkownik);
				
			} else {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Taki użytkownik już istnieje", null));
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return null;
		}
		
		return "Login?faces-redirect=true";
	}
	
	public String loginScreen() {
		return "personEdit?faces-redirect=true";
	}
}