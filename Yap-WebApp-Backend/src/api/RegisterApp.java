package api;

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

import database_connectors.DevelopersTable;

@SuppressWarnings("serial")
public class RegisterApp extends HttpServlet
{	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String user_name ="";
		HttpSession session = request.getSession(false);
        if (session!=null)
		{
        	user_name = (String) session.getAttribute("user_name");
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
					
		String app_name = request.getParameterValues("app_name")[0];
		
		//Input Sanitation
		if(app_name.trim().length() > 0)
		{
			DevelopersTable.addDeveloper(user_name, app_name);
			
			PrintWriter out = response.getWriter();
			
			ServletContext cntxt = getServletContext();

	        String fName = "/appsuccess.txt";
	        String template = "";
	        InputStream ins = cntxt.getResourceAsStream(fName);
	        try {
	            if (ins != null) {
	                InputStreamReader isr = new InputStreamReader(ins);
	                BufferedReader reader = new BufferedReader(isr);               
	                String word = "";
	                while ((word = reader.readLine()) != null) {                    
	                	template = template + word;
	                }
	            }
	        }finally {
	        	ins.close();
	        }       
			
	        out.println(template);	        			
		}
		else{
			String errorlanding = "";
		    String fName = "/DevAppRegistration.html";
		    ServletContext cntxt = getServletContext();
		    InputStream ins = cntxt.getResourceAsStream(fName);
		      
		    try 
		    {
		    	if (ins != null)
		    	{
		    		InputStreamReader isr = new InputStreamReader(ins);
		    		BufferedReader reader = new BufferedReader(isr);               
		    		String word = "";
		    		while ((word = reader.readLine()) != null)
		    		{                    
		    			errorlanding = errorlanding + word;
		            }
		         }
		     }
		     finally
		     {
		    	 ins.close();
		     }
		      		    		     
		     errorlanding = errorlanding.replace("Welcome to Yap! Submit your Business here!", "Incorrect form entry. Please refill without blanks.");
		      		      
		     response.setContentType("text/html");
			 PrintWriter outx = response.getWriter();
			 outx.println(errorlanding);
		  }				
	}

	public void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws IOException
	{	
		String user_name ="";
		HttpSession session = request.getSession(false);
        if (session!=null)
		{
        	user_name = (String) session.getAttribute("user_name");
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
					
		String app_name = request.getParameterValues("app_name")[0];
		
		//Input Sanitation
		if(app_name.trim().length() > 0)
		{
			DevelopersTable.addDeveloper(user_name, app_name);
			
			PrintWriter out = response.getWriter();
			ServletContext cntxt = getServletContext();

	        String fName = "/appsuccess.txt";
	        String template = "";
	        InputStream ins = cntxt.getResourceAsStream(fName);
	        try {
	            if (ins != null) {
	                InputStreamReader isr = new InputStreamReader(ins);
	                BufferedReader reader = new BufferedReader(isr);               
	                String word = "";
	                while ((word = reader.readLine()) != null) {                    
	                	template = template + word;
	                }
	            }
	        }finally {
	        	ins.close();
	        }       
			
	        out.println(template);
		}
		else
		{
			String errorlanding = "";
		    String fName = "/DevAppRegistration.html";
		    ServletContext cntxt = getServletContext();
		    InputStream ins = cntxt.getResourceAsStream(fName);
		      
		    try 
		    {
		    	if (ins != null)
		    	{
		    		InputStreamReader isr = new InputStreamReader(ins);
		    		BufferedReader reader = new BufferedReader(isr);               
		    		String word = "";
		    		while ((word = reader.readLine()) != null)
		    		{                    
		    			errorlanding = errorlanding + word;
		            }
		         }
		     }
		     finally
		     {
		    	 ins.close();
		     }
		      		    		     
		     errorlanding = errorlanding.replace("Welcome to Yap! Submit your Business here!", "Incorrect form entry. Please refill without blanks.");
		      		      
		     response.setContentType("text/html");
			 PrintWriter outx = response.getWriter();
			 outx.println(errorlanding);
		  }				
	}
}
