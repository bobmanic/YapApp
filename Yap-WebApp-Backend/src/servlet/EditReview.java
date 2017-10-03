package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database_connectors.ReviewsTable;
import util.YapReview;

public class EditReview extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{				
		//Check for Old Session
		 HttpSession session = request.getSession(false);		 
		 if (session==null)
		 {
			 try {
				request.getRequestDispatcher("Landing.html").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 
		 //Fetch previous Review details and add them to New Review HTML Form
		 //Send submission to UpdateReview
		 String user_name = request.getParameterValues("user_name")[0];
		 String business_id = request.getParameterValues("business_id")[0];
		 
		 YapReview yReview = ReviewsTable.getReview(business_id,user_name);
		 
		 ServletContext cntxt = getServletContext();
		 String fName = "/AddReview.html";
		 InputStream ins = cntxt.getResourceAsStream(fName);
		 String addrevform = "";
		 
		 try {
			 if (ins != null) {
				 InputStreamReader isr = new InputStreamReader(ins);
				 BufferedReader reader = new BufferedReader(isr);               
				 String word = "";
				 while ((word = reader.readLine()) != null) {                    
					 addrevform = addrevform + word;
				 }
			 }
		 }finally {
			 ins.close();
		 }
		 
		 addrevform = addrevform.replace("Your Review Here.", yReview.getReview());
		 addrevform = addrevform.replace("$user-name$", user_name);
		 addrevform = addrevform.replace("$business-id$", business_id);
		 addrevform = addrevform.replace("$previous_review$", yReview.getReview());
		 addrevform = addrevform.replace("/Yap/AddReview", "/Yap/UpdateReview");
		 
		 response.setContentType("text/html");		
		 PrintWriter out = response.getWriter();
		 out.println(addrevform);
	 
	}
}