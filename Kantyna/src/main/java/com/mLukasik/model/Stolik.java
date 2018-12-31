package com.mLukasik.model;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="stoliki")
public class Stolik 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_stolika")
	private int idStolika;
	
	@Column(name = "nazwa")
	private String nazwa;
	
	@Column(name = "ilosc_miejsc")
	private int iloscMiejsc;
	
	@Column(name = "czy_jest_zajety")
	private boolean czyJestZajety;
	
	/*
	@Column()
	private Time start;
	
	@Column()
	private Time koniec;
	
	@Transient
	private String czasStart
	*/

	public int getIdStolika()
	{
		return idStolika;
	}

	public void setIdStolika(int idStolika)
	{
		this.idStolika = idStolika;
	}

	public String getNazwa() 
	{
		return nazwa;
	}

	public void setNazwa(String nazwa) 
	{
		this.nazwa = nazwa;
	}

	public int getIloscMiejsc() 
	{
		return iloscMiejsc;
	}

	public void setIloscMiejsc(int iloscMiejsc)
	{
		this.iloscMiejsc = iloscMiejsc;
	}

	public boolean getCzyJestZajety()
	{
		return czyJestZajety;
	}

	public void setCzyJestZajety(boolean czyJestZajety) 
	{
		this.czyJestZajety = czyJestZajety;
	}
}
