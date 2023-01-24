import pizzeria.entities.Rola;
import pizzeria.entities.Uzytkownik;
import pizzeriaDAO.UzytkownikDAO;
import pizzeriaDAO.ZamowienieDAO;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Session;
import javax.naming.ldap.ManageReferralControl;
import javax.persistence.EntityManager;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.simplesecurity.RemoteClient;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;


@Named
@ViewScoped
public class AdminBB implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int ID_Uzytkownik;
	
	@Inject
	FacesContext context;
	
	@Inject
	ExternalContext extctx;
	
	@EJB
	UzytkownikDAO uzytkownikDAO;
	
	Uzytkownik uzytkownik = new Uzytkownik();
	
	private LazyDataModel<Uzytkownik> lazyModel;

    public LazyDataModel<Uzytkownik> getLazyModel() {
        return lazyModel;
    }

	public List<Uzytkownik> getUserWithRoles(){
		
		List<Uzytkownik> list = null;
		
		list = uzytkownikDAO.getUserWithRoles();
		
		return list;
	}
	
	public void changeToAdmin(Uzytkownik uzytkownik) {

		HttpSession sessionZam = (HttpSession) extctx.getSession(false);
		ID_Uzytkownik = (Integer) sessionZam.getAttribute("ID_Uzytkownik");
		
		uzytkownikDAO.changeToAdmin(ID_Uzytkownik);
	}
	
	public void changeToMod(Uzytkownik uzytkownik) {
		
		HttpSession sessionZam = (HttpSession) extctx.getSession(false);
		ID_Uzytkownik = (Integer) sessionZam.getAttribute("ID_Uzytkownik");
		
		uzytkownikDAO.changeToMod(ID_Uzytkownik);
	}
	
	public void changeToUser(Uzytkownik uzytkownik) {
		
		HttpSession sessionZam = (HttpSession) extctx.getSession(false);
		ID_Uzytkownik = (Integer) sessionZam.getAttribute("ID_Uzytkownik");
		
		uzytkownikDAO.changeToUser(ID_Uzytkownik);
	}
	
	@PostConstruct
    public void init() {
        lazyModel = new LazyDataModel<Uzytkownik>() {
            //Potrzebna jest tylko metoda load
            @Override
            public List<Uzytkownik> load(int first, int pageSize, Map<String, SortMeta> sortBy,
                    Map<String, FilterMeta> filterBy) {

                List<Uzytkownik> products = uzytkownikDAO.getListLazy(first, pageSize);
                setRowCount(uzytkownikDAO.getFullList().size());
                return products;
            }
        };
    }
}