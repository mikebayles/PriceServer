package com.models;

public class Phone
{
	private String number;
	private String carrierAddress;
	
	public Phone()
	{
		
	}
	
	public Phone(String number, String carrier)
	{		
		this.number = number;
		this.carrierAddress = carrier;
	}
	
	public String getNumber()
	{
		return number;
	}
	public void setNumber(String number)
	{
		this.number = number;
	}
	public String getCarrierAddress()
	{
		return carrierAddress;
	}
	public void setCarrierAddress(String carrierAddress)
	{
		this.carrierAddress = carrierAddress;
	}	
}
