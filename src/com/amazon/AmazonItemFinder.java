/**********************************************************************************************
 * Copyright 2009 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file 
 * except in compliance with the License. A copy of the License is located at
 *
 *       http://aws.amazon.com/apache2.0/
 *
 * or in the "LICENSE.txt" file accompanying this file. This file is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under the License. 
 *
 * ********************************************************************************************
 *
 *  Amazon Product Advertising API
 *  Signed Requests Sample Code
 *
 *  API Version: 2009-03-31
 *
 */

package com.amazon;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.models.Book;
import com.models.Price;

/*
 * This class shows how to make a simple authenticated ItemLookup call to the
 * Amazon Product Advertising API.
 * 
 * See the README.html that came with this sample for instructions on
 * configuring and running the sample.
 */
public class AmazonItemFinder 
{
	public final class SearchType 
	{
	    public static final String ITEM_LOOKUP = "ItemLookup";
	    public static final String ITEM_SEARCH = "ItemSearch";	  
	}
	
    /*
     * Your AWS Access Key ID, as taken from the AWS Your Account page.
     */
    private static final String AWS_ACCESS_KEY_ID = "AKIAJPHZTDYXDP354FRQ";

    /*
     * Your AWS Secret Key corresponding to the above ID, as taken from the AWS
     * Your Account page.
     */
    private static final String AWS_SECRET_KEY = "wnb47h+tXxLc+dLKjvdoqKgkGAeqEWav+5JIuhtU";
    private static final String ENDPOINT = "webservices.amazon.com";

    /*
     * The Item ID to lookup. The value below was selected for the US locale.
     * You can choose a different value if this value does not work in the
     * locale of your choice.
     */
    
    public static Price search(String search, String searchType)
    {    
    	
    	SignedRequestsHelper helper;
        try 
        {
            helper = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();     
            return null;
        }
        
        String requestUrl = null;        

        /* The helper can sign requests in two forms - map form and string form */
        
        /* 
         * Here is an example in map form, where the request parameters are stored in a map.
         */        
        Map<String, String> params = new HashMap<String, String>();
        params.put("Service", "AWSECommerceService");
        params.put("AssociateTag","mikebay-20");
        params.put("Version", "2011-08-01");
        params.put("Operation", searchType);
        
        params.put("ResponseGroup", "ItemAttributes,Images");
        if(searchType.equals(SearchType.ITEM_SEARCH))
        {
        	params.put("Keywords", search);
        	params.put("SearchIndex", "All");
        }
        else
        	params.put("ItemId", search);

        requestUrl = helper.sign(params);
        //System.out.println("Signed Request is \"" + requestUrl + "\"");
        Price ret = fetchPrice(requestUrl);      
        //System.out.println(ret);
        
        return ret;
        
    }

    public static Price search(String item) 
    {
    	Price ret = search(item,SearchType.ITEM_LOOKUP);
    	return ret == null ? search(item, SearchType.ITEM_SEARCH) : ret;
    }

    /*
     * Utility function to fetch the response from the service and extract the
     * title from the XML.
     */
    private static Price fetchPrice(String requestUrl) 
    {               
        try
        {
		    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		    DocumentBuilder db = dbf.newDocumentBuilder();
		    Document doc = db.parse(requestUrl);  
		    
		    String id;
		    String title;
		    String author;
		    double price;
		    String url;
		    String image;
		    
		    Node asinNode = doc.getElementsByTagName("ASIN").item(0);		
		    id = asinNode != null ? asinNode.getTextContent() : "";
		                
		    Node titleNode = doc.getElementsByTagName("Title").item(0);		
		    title = titleNode != null ? titleNode.getTextContent() : "";
		    
		    Node authorNode = doc.getElementsByTagName("Author").item(0);
		    author = authorNode != null ? authorNode.getTextContent() : "";
		    
		    Node tradeInPriceNode = doc.getElementsByTagName("TradeInValue").item(0);
		    price = tradeInPriceNode != null ? Double.parseDouble(tradeInPriceNode.getLastChild().getTextContent().replace("$", "")) : 0.0;
		    
		    Node urlNode = doc.getElementsByTagName("DetailPageURL").item(0);
		    url = urlNode != null ? urlNode.getTextContent() : "";
		    
		    Node imageNode = doc.getElementsByTagName("MediumImage").item(0);
		    image = imageNode != null ? imageNode.getFirstChild().getTextContent() : "";
		                		   
		    Book book = new Book(id, title, author, url, image);
		    Price priceObj = new Price(book, new Date(), price);
		    
		    return title.equals("") ? null : priceObj;
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	return null;
        }                        
    }    
}
