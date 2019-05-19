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

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="uzytkownicy")
public class Uzytkownik 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_uzytkownika")
	private int id;
	
	@ManyToOne 
	@JoinColumn(name = "id_roli", nullable = false)
	private Rola rola;
	
	@Column(name = "imie")
	private String imie;
	
	@Column(name = "nazwisko")
	private String nazwisko;
	
	@Column(name = "login")
	private String login;

	@Column(name = "haslo")
	private String haslo;
	
	@Column(name = "telefon")
	private String telefon;
	
	@Column(name = "czy_aktywny")
	private boolean czyAktywny;
	
	@Column(name = "komentarz" )
	private String komentarz;
    
	@JsonIgnore
	@Transient
	private String rolaa;
	
	public String getRolaa()
	{
		return rolaa;
	}

	public void setRolaa(String rolaa)
	{
		this.rolaa = rolaa;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public Rola getRola()
	{
		return rola;
	}
	
	public void setRola(Rola rola) 
	{
		this.rola = rola;
	}
	
	public String getImie() 
	{
		return imie;
	}

	public void setImie(String imie)
	{
		this.imie = imie;
	}

	public String getNazwisko() 
	{
		return nazwisko;
	}

	public void setNazwisko(String nazwisko) 
	{
		this.nazwisko = nazwisko;
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public String getHaslo()
	{
		return haslo;
	}

	public void setHaslo(String haslo)
	{
		this.haslo = haslo;
	}

	public String getTelefon() 
	{
		return telefon;
	}

	public void setTelefon(String telefon) 
	{
		this.telefon = telefon;
	}

	public boolean getCzyAktywny()
	{
		return czyAktywny;
	}

	public void setCzyAktywny(boolean czyAktywny) 
	{
		this.czyAktywny = czyAktywny;
	}
	
	public String getKomentarz() 
	{
		return komentarz;
	}

	public void setKomentarz(String komentarz) 
	{
		this.komentarz = komentarz;
	}
}