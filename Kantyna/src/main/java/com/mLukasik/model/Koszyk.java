package com.mLukasik.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="koszyk")
public class Koszyk 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "ilosc")
	private int ilosc;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_potrawy", nullable = false)
	private Potrawa potrawa;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_uzytkownika", nullable = false)
	private Uzytkownik uzytkownik;
	
	@Transient
	private int idPotrawy;

	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public int getIlosc() 
	{
		return ilosc;
	}

	public void setIlosc(int ilosc) 
	{
		this.ilosc = ilosc;
	}

	public Potrawa getPotrawa()
	{
		return potrawa;
	}

	public void setPotrawa(Potrawa potrawa) 
	{
		this.potrawa = potrawa;
	}

	public Uzytkownik getUzytkownik() 
	{
		return uzytkownik;
	}

	public void setUzytkownik(Uzytkownik uzytkownik) 
	{
		this.uzytkownik = uzytkownik;
	}
	
	public int getIdPotrawy() 
	{
		return idPotrawy;
	}

	public void setIdPotrawy(int idPotrawy) 
	{
		this.idPotrawy = idPotrawy;
	}
}