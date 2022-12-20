package pizzeria.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the rola database table.
 * 
 */
@Entity
@NamedQuery(name="Rola.findAll", query="SELECT r FROM Rola r")
public class Rola implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int ID_Rola;

	@Temporal(TemporalType.TIMESTAMP)
	private Date data_nadania;

	@Temporal(TemporalType.TIMESTAMP)
	private Date data_odebrania;

	private String nazwa_roli;

	//bi-directional many-to-many association to Uzytkownik
	@ManyToMany(mappedBy="rolas")
	private List<Uzytkownik> uzytkowniks;

	public Rola() {
	}

	public int getID_Rola() {
		return this.ID_Rola;
	}

	public void setID_Rola(int ID_Rola) {
		this.ID_Rola = ID_Rola;
	}

	public Date getData_nadania() {
		return this.data_nadania;
	}

	public void setData_nadania(Date data_nadania) {
		this.data_nadania = data_nadania;
	}

	public Date getData_odebrania() {
		return this.data_odebrania;
	}

	public void setData_odebrania(Date data_odebrania) {
		this.data_odebrania = data_odebrania;
	}

	public String getNazwa_roli() {
		return this.nazwa_roli;
	}

	public void setNazwa_roli(String nazwa_roli) {
		this.nazwa_roli = nazwa_roli;
	}

	public List<Uzytkownik> getUzytkowniks() {
		return this.uzytkowniks;
	}

	public void setUzytkowniks(List<Uzytkownik> uzytkowniks) {
		this.uzytkowniks = uzytkowniks;
	}

}