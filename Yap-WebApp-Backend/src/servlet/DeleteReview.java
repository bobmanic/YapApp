package servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database_connectors.ReviewsTable;

@SuppressWarnings("serial")
public class DeleteReview extends HttpServlet
{	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		//Check for Old Session
		 HttpSession session = request.getSession(false);		 
		 if (session!=null)
		 {
			 try {
				request.getRequestDispatcher("/AllBusiness").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 else
		 {
			 try {
				request.getRequestDispatcher("Landing.html").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 
		 //Fetch previous Review and Delete it.
		 String user_name = request.getParameterValues("user_name")[0];
		 String business_id = request.getParameterValues("business_id")[0];
		 
		 ReviewsTable.deleteReview(business_id, user_name);
		 
		 try {
				String path = "/AllReviews?business_id=";
				ServletContext cntxt = getServletContext();
				cntxt.getRequestDispatcher(path+business_id).forward(request, response);
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
