package database_connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import util.YapApp;
import util.YapBusiness;

public class DevelopersTable 
{
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	//static final String DB_URL = "jdbc:mysql://sql.cs.usfca.edu:3306/user38";
	static final String DB_URL = "jdbc:mysql://localhost:3306/yap";
		
	//static final String USER = "user38";//"root";
	//static final String PASS = "user38";//"sandog2267139";
	static final String USER = "root";
	static final String PASS = "naseem";//"sandog2267139";
	
	public static boolean addDeveloper(String dev_name, String app_name)
	{
		   int rs=0;
		   Connection conn = null;
		   Statement stmt = null;
		   try{
		      Class.forName("com.mysql.jdbc.Driver");

		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      stmt = conn.createStatement();
		      
		      String sql;
		      sql = "INSERT INTO dev VALUES (default, '" + dev_name + "', '" + app_name +"');";
		      rs = stmt.executeUpdate(sql);		      
		      
		      stmt.close();
		      conn.close();
		   }
		   catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }
		   catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }
		   finally
		   {
		      //finally block used to close resources
		      try
		      {
		         if(stmt!=null)
		            stmt.close();
		      }
		      catch(SQLException se2)
		      {
		      
		      }
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }
		   }
		   
		   if(rs==1)
	       {
	    	  return true;
	       }
		   else
		   {
			   return false;
		   }
		}	
	
	public static boolean deleteDeveloper(String dev_name)
	{
		int rs=0;
		Connection conn = null;
		Statement stmt = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			
			String sql;			
			sql = "DELETE FROM dev WHERE dev_name='" + dev_name + "';";
			rs = stmt.executeUpdate(sql); 
			
			stmt.close();
			conn.close();
		}
		catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}
		catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}
		finally
		{
			//finally block used to close resources
			try
			{
				if(stmt!=null)
					stmt.close();
			}
			catch(SQLException se2)
			{
				
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		
		if(rs==1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean deleteApp(String app_id, String dev_name)
	{
		int rs=0;
		Connection conn = null;
		Statement stmt = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			
			String sql;			
			sql = "DELETE FROM dev WHERE dev_name='" + dev_name + "' AND app_id=" + app_id +";";
			rs = stmt.executeUpdate(sql);		      
			
			stmt.close();
			conn.close();
		}
		catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}
		catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}
		finally
		{
			//finally block used to close resources
			try
			{
				if(stmt!=null)
					stmt.close();
			}
			catch(SQLException se2)
			{
				
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		
		if(rs==1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean authenticateAppID(String app_id) 
	{	
	   boolean found = false;
	   ResultSet rs = null;
	   Connection conn = null;
	   Statement stmt = null;
	   try
	   {
	      Class.forName("com.mysql.jdbc.Driver");
	      conn = DriverManager.getConnection(DB_URL,USER,PASS);
	      stmt = conn.createStatement();
	      
	      String sql;		     
	      sql = "SELECT * FROM dev WHERE app_id='"+
	    		  app_id +"';";
	    		  
	      rs = stmt.executeQuery(sql);
	   
	      if(rs.next())
	      {
	    	 found = true;
	      }
	      else
	      {
	    	  found = false;
	      }
	   
	      stmt.close();
	      conn.close();
	   }
	   catch(SQLException se){
	      se.printStackTrace();
	   }
	   catch(Exception e){	e.printStackTrace();}
	   finally
	   {
	      try
	      {
	    	  if(stmt!=null)	stmt.close();
	      }
	      catch(SQLException se2)
	      {
	    	  
	      }
	      
	      try 
	      { 
	    	  if(conn!=null) conn.close();
	      }
	      catch(SQLException se)
	      {
	         se.printStackTrace();
	      }
	   }
	   
	   return found;
	}	
	
	public static ArrayList<YapApp> getAllApps(String user_name)
	{
		   ArrayList <YapApp> devYapApps = new ArrayList <YapApp>();
		   ResultSet rs = null;
		   Connection conn = null;
		   Statement stmt = null;
		   try
		   {
		      Class.forName("com.mysql.jdbc.Driver");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      stmt = conn.createStatement();
		      
		      String sql;		     
		      sql = "SELECT * FROM dev WHERE dev_name = '"+ user_name +"';";
		    		  
		      rs = stmt.executeQuery(sql);
		   
		      while (rs.next()) 
		      {
			      int app_id = rs.getInt("app_id");
				  String dev_name = rs.getString("dev_name");
				  String app_name = rs.getString("app_name");			  
				  
			      YapApp yApp = new YapApp(app_id, app_name);		      
			      devYapApps.add(yApp);
		   	  }
		   
		      stmt.close();
		      conn.close();
		   }
		   catch(SQLException se){
		      se.printStackTrace();
		   }
		   catch(Exception e){	e.printStackTrace();}
		   finally
		   {
		      try
		      { if(stmt!=null)	stmt.close();}
		      catch(SQLException se2){}
		      
		      try 
		      { if(conn!=null) conn.close();}
		      catch(SQLException se){
		         se.printStackTrace();
		      }
		   }
		   
		   return devYapApps;
	}

	public static boolean checkUser(String username) 
	{		
	   boolean found = false;
	   ResultSet rs = null;
	   Connection conn = null;
	   Statement stmt = null;
	   try
	   {
	      Class.forName("com.mysql.jdbc.Driver");
	      conn = DriverManager.getConnection(DB_URL,USER,PASS);
	      stmt = conn.createStatement();
	      
	      String sql;		     
	      sql = "SELECT * FROM dev WHERE dev_name='"+
	    		  username +"'"+
	    		  ";";
	    		  
	      rs = stmt.executeQuery(sql);
	   
	      if(rs.next())
	      {
	    	 found = true;
	      }
	      else
	      {
	    	  found = false;
	      }
	   
	      stmt.close();
	      conn.close();
	   }
	   catch(SQLException se){
	      se.printStackTrace();
	   }
	   catch(Exception e){	e.printStackTrace();}
	   finally
	   {
	      try
	      {
	    	  if(stmt!=null)	stmt.close();
	      }
	      catch(SQLException se2)
	      {
	    	  
	      }
	      
	      try 
	      { 
	    	  if(conn!=null) conn.close();
	      }
	      catch(SQLException se)
	      {
	         se.printStackTrace();
	      }
	   }
	   
	   return found;		
	}

}
