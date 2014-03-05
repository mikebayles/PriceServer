import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.MySQLHelper;


/**
 * Servlet implementation class AdminServlet
 */
public class AdminServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");	
		//PrintWriter out = response.getWriter();
		
		String method = request.getParameter("method");
		if(method == null)
			return;
		
		if(method.equals("stopUpdating"))
		{
			MySQLHelper.getInstance().shutdown();
		}
		else if(method.equals("startUpdating"))
		{
			MySQLHelper.getInstance().startUp();
		}
	}
}