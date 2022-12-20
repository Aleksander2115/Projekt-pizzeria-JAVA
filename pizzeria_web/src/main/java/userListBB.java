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
public class userListBB {
	
	private String nazwisko;	
	
	@EJB
	UzytkownikDAO uzytkownikDAO;
	
	public String getNazwisko() {
		return nazwisko;
	}

	public void setSurname(String surname) {
		this.nazwisko = surname;
	}	
	
	public List<Uzytkownik> getFullList(){
		return uzytkownikDAO.getFullList();
	}

	public List<Uzytkownik> getList(){
		List<Uzytkownik> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (nazwisko != null && nazwisko.length() > 0){
			searchParams.put("surname", nazwisko);
		}
		
		//2. Get list
		list = uzytkownikDAO.getList(searchParams);
		
		return list;
	}
}