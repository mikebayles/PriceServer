package com.models;

import java.util.Date;

public class Price
{
	private Book book;
	private Date date;
	private double value;
	
	public Price(Book book, Date date, double value)
	{		
		this.book = book;
		this.date = date;
		this.value = value;
	}

	public Book getBook()
	{
		return book;
	}

	public void setBook(Book book)
	{
		this.book = book;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public double getValue()
	{
		return value;
	}

	public void setValue(double value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		return String.format("Book = %s, Date = %s, Price = %s",book.toString(),date,value);
	}
	
	
}
