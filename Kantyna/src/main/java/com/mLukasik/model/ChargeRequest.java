package com.mLukasik.model;

//@Data //z lomboka, zastepuje gettery, settery i pare innych
public class ChargeRequest
{
    private String description;
    private int amount;
    private String currency;
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
    
    public int getAmount()
	{
		return amount;
	}
	public void setAmount(int amount)
	{
		this.amount = amount;
	}
    
	public String getDescription() 
	{
		return description;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}
	public String getCurrency() 
	{
		return currency;
	}
	public void setCurrency(String currency) 
	{
		this.currency = currency;
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