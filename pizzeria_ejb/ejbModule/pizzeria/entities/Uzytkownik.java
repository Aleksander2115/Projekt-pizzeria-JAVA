package pizzeria.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the uzytkownik database table.
 * 
 */
@Entity
@NamedQuery(name="Uzytkownik.findAll", query="SELECT u FROM Uzytkownik u")
public class Uzytkownik implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int ID_Uzytkownik;

	private String haslo;

	private String imie;

	private int kod_pocztowy;

	private String login;

	private String miejscowosc;

	private String nazwisko;

	private String nr_domu;

	private int nr_telefonu;

	private String ulica;

	//bi-directional many-to-many association to Rola
	@ManyToMany
	@JoinTable(
		name="uzytkownik_rola"
		, joinColumns={
			@JoinColumn(name="ID_Uzytkownik")
			}
		, inverseJoinColumns={
			@JoinColumn(name="ID_Rola")
			}
		)
	private List<Rola> rolas;

	//bi-directional many-to-one association to Zamowienie
	@OneToMany(mappedBy="uzytkownik")
	private List<Zamowienie> zamowienies;

	public Uzytkownik() {
	}

	public int getID_Uzytkownik() {
		return this.ID_Uzytkownik;
	}

	public void setID_Uzytkownik(int ID_Uzytkownik) {
		this.ID_Uzytkownik = ID_Uzytkownik;
	}

	public String getHaslo() {
		return this.haslo;
	}

	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}

	public String getImie() {
		return this.imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public int getKod_pocztowy() {
		return this.kod_pocztowy;
	}

	public void setKod_pocztowy(int kod_pocztowy) {
		this.kod_pocztowy = kod_pocztowy;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMiejscowosc() {
		return this.miejscowosc;
	}

	public void setMiejscowosc(String miejscowosc) {
		this.miejscowosc = miejscowosc;
	}

	public String getNazwisko() {
		return this.nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public String getNr_domu() {
		return this.nr_domu;
	}

	public void setNr_domu(String nr_domu) {
		this.nr_domu = nr_domu;
	}

	public int getNr_telefonu() {
		return this.nr_telefonu;
	}

	public void setNr_telefonu(int nr_telefonu) {
		this.nr_telefonu = nr_telefonu;
	}

	public String getUlica() {
		return this.ulica;
	}

	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

	public List<Rola> getRolas() {
		return this.rolas;
	}

	public void setRolas(List<Rola> rolas) {
		this.rolas = rolas;
	}

	public List<Zamowienie> getZamowienies() {
		return this.zamowienies;
	}

	public void setZamowienies(List<Zamowienie> zamowienies) {
		this.zamowienies = zamowienies;
	}

	public Zamowienie addZamowieny(Zamowienie zamowieny) {
		getZamowienies().add(zamowieny);
		zamowieny.setUzytkownik(this);

		return zamowieny;
	}

	public Zamowienie removeZamowieny(Zamowienie zamowieny) {
		getZamowienies().remove(zamowieny);
		zamowieny.setUzytkownik(null);

		return zamowieny;
	}

}