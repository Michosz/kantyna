package com.mLukasik.model;

import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="parametry")
public class Parametry 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_parametru")
	private int idParametru;
	
	@Column(name = "godzina_otwarcia")
	private LocalTime godzinaOtwarcia;
	
	@Column(name = "godzina_zamkniecia")
	private LocalTime godzinaZamkniecia;
	
	@Column(name = "szukanie_stolika")
	private int szukanieStolika;
	
	@Column(name = "czy_zamkniete")
	private boolean czyZamkniete;
	
	@Column(name = "czy_auto_zwalniac")
	private boolean czyAutoZwalniac;
	
	@Column(name = "co_ile_zwalniac")
	private LocalTime coIleZwalniac;

	@Transient
	@JsonIgnore
	private String godzinaO;
	
	@Transient
	@JsonIgnore
	private String godzinaZ;
	
	@Transient
	@JsonIgnore
	private String zwalnianie;
	
	public boolean getCzyAutoZwalniac() 
	{
		return czyAutoZwalniac;
	}

	public void setCzyAutoZwalniac(boolean czyAutoZwalniac) 
	{
		this.czyAutoZwalniac = czyAutoZwalniac;
	}

	public LocalTime getCoIleZwalniac()
	{
		return coIleZwalniac;
	}

	public void setCoIleZwalniac(LocalTime coIleZwalniac) 
	{
		this.coIleZwalniac = coIleZwalniac;
	}

	public String getZwalnianie() 
	{
		return zwalnianie;
	}

	public void setZwalnianie(String zwalnianie) 
	{
		this.zwalnianie = zwalnianie;
	}

	public String getGodzinaO() 
	{
		return godzinaO;
	}

	public void setGodzinaO(String godzinaO) 
	{
		this.godzinaO = godzinaO;
	}

	public String getGodzinaZ() 
	{
		return godzinaZ;
	}

	public void setGodzinaZ(String godzinaZ) 
	{
		this.godzinaZ = godzinaZ;
	}

	public int getIdParametru() 
	{
		return idParametru;
	}

	public void setIdParametru(int id) 
	{
		this.idParametru = id;
	}

	public LocalTime getGodzinaOtwarcia()
	{
		return godzinaOtwarcia;
	}

	public boolean getCzyZamkniete() 
	{
		return czyZamkniete;
	}

	public void setCzyZamkniete(boolean czyZamkniete)
	{
		this.czyZamkniete = czyZamkniete;
	}
	
	public void setGodzinaOtwarcia(LocalTime godzinaOtwarcia) 
	{
		this.godzinaOtwarcia = godzinaOtwarcia;
	}

	public LocalTime getGodzinaZamkniecia() 
	{
		return godzinaZamkniecia;
	}

	public void setGodzinaZamkniecia(LocalTime godzinaZamkniecia) 
	{
		this.godzinaZamkniecia = godzinaZamkniecia;
	}

	public int getSzukanieStolika() 
	{
		return szukanieStolika;
	}

	public void setSzukanieStolika(int szukaneStoliki) 
	{
		this.szukanieStolika = szukaneStoliki;
	}
}
