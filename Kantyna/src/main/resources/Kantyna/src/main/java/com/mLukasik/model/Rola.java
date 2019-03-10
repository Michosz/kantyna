package com.mLukasik.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="role")
public class Rola 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_roli")
	private int id;
	
	@Column(name = "rola")
	private String rola;

	public int getId()
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public String getRola() 
	{
		return rola;
	}

	public void setRola(String rola) 
	{
		this.rola = rola;
	}
}
