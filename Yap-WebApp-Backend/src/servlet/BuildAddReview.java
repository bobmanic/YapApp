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

@SuppressWarnings("serial")
public class BuildAddReview extends HttpServlet
{	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String addrev = "";
		String fName = "/AddReview.html";
		ServletContext cntxt = getServletContext();
		InputStream ins = cntxt.getResourceAsStream(fName);
        try {
            if (ins != null) {
                InputStreamReader isr = new InputStreamReader(ins);
                BufferedReader reader = new BufferedReader(isr);               
                String word = "";
                while ((word = reader.readLine()) != null) {                    
                	addrev = addrev + word;
                }
            }
        }finally {
        	ins.close();
        }
        
        HttpSession session = request.getSession(false);
        if (session!=null)
		{
        	String username = (String) session.getAttribute("user_name");
        	addrev = addrev.replace("$user-name$", username);

        	String business_id = (String) session.getAttribute("businessid");
        	addrev = addrev.replace("$business-id$", business_id);        	
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
		//
		
		response.setContentType("text/html");
		PrintWriter outx = response.getWriter();
		outx.println(addrev);
	}
}
