import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.amazon.AmazonItemFinder;
import com.common.MySQLHelper;
import com.models.Price;

/**
 * Servlet implementation class BookServlet
 */
public class BookServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html><html><head><title>Amazon Book Search</title><link href='../css/main.css' rel='stylesheet'></head><body><div id='wrapper' class='center'>");		
		
		
		
		String method = request.getParameter("method");
		
		if(method != null)
		{
			String book = request.getParameter("book");
			if(method.equals("search"))
			{
				Price price = AmazonItemFinder.search(book);
				if(price != null)
				{
					out.println(String.format("<h1>%s</h1>", price.getBook().getTitle()));
					out.println(String.format("<img src='%s'><br>", price.getBook().getImage()));
					out.println(String.format("<a href='%s'>View on Amazon</a>", price.getBook().getUrl()));
					
					out.println("<table border='.75'><th>Date</th><th>Price</th>");
					
					if(!MySQLHelper.getInstance().bookExists(price.getBook()))
					{
						MySQLHelper.getInstance().createPriceAndBook(price);										
					}
					List<Price> prices = MySQLHelper.getInstance().getPricesForBook(price.getBook());
					for(int i = 0; i <prices.size(); i++)
					{
						Price bookPrice = prices.get(i);
						
						String imageUrl = "";
						if(i == 0)
							imageUrl = "../images/greyAccross.png";
						else
						{
							double currentPrice = bookPrice.getValue();
							double previousPrice = prices.get(i-1).getValue();
							if(currentPrice > previousPrice)
								imageUrl = "../images/upGreen.png";
							else if(currentPrice < previousPrice)
								imageUrl = "../images/downRed.png";
							else
								imageUrl = "../images/greyAccross.png";
							
						}
						
						out.println("<tr>");
						out.println(String.format("<td>%s</td><td>%s</td><td><img src='%s'></td>", bookPrice.getDate(),bookPrice.getValue(),imageUrl));
						out.println("</tr>");
					}
				
					out.println("</table>");
					out.println("</div></body></body>");
				}			
			}			
			else if(method.equals("top5"))
			{
				;
			}
		}			
	}
}
