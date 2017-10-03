package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database_connectors.BusinessTable;

@SuppressWarnings("serial")
public class SubmitBusiness extends HttpServlet
{	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		//BLANK	
	}

	public void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws IOException
	{				
		String business_name = request.getParameterValues("business_name")[0];		
		String city = request.getParameterValues("city")[0];		
		String state = request.getParameterValues("state")[0];
		String lat = request.getParameterValues("lat")[0];
		String lon = request.getParameterValues("lon")[0];
		String type = request.getParameterValues("type")[0];
		
		String[] neighborhoods = request.getParameterValues("neighborhood");
		
		String neighborhood = "";
		for(int y=0; y<neighborhoods.length; y++)
		{
			neighborhood = neighborhood +" "+neighborhoods[y];		
		}
		
		//Input Sanitation
		if((business_name.trim().length() > 0)||(city.trim().length() > 0)||(state.trim().length() > 0)||(lat.trim().length() > 0)||(lon.trim().length() > 0)||(type.trim().length() > 0)||(neighborhood.trim().length() > 0))
		{
			BusinessTable.addBusiness(business_name, city, state, lat, lon, type, neighborhood);
			
			PrintWriter out = response.getWriter();
			ServletContext cntxt = getServletContext();

	        String fName = "/businesssuccess.txt";
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
		    String fName = "/BusinessRegistration.html";
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
