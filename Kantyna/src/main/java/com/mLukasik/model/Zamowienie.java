package com.mLukasik.model;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.Fetch;

import org.hibernate.annotations.Type;

@Entity
@Table(name="zamowienia")
public class Zamowienie 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_zamowienia")
	private int id;
	
	@Column(name = "data_zamowienia")
	@Type(type = "date") //zeby wyswietlalo tylko date,bez godziny
	private Date dataZam ;
	
	@Column(name = "czy_manager_je_widzial")
	private boolean czyManagerJeWidzial;

	@Column(name = "czy_zrealizowane")
	private boolean czyZrealizowane;
	
	@Column(name = "czas_realizacji")
	private LocalTime czasRealizacji;

	@ManyToOne 
	@JoinColumn(name = "id_stolika", nullable = false)
	private Stolik stolik;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_uzytkownika", nullable = false)
	private Uzytkownik uzytkownik;
	
	@OneToMany(mappedBy = "zamowienie", cascade=CascadeType.ALL) //mappedBy = cos - nazwa obiektu w innej klasie
	//cascade all - zapisujesz wszystko co powiazane z ta klasa do bazy
	private List<Potrawy_Zamowienia> potrawy_Zamowienia = new ArrayList<Potrawy_Zamowienia>();
	
	@Transient
	int koszyk;
	
	@Transient
	int iloscMiejsc;
	
	@Transient
	String czasReal;

	public LocalTime getCzasRealizacji() 
	{
		return czasRealizacji;
	}

	public void setCzasRealizacji(LocalTime czasRealizacji)
	{
		this.czasRealizacji = czasRealizacji;
	}
	
	public String getCzasReal() 
	{
		return czasReal;
	}

	public void setCzasReal(String czasReal) 
	{
		this.czasReal = czasReal;
	}
	
	public int getIloscMiejsc() 
	{
		return iloscMiejsc;
	}

	public void setIloscMiejsc(int iloscMiejsc) 
	{
		this.iloscMiejsc = iloscMiejsc;
	}

	public List<Potrawy_Zamowienia> getPotrawy_Zamowienia() 
	{
		return potrawy_Zamowienia;
	}

	public void setPotrawy_Zamowienia(List<Potrawy_Zamowienia> potrawy_Zamowienia)
	{
		this.potrawy_Zamowienia = potrawy_Zamowienia;
	}

	public int getKoszyk() 
	{
		return koszyk;
	}

	public void setKoszyk(int koszyk) {
		this.koszyk = koszyk;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public Date getDataZam() 
	{
		return dataZam;
	}

	public void setDataZam(Date dataZam)
	{
		this.dataZam = dataZam;
	}
	
	public boolean getCzyZrealizowane() 
	{
		return czyZrealizowane;
	}

	public void setCzyZrealizowane(boolean czyZrealizowane)
	{
		this.czyZrealizowane = czyZrealizowane;
	}

	public boolean isCzyManagerJeWidzial() 
	{
		return czyManagerJeWidzial;
	}

	public void setCzyManagerJeWidzial(boolean czyManagerJeWidzial) 
	{
		this.czyManagerJeWidzial = czyManagerJeWidzial;
	}

	public Stolik getStolik() 
	{
		return stolik;
	}

	public void setStolik(Stolik stolik) 
	{
		this.stolik = stolik;
	}

	public Uzytkownik getUzytkownik() 
	{
		return uzytkownik;
	}

	public void setUzytkownik(Uzytkownik uzytkownik) 
	{
		this.uzytkownik = uzytkownik;
	}
}