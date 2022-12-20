package pizzeria.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the dodatek database table.
 * 
 */
@Entity
@NamedQuery(name="Dodatek.findAll", query="SELECT d FROM Dodatek d")
public class Dodatek implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int ID_Dodatek;

	private String nazwa;

	private String rodzaj;

	//bi-directional many-to-many association to Zamowienie
	@ManyToMany(mappedBy="dodateks")
	private List<Zamowienie> zamowienies;

	public Dodatek() {
	}

	public int getID_Dodatek() {
		return this.ID_Dodatek;
	}

	public void setID_Dodatek(int ID_Dodatek) {
		this.ID_Dodatek = ID_Dodatek;
	}

	public String getNazwa() {
		return this.nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getRodzaj() {
		return this.rodzaj;
	}

	public void setRodzaj(String rodzaj) {
		this.rodzaj = rodzaj;
	}

	public List<Zamowienie> getZamowienies() {
		return this.zamowienies;
	}

	public void setZamowienies(List<Zamowienie> zamowienies) {
		this.zamowienies = zamowienies;
	}

}