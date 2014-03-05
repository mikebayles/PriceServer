package com.models;

import java.text.NumberFormat;

public class Alert
{
	private Price price;
	
	public Alert(Price price)
	{	
		this.price = price;	
	}
	
	public Alert()
	{
		
	}
	
	public Price getPrice()
	{
		return price;
	}
	public void setPrice(Price price)
	{
		this.price = price;
	}
	public String getText()
	{
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String moneyString = formatter.format(price.getValue());
		return String.format("%s has changed to %s", price.getBook().getTitle(),moneyString);
	}

	@Override
	public String toString()
	{
		return getText();
	}
		
}
