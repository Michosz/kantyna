package com.mLukasik.model;

import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="parametry")
public class Parametry 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_parametru")
	private int idParametru;
	
	@Column(name = "godzina_otwarcia")
	private Time godzinaOtwarcia;
	
	@Column(name = "godzina_zamkniecia")
	private Time godzinaZamkniecia;
	
	@Column(name = "szukanie_stolika")
	private int szukanieStolika;
	
	@Transient
	private String godzinaO;
	
	@Transient
	private String godzinaZ;

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

	public Time getGodzinaOtwarcia()
	{
		return godzinaOtwarcia;
	}

	public void setGodzinaOtwarcia(Time godzinaOtwarcia) 
	{
		this.godzinaOtwarcia = godzinaOtwarcia;
	}

	public Time getGodzinaZamkniecia() 
	{
		return godzinaZamkniecia;
	}

	public void setGodzinaZamkniecia(Time godzinaZamkniecia) 
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
