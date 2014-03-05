package com.common;

import java.util.*;
import java.util.Map.Entry;

import javax.mail.*;
import javax.mail.internet.*;

import com.models.Alert;
import com.models.User;

public class EmailHelper
{
	final static String username = "parkinggaragemanagementsystem@gmail.com";
	final static String password = "password";
	
	private static void sendMessage(String to, String subject, String text)
	{		 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try
		{
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(text);
 
			Transport.send(message);
 
			System.out.println("Message sent to " + to);
 
		} 
		catch (MessagingException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void sendPriceChange(Map<List<User>, Alert> map)
	{
		for (Entry<List<User>, Alert> entry : map.entrySet())
		{
			for(User user : entry.getKey())
			{
				String to = String.format("%s@%s", user.getPhone().getNumber(),user.getPhone().getCarrierAddress());
				sendMessage(to, "Price Change!", entry.getValue().getText());
			}
		}
	}
}
