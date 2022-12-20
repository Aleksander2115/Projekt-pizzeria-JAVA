package pizzeria.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the zamowienie database table.
 * 
 */
@Entity
@NamedQuery(name="Zamowienie.findAll", query="SELECT z FROM Zamowienie z")
public class Zamowienie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int ID_Zamowienie;

	private int cena_dostawy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date data_zamowienia;

	private int koszt_calkowity;

	private int status;

	//bi-directional many-to-one association to Uzytkownik
	@ManyToOne
	@JoinColumn(name="ID_Uzytkownik")
	private Uzytkownik uzytkownik;

	//bi-directional many-to-many association to Dodatek
	@ManyToMany
	@JoinTable(
		name="zamowienie_dodatek"
		, joinColumns={
			@JoinColumn(name="ID_Zamowienie")
			}
		, inverseJoinColumns={
			@JoinColumn(name="ID_Dodatek")
			}
		)
	private List<Dodatek> dodateks;

	//bi-directional many-to-many association to Pizza
	@ManyToMany
	@JoinTable(
		name="zamowienie_pizza"
		, joinColumns={
			@JoinColumn(name="ID_Zamowienie")
			}
		, inverseJoinColumns={
			@JoinColumn(name="ID_Pizza")
			}
		)
	private List<Pizza> pizzas;

	public Zamowienie() {
	}

	public int getID_Zamowienie() {
		return this.ID_Zamowienie;
	}

	public void setID_Zamowienie(int ID_Zamowienie) {
		this.ID_Zamowienie = ID_Zamowienie;
	}

	public int getCena_dostawy() {
		return this.cena_dostawy;
	}

	public void setCena_dostawy(int cena_dostawy) {
		this.cena_dostawy = cena_dostawy;
	}

	public Date getData_zamowienia() {
		return this.data_zamowienia;
	}

	public void setData_zamowienia(Date data_zamowienia) {
		this.data_zamowienia = data_zamowienia;
	}

	public int getKoszt_calkowity() {
		return this.koszt_calkowity;
	}

	public void setKoszt_calkowity(int koszt_calkowity) {
		this.koszt_calkowity = koszt_calkowity;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Uzytkownik getUzytkownik() {
		return this.uzytkownik;
	}

	public void setUzytkownik(Uzytkownik uzytkownik) {
		this.uzytkownik = uzytkownik;
	}

	public List<Dodatek> getDodateks() {
		return this.dodateks;
	}

	public void setDodateks(List<Dodatek> dodateks) {
		this.dodateks = dodateks;
	}

	public List<Pizza> getPizzas() {
		return this.pizzas;
	}

	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}

}