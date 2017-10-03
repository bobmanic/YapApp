package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database_connectors.ReviewsTable;

@SuppressWarnings("serial")
public class UpdateReview extends HttpServlet
{	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		//BLANK	
	}

	public void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws IOException
	{	
		String user_name = request.getParameterValues("user_name")[0];
		String business_id = request.getParameterValues("business_id")[0];
		String rating = request.getParameter("rating");
		String reviewtext = request.getParameterValues("reviewtext")[0];
		
		Date date = new Date();
		String modifiedDate = new SimpleDateFormat("yyyyMMdd").format(date);
				
		ReviewsTable.updateReview(user_name, reviewtext, Integer.parseInt(business_id), Integer.parseInt(rating), modifiedDate);		
				
		try {
			String path = "/AllReviews?business_id=";
			ServletContext cntxt = getServletContext();
			cntxt.getRequestDispatcher(path+business_id).forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
}
