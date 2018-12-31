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

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name="potrawy")
public class Potrawa 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_potrawy")
	private int id;
	
	@Column(name = "cena")
	private int cena;
	
	@Column(name = "czy_jest_dostepna")
	private boolean czyJestDostepna;
	
	@Column(name = "zdjecie")
	private byte[] zdjecie;
	
	@Column(name = "nazwa")
	private String nazwa;

	@Transient
	private MultipartFile obrazek;
	
	@Transient
	private String base64;
	
	@Transient
	private String rodzajPot;
	
	@ManyToOne 
	@JoinColumn(name = "id_rodzaju", nullable = false)
	private RodzajPotrawy rodzajPotrawy;
	
	//dodac one to one (nie pamietam o co chodzilo)
	
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

	public int getCena() 
	{
		return cena;
	}

	public void setCena(int cena)
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
}
