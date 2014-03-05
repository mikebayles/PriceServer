package com.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.amazon.AmazonItemFinder;
import com.common.EmailHelper;
import com.common.MySQLHelper;
import com.models.Alert;
import com.models.Price;
import com.models.User;

public class UpdatePrices implements Runnable
{
	ScheduledExecutorService scheduler; 
	
	public void start()
	{
		scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this, 0, 20, TimeUnit.MINUTES);
	}
	
	@Override
	public void run()
	{
       
		if(!MySQLHelper.getInstance().shouldBeRunning())
		{
			System.out.println("About to shutdown price tracking service");
			scheduler.shutdownNow();
			System.out.println("Price tracking shut down");
			return;
		}		
					
		System.out.println("Running update prices at " + new java.util.Date());
		List<Price> pricesToUpdate = new ArrayList<Price>();
		List<Price> mostRecentPrices = MySQLHelper.getInstance().getMostRecentPrices();
		for (Price price : mostRecentPrices)
		{
			Price newPrice = AmazonItemFinder.search(price.getBook().getId());
			if(newPrice.getValue() != price.getValue())
			{
				pricesToUpdate.add(newPrice);				
			}
		}
		
		MySQLHelper.getInstance().insertPrices(pricesToUpdate);
		sendAlerts(pricesToUpdate);
	}

	private void sendAlerts(List<Price> pricesToUpdate)
	{
		Map<List<User>, Alert> alerts = new HashMap<List<User>,Alert>();
		for (Price price : pricesToUpdate)
		{
			alerts.put(MySQLHelper.getInstance().findSubscriptionForBook(price.getBook()),new Alert(price));			
		}
		
		EmailHelper.sendPriceChange(alerts);
	}
}
