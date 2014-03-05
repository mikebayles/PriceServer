package com.models;


public class Book
{
	private String id;
	private String title;
	private String author;	
	private String url;
	private String image;
			
	public Book(String id, String title, String author, String url, String image)
	{	
		this.id = id;
		this.title = title;
		this.author = author;		
		this.url = url;
		this.image = image;
	}
	
	public Book()
	{
		
	}
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getAuthor()
	{
		return author;
	}
	public void setAuthor(String author)
	{
		this.author = author;
	}
	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	@Override
	public String toString()
	{
		return "Book [id=" + id + ", title=" + title + ", author=" + author + "]";
	}

	@Override
    public boolean equals(Object obj) 
	{
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof Book))
            return false;

        Book other = (Book) obj;
        return other.id == id;             
    }
}
