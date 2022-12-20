package pizzeria.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the pizza database table.
 * 
 */
@Entity
@NamedQuery(name="Pizza.findAll", query="SELECT p FROM Pizza p")
public class Pizza implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int ID_Pizza;

	private String cena;

	private String nazwa;

	private String rozmiar;

	//bi-directional many-to-many association to Zamowienie
	@ManyToMany(mappedBy="pizzas")
	private List<Zamowienie> zamowienies;

	public Pizza() {
	}

	public int getID_Pizza() {
		return this.ID_Pizza;
	}

	public void setID_Pizza(int ID_Pizza) {
		this.ID_Pizza = ID_Pizza;
	}

	public String getCena() {
		return this.cena;
	}

	public void setCena(String cena) {
		this.cena = cena;
	}

	public String getNazwa() {
		return this.nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getRozmiar() {
		return this.rozmiar;
	}

	public void setRozmiar(String rozmiar) {
		this.rozmiar = rozmiar;
	}

	public List<Zamowienie> getZamowienies() {
		return this.zamowienies;
	}

	public void setZamowienies(List<Zamowienie> zamowienies) {
		this.zamowienies = zamowienies;
	}

}