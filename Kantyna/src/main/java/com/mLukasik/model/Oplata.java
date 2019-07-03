package com.mLukasik.model;

//@Data //z lomboka, zastepuje gettery, settery i pare innych
public class Oplata
{
    private String opis;
    private int suma;
    private String waluta;
    private String stripeEmail;
    private String stripeToken;
    private int stripeNum;
    
    public int getStripeNum()
    {
    	return this.stripeNum;
    }
    public void setStripeNum(int id)
    {
    	this.stripeNum = id;
    }
    
    public int getSuma()
	{
		return suma;
	}
	public void setSuma(int amount)
	{
		this.suma = amount;
	}
    
	public String getOpis() 
	{
		return opis;
	}
	public void setOpis(String description) 
	{
		this.opis = description;
	}
	public String getWaluta() 
	{
		return waluta;
	}
	public void setWaluta(String currency) 
	{
		this.waluta = currency;
	}
	public String getStripeEmail() 
	{
		return stripeEmail;
	}
	public void setStripeEmail(String stripeEmail)
	{
		this.stripeEmail = stripeEmail;
	}
	public String getStripeToken() 
	{
		return stripeToken;
	}
	public void setStripeToken(String stripeToken) 
	{
		this.stripeToken = stripeToken;
	}
}