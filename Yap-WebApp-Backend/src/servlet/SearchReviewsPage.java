package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class SearchReviewsPage extends HttpServlet
{	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
	  try {
				request.getRequestDispatcher("/SearchReviews.html").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}

	public void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws IOException
	{						
		//BLANK
	}
}
