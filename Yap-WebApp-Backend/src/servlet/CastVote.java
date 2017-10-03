package servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database_connectors.VotesTable;

@SuppressWarnings("serial")
public class CastVote extends HttpServlet
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
		 		 
		 String user_name = request.getParameterValues("user_name")[0];
		 String review_id = request.getParameterValues("review_id")[0];		 
		 String type = request.getParameterValues("vote_type")[0];		
		 
		 if(VotesTable.addVote(user_name, review_id, false, false, false))
		 {
			 VotesTable.updateVote(review_id, user_name, type);
		 }
		 
		 try {
				String path = "/AllBusiness";
				ServletContext cntxt = getServletContext();
				cntxt.getRequestDispatcher(path).forward(request, response);
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
