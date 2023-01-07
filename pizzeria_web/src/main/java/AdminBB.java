import javax.inject.Inject;
import javax.inject.Named;

import pizzeria.entities.Uzytkownik;
import pizzeriaDAO.UzytkownikDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

@Named
@RequestScoped
//@SessionScoped
public class AdminBB {
	
	private Integer ID_Uzytkownik;
	private Integer ID_Rola; 
	
	@EJB
	UzytkownikDAO uzytkownikDAO;	
	
	public Integer getID_Uzytkownik() {
		return ID_Uzytkownik;
	}

	public void setID_Uzytkownik(Integer iD_Uzytkownik) {
		ID_Uzytkownik = iD_Uzytkownik;
	}

	public Integer getID_Rola() {
		return ID_Rola;
	}

	public void setID_Rola(Integer iD_Rola) {
		ID_Rola = iD_Rola;
	}

	public List<Uzytkownik> getFullList(){
		return uzytkownikDAO.getFullList();
	}

	public List<Uzytkownik> userRoleList(){
		
		List<Uzytkownik> list = null;
		
		
		
		return list;
	}
}