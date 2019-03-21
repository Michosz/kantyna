package com.mLukasik.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="potrawy")
public class Potrawa 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_potrawy")
	private int id;
	
	@Column(name = "cena", scale=2)
	private double cena;
	
	@Column(name = "czy_jest_dostepna")
	private boolean czyJestDostepna;

	//@JsonIgnore
	@Column(name = "zdjecie")
	private byte[] zdjecie;
	
	@Column(name = "nazwa")
	private String nazwa;
	
	@Column(name = "czy_promocja")
	//@ColumnDefault("false") //upewnic sie czy ta adnotacja jest potrzebna
	private boolean czyPromocja;
	
	@Column(name = "cena_promocyjna", scale=2)
	//@ColumnDefault("0")
	private double cenaPromocyjna;
	@JsonIgnore
	@Transient
	private MultipartFile obrazek;
	@JsonIgnore
	@Transient
	private String base64;
	@JsonIgnore
	@Transient
	private String rodzajPot;
	
	@ManyToOne
	@JoinColumn(name = "id_rodzaju", nullable = false)
	private RodzajPotrawy rodzajPotrawy;
	
	@JsonIgnore
	//@JsonIgnoreProperties("potrawa") // zeby nie wpadalo w nieskonczona petle i wyswietlalo komentarze
	@OneToMany(mappedBy="potrawa")
	private List<Komentarz> listaKomentarzy = new ArrayList<Komentarz>();
	//dodac one to one (nie pamietam o co chodzilo)

	/*@ManyToMany(mappedBy = "listaPotraw")
    private List<Zamowienie> listaZamowien = new ArrayList<Zamowienie>();*/
	@JsonIgnore
	@OneToMany(mappedBy="potrawa")
	private List<Potrawy_Zamowienia> potrawy_zamowienia = new ArrayList<Potrawy_Zamowienia>();
	
	public List<Potrawy_Zamowienia> getPotrawy_zamowienia() 
	{
		return potrawy_zamowienia;
	}
	
	public void setPotrawy_zamowienia(List<Potrawy_Zamowienia> potrawy_zamowienia)
	{
		this.potrawy_zamowienia = potrawy_zamowienia;
	}

	public List<Komentarz> getListaKomentarzy() 
	{
		return listaKomentarzy;
	}

	public void setListaKomentarzy(List<Komentarz> listaKomentarzy) 
	{
		this.listaKomentarzy = listaKomentarzy;
	}

	public String getNazwa() 
	{
		return nazwa;
	}
	
	public void setNazwa(String nazwa) 
	{
		this.nazwa = nazwa;
	}
	
	public String getRodzajPot()
	{
		return rodzajPot;
	}

	public void setRodzajPot(String rodzajPot) 
	{
		this.rodzajPot = rodzajPot;
	}

	public String getBase64() 
	{
		return base64;
	}

	public void setBase64(String base64) 
	{
		this.base64 = base64;
	}
	
	public RodzajPotrawy getRodzajPotrawy()
	{
		return rodzajPotrawy;
	}
	
	public void setRodzajPotrawy(RodzajPotrawy rodzaj) 
	{
		this.rodzajPotrawy = rodzaj;
	}
	
	public MultipartFile getObrazek()
	{
		return obrazek;
	}
	
	public void setObrazek(MultipartFile obr)
	{
		this.obrazek = obr;
	}
	
	public int getId()
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public double getCena() 
	{
		return cena;
	}

	public void setCena(double cena)
	{
		this.cena = cena;
	}

	public boolean getCzyJestDostepna() 
	{
		return czyJestDostepna;
	}

	public void setCzyJestDostepna(boolean czyJestDostepna) 
	{
		this.czyJestDostepna = czyJestDostepna;
	}

	public byte[] getZdjecie() 
	{
		return zdjecie;
	}

	public void setZdjecie(byte[] zdjecie)
	{	
		this.zdjecie = zdjecie;
	}

	public boolean getCzyPromocja() 
	{
		return czyPromocja;
	}

	public void setCzyPromocja(boolean czyPromocja) 
	{
		this.czyPromocja = czyPromocja;
	}

	public double getCenaPromocyjna() 
	{
		return cenaPromocyjna;
	}

	public void setCenaPromocyjna(double cenaPromocyjna) 
	{
		this.cenaPromocyjna = cenaPromocyjna;
	}
}