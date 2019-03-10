package com.mLukasik.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rodzaje_potraw")
public class RodzajPotrawy 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_rodzaju")
	private int id;
	
	@Column(name = "rodzaj")
	private String rodzaj;

	@Override
	public boolean equals(Object obiekt)
	{
	    final RodzajPotrawy rodz = (RodzajPotrawy) obiekt;
	    if(!(obiekt instanceof RodzajPotrawy))
	    {
	    	return false;
	    }
	    else if(this.getId() == rodz.getId())
        {
        	return true;
        }
        else
        {
        	return false;
        }
	}
	
	@Override
	public int hashCode() 
	{
		return this.getId();
	}
	
	public int getId()
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public String getRodzaj() 
	{
		return rodzaj;
	}

	public void setRodzaj(String rodzaj) 
	{
		this.rodzaj = rodzaj;
	}
}
