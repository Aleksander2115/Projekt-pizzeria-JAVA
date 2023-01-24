import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Session;
import javax.naming.ldap.ManageReferralControl;
import javax.persistence.EntityManager;

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
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.simplesecurity.RemoteClient;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Named
@ViewScoped
public class ModBB implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EJB
	ZamowienieDAO zamowienieDAO;	

	public List<Zamowienie> wholeOrderList(){
		
		List<Zamowienie> list = null;
		
		list = zamowienieDAO.getWholeOrderList();
		
		return list;
	}
	
	public void deleteOrder(Zamowienie zamowienie) {
		
		zamowienieDAO.deleteOrder(zamowienie.getID_Zamowienie(), zamowienie.getStatus());
	}
}