package com.models;

public class User
{
	private String username;	
	private String first;
	private String last;
	private Phone phone;
	
	public User(String username, String first, String last, Phone phone)
	{
		super();
		this.username = username;
		this.first = first;
		this.last = last;
		this.phone = phone;
	}
	
	public User()
	{
		
	}
	
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getFirst()
	{
		return first;
	}
	public void setFirst(String first)
	{
		this.first = first;
	}
	public String getLast()
	{
		return last;
	}
	public void setLast(String last)
	{
		this.last = last;
	}
	public Phone getPhone()
	{
		return phone;
	}
	public void setPhone(Phone phone)
	{
		this.phone = phone;
	}	
}
