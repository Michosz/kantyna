package com.mLukasik.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mLukasik.model.Uzytkownik;

@Entity
@Table(name="komentarze")
public class Komentarz 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_komentarza")
	private int id;
	
	@Column(name = "ocena")
	private int ocena;
	
	@Column(name = "komentarz")
	private String komentarz;
	
	@ManyToOne
	@JoinColumn(name = "id_uzytkownika", nullable = false)
	private Uzytkownik uzytkownik;
	
	@ManyToOne
	@JoinColumn(name = "id_potrawy", nullable = false)
	private Potrawa potrawa;
	
	@JsonIgnore
	@Transient
	private String jakaOcena;
	
	@Transient
	private int idUzytkownika;
	
	@Transient
	private int idPotrawy;
	
	@JsonIgnore
	@Transient
	private int idPomoc;
	
	public int getIdPomoc() {
		return idPomoc;
	}

	public void setIdPomoc(int idPomoc) {
		this.idPomoc = idPomoc;
	}

	public int getIdUzytkownika() 
	{
		return idUzytkownika;
	}

	public void setIdUzytkownika(int idUzytkownika) 
	{
		this.idUzytkownika = idUzytkownika;
	}

	public int getIdPotrawy() 
	{
		return idPotrawy;
	}

	public void setIdPotrawy(int idPotrawy) 
	{
		this.idPotrawy = idPotrawy;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public int getOcena() 
	{
		return ocena;
	}

	public void setOcena(int ocena) 
	{
		this.ocena = ocena;
	}

	public String getKomentarz() 
	{
		return komentarz;
	}

	public void setKomentarz(String komentarz) 
	{
		this.komentarz = komentarz;
	}

	public Uzytkownik getUzytkownik()
	{
		return uzytkownik;
	}

	public void setUzytkownik(Uzytkownik uzytkownik) 
	{
		this.uzytkownik = uzytkownik;
	}

	public Potrawa getPotrawa() 
	{
		return potrawa;
	}

	public void setPotrawa(Potrawa potrawa) 
	{
		this.potrawa = potrawa;
	}

	public String getJakaOcena() 
	{
		return jakaOcena;
	}

	public void setJakaOcena(String jakaOcena)
	{
		this.jakaOcena = jakaOcena;
	}
}