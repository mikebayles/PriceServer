package com.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.models.Book;
import com.models.Phone;
import com.models.Price;
import com.models.User;

public class MySQLHelper
{
	private static MySQLHelper instance;	
	private Connection con = null;
	
	private MySQLHelper()
	{
		//PropertiesHelper prop = PropertiesHelper.getInstance();
		
		String url ="rei.cs.ndsu.nodak.edu";//prop.getValue("dbURL");
		String user = "mbayles";//prop.getValue("dbUser");
		String password = "Zz8N9aYXt6";//prop.getValue("dbPassword");		
		String connectionString = String.format("jdbc:mysql://%s:3306/%s", url,user);
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(connectionString, user, password);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
				
	}
	
	public static synchronized MySQLHelper getInstance()
	{
		if(instance == null)
			return new MySQLHelper();
		
		return instance;
	}
	
	private ResultSet executeSelect(String query)
	{
		try
		{
			return con.createStatement().executeQuery(query);
		} 
		catch (SQLException e)
		{			
			e.printStackTrace();
		}
		
		return null;
	}
	
	private int executeNonQuery(String sql)
	{
		try
		{
			return con.createStatement().executeUpdate(sql);
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return 0;
	}	
	
	private String getDate(Date dt)
	{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String currentTime = sdf.format(dt);
		return currentTime;
	}
	
	public boolean bookExists(Book book)
	{
		try
		{		
			String query = String.format("SELECT * from a_book where id = '%s'",book.getId());
		
			ResultSet rs = executeSelect(query);	
			return rs.next();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public void createPriceAndBook(Price price)
	{
		Book book = price.getBook();
		
		String createBookSQL = String.format("INSERT INTO a_book(id,title,author) VALUES('%s','%s','%s')", book.getId(), book.getTitle(), book.getAuthor());
		executeNonQuery(createBookSQL);
		
		insertPrice(price);
	}
	
	public List<Price> getMostRecentPrices()
	{
		List<Price> ret = new ArrayList<Price>();
		
		try
		{		
			String query = "SELECT m1.* FROM a_price m1 LEFT JOIN a_price m2 ON (m1.book = m2.book AND m1.id < m2.id) WHERE m2.id IS NULL";
		
			ResultSet rs = executeSelect(query);
			while(rs.next())
			{
				Book book = new Book();
				book.setId(rs.getString("book"));
				
				Price price = new Price(book, rs.getDate("date"), rs.getDouble("price"));
				ret.add(price);
			}			
		} 
		catch (SQLException e)
		{
			e.printStackTrace();			
		}
		
		return ret;
	}
	
	public void insertPrices(List<Price> prices)
	{
		for (Price price : prices)
		{
			insertPrice(price);
		}
	}
	
	private void insertPrice(Price price)
	{
		String sql = String.format("INSERT INTO a_price(book, date, price) VALUES('%s','%s',%s)",price.getBook().getId(),getDate(price.getDate()), price.getValue());
		executeNonQuery(sql);
	}
	
	public List<Price> getPricesForBook(Book book)
	{
		List<Price> ret = new ArrayList<Price>();
		
		try
		{		
			String query = String.format("SELECT * from a_price where book = '%s' order by date", book.getId());
		
			ResultSet rs = executeSelect(query);
			while(rs.next())
			{
				Price price = new Price(book, rs.getDate("date"), rs.getDouble("price"));
				ret.add(price);
			}			
		} 
		catch (SQLException e)
		{
			e.printStackTrace();			
		}
		
		return ret;
	}
	
	public void shutdown()
	{
		String sql = "UPDATE a_config set value = '0' where `key` = 'running'";
		executeNonQuery(sql);
	}
	

	public void startUp()
	{
		String sql = "UPDATE a_config set value = '1' where `key` = 'running'";
		executeNonQuery(sql);
	}
	
	public boolean shouldBeRunning()
	{
		try
		{		
			String query = "SELECT * from a_config where `key` = 'running'";
		
			ResultSet rs = executeSelect(query);
			while(rs.next())
			{
				return rs.getString("value").equals("1");
			}			
		} 
		catch (SQLException e)
		{
			e.printStackTrace();			
		}
		return false;
	}

	public List<User> findSubscriptionForBook(Book book)
	{
		List<User> ret = new ArrayList<User>();
		try
		{		
			String query = String.format("SELECT * FROM a_user a JOIN a_alert b ON a.username=b.username JOIN a_carrier c ON a.carrier = c.name WHERE book = '%s'",book.getId());
			ResultSet rs = executeSelect(query);
			while(rs.next())
			{
				Phone phone = new Phone(rs.getString("phone"),rs.getString("address"));
				User user = new User(rs.getString("username"),rs.getString("first"),rs.getString("last"),phone);
				ret.add(user);
			}			
		} 
		catch (SQLException e)
		{
			e.printStackTrace();			
		}
		
		return ret;
	}
}
