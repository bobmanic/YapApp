package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database_connectors.UsersTable;

@SuppressWarnings("serial")
public class UserRegistration extends HttpServlet
{	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		//BLANK	
	}

	public void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws IOException
	{
				
		String user_name = request.getParameterValues("username")[0];
		String password = request.getParameterValues("password")[0];
		String email = request.getParameterValues("email")[0];
		String option = request.getParameterValues("option")[0];
		
		//Checking Login
		if(option.equalsIgnoreCase("Login"))
		{
			  // Create a session object if it is already not  created.
		      HttpSession session = request.getSession(true);
		      // Get session creation time.
		      Date createTime = new Date(session.getCreationTime());
		      // Get last access time of this web page.
		      Date lastAccessTime = new Date(session.getLastAccessedTime());
			
		      // Check if this is new comer on your web page.
		      if ((session.isNew())&((user_name.trim().length() > 0)||(password.trim().length() > 0)||(email.trim().length() > 0)))
		      {		    	  
			          //USER AUTHENTICATION		    		  
		    		  if(UsersTable.authenticateUser(user_name, password))
		    		  {			    	 
				    	 //NEW SESSION
				         session.setAttribute("user_name", user_name);
				         session.setAttribute("password", password);
				         
				         try 
				         {
				        	 request.getRequestDispatcher("AllBusiness").forward(request, response);
						 }
				         catch (ServletException e)
				         {
							 // TODO Auto-generated catch block
							 e.printStackTrace();
						 }
			          }
		    		  else
		    		  {
		    			  String errorlanding = "";
		    		      String fName = "/Landing.html";
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
		    		      		    		     
		    		      errorlanding = errorlanding.replace("Welcome to Yap! The place for all your reviews!", "Incorrect Username\\Password. Please retry.");
		    		      
		    		      session.invalidate();
		    		      response.setContentType("text/html");
		    			  PrintWriter outx = response.getWriter();
		    			  outx.println(errorlanding);
		    		  }
		      }
		      else //Blank Inputs
	    	  {
				  String errorlanding = "";
	  		      String fName = "/Landing.html";
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

	  		      errorlanding = errorlanding.replace("Welcome to Yap! The place for all your reviews!", "Empty Username\\Password. Please refill the form without blank spaces.");
	  		        		     
	  		      response.setContentType("text/html");
	  			  PrintWriter outx = response.getWriter();
	  			  outx.println(errorlanding);
				}
		}
		else	//Register New User
		{
			//Blank Inputs
			if((user_name.trim().length() == 0)||(password.trim().length() == 0)||(email.trim().length() == 0))
			{
			  String errorlanding = "";
  		      String fName = "/Landing.html";
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
  		      		    		     
  		      errorlanding = errorlanding.replace("Welcome to Yap! The place for all your reviews!", "Empty Username\\Password\\Email. Please refill the form without blank spaces.");
  		        		     
  		      response.setContentType("text/html");
  			  PrintWriter outx = response.getWriter();
  			  outx.println(errorlanding);
			}
			
			//Register New User
			if((!user_name.isEmpty())&&(!password.isEmpty())&&(!email.isEmpty()))
	    	{
				// Create a session object if it is already not  created.
			    HttpSession session = request.getSession(true);
			    // Get session creation time.
			    Date createTime = new Date(session.getCreationTime());
			    // Get last access time of this web page.
			    Date lastAccessTime = new Date(session.getLastAccessedTime());
				
		        //USER AUTHENTICATION
				if(!UsersTable.authenticateUser(user_name, password))
				{	
					//User Registration		
					UsersTable.addUser(user_name, email, password);
					//NEW SESSION
					session.setAttribute("user_name", user_name);
					session.setAttribute("password", password);
					
					//Acknowledge New User Registration
					PrintWriter out = response.getWriter();
					
					ServletContext cntxt = getServletContext();

			        String fName = "/newuser.txt";
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
					
			        template = template.replaceAll("user_name", request.getParameterValues("username")[0]);
			        
			        out.println(template);
					
		        }
	    	    else //Existing User Name
	    	    {
	    	    	session.invalidate();
	    	    	String errorlanding = "";
	    	    	String fName = "/Landing.html";
	    	    	ServletContext cntxt = getServletContext();
	    	    	InputStream ins1 = cntxt.getResourceAsStream(fName);
	    	    	try 
	    	    	{
	    	    		if (ins1 != null)
	    	    		{
	    	    			InputStreamReader isr = new InputStreamReader(ins1);
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
	    		    	 ins1.close();
	    		     }
	    		      		    		     
	    		     errorlanding = errorlanding.replace("Welcome to Yap! The place for all your reviews!", "Username already exists. Please enter new Username.");
	    		     response.setContentType("text/html");
	    		     PrintWriter outx = response.getWriter();
	    			 outx.println(errorlanding);
	    		  }
	          }
			  else //Missing Email
			  {
				  String errorlanding = "";
				  String fName = "/Landing.html";
				  ServletContext cntxt = getServletContext();
				  InputStream ins2 = cntxt.getResourceAsStream(fName);
				  try 
				  {
					  if (ins2 != null)
					  {
						  InputStreamReader isr = new InputStreamReader(ins2);
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
	    			   ins2.close();
	    		   }
	    		      		    		     
	    		   errorlanding = errorlanding.replace("Welcome to Yap! The place for all your reviews!", "Missing input. Please refill form with e-mail.");
	    		   response.setContentType("text/html");
	    		   PrintWriter outx = response.getWriter();
	    		   outx.println(errorlanding);
			  }
			
		}
	}
}
