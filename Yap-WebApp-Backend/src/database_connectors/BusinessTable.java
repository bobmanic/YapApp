package database_connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import util.YapBusiness;

public class BusinessTable 
{
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	//static final String DB_URL = "jdbc:mysql://sql.cs.usfca.edu:3306/user38";
	static final String DB_URL = "jdbc:mysql://localhost:3306/yap";
		
	//static final String USER = "user38";//"root";
	//static final String PASS = "user38";//"sandog2267139";
	static final String USER = "root";
	static final String PASS = "naseem";//"sandog2267139";
	
	public static boolean addBusiness(String business_name, String city, String state, String lat, String lon, String type, String negihborhood)
	{
		   int rs=0;
		   Connection conn = null;
		   Statement stmt = null;
		   try
		   {
		      Class.forName("com.mysql.jdbc.Driver");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      stmt = conn.createStatement();
		      
		      String sql = "";
		      if(!checkExistsBusiness(business_name, city, state, lat, lon, type, negihborhood))
		      {
		    	  sql = "INSERT INTO business VALUES (default, '" + business_name + "', '" + city + "','" + state + "', "+ lat + "," + lon + ", '" + type + "', '" + negihborhood + "');";
		    	  rs = stmt.executeUpdate(sql);
		      }
		      else
		      {
		    	  rs=1; //Entry Already exists no need to add.
		      }
		      stmt.close();
		      conn.close();
		   }
		   catch(SQLException se)	{	se.printStackTrace();}
		   catch(Exception e)	{	e.printStackTrace();}
		   finally
		   {
		      try
		      {
		         if(stmt!=null)	stmt.close();
		      }
		      catch(SQLException se2){}		      
		      try
		      {	if(conn!=null)	conn.close();}
		      catch(SQLException se)
		      {	se.printStackTrace();}
		   }
		   
		   if(rs==1) {return true;}
		   else {return false;}
		}
	
	public static YapBusiness getBusinessById(String business_id)
	{		   
		   ResultSet rs = null;
		   Connection conn = null;
		   Statement stmt = null;
		   try
		   {
		      Class.forName("com.mysql.jdbc.Driver");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      stmt = conn.createStatement();
		      
		      String sql;		     
		      sql = "SELECT * FROM business WHERE business_id="+business_id+";";
		    		  
		      rs = stmt.executeQuery(sql);
		   		      
		      int bid = rs.getInt("business_id");
			  String bname = rs.getString("name");
			  String bcity = rs.getString("city");
			  String bstate = rs.getString("state");
			  String blat = rs.getString("latitude");
			  String blon = rs.getString("longitude");
			  String btype = rs.getString("type");
			  String bneighborhoods = rs.getString("neighborhoods");
			  
		      YapBusiness yBusiness = new YapBusiness(Integer.toString(bid), bname, bcity, bstate, Double.parseDouble(blat), Double.parseDouble(blon), btype, bneighborhoods);		      			      		   	  
		   
		      stmt.close();
		      conn.close();
		      
		      return yBusiness;
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
		   
		   return null;
	}
	
	public static ArrayList<YapBusiness> getAllBusinesses()
	{
		   ArrayList <YapBusiness> allYapBusiness = new ArrayList <YapBusiness>();
		   ResultSet rs = null;
		   Connection conn = null;
		   Statement stmt = null;
		   try
		   {
		      Class.forName("com.mysql.jdbc.Driver");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      stmt = conn.createStatement();
		      
		      String sql;		     
		      sql = "SELECT * FROM business;";
		    		  
		      rs = stmt.executeQuery(sql);
		   
		      while (rs.next()) 
		      {
			      int bid = rs.getInt("business_id");
				  String bname = rs.getString("name");
				  String bcity = rs.getString("city");
				  String bstate = rs.getString("state");
				  String blat = rs.getString("latitude");
				  String blon = rs.getString("longitude");
				  String btype = rs.getString("type");
				  String bneighborhoods = rs.getString("neighborhoods");
				  
			      YapBusiness yBusiness = new YapBusiness(Integer.toString(bid), bname, bcity, bstate, Double.parseDouble(blat), Double.parseDouble(blon), btype, bneighborhoods);		      
			      allYapBusiness.add(yBusiness);
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
		   
		   return allYapBusiness;
	}
	
	public static boolean averageBusinessRating(int business_id)
	{	
		//TODO
		return false;
	}
	
	public static boolean checkExistsBusiness(String business_name, String city, String state, String lat, String lon, String type, String negihborhood)
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
		      sql = "SELECT * FROM business WHERE name='"+ business_name + "' AND city='"+ city + "' AND state='"+ state + "' AND latitude='"+ lat + "' AND longitude='"+ lon + "' AND type='"+ type + "' AND neighborhoods='"+ negihborhood +"';";
		    		  
		      rs = stmt.executeQuery(sql);
	   	     
		      if(rs.next())
	   		  {
		    	  found =  true; 
	   		  }
	   		  else
	   		  {
	   			  found =  false;
	   	   	  }
		   
		      stmt.close();
		      conn.close();
		      
		      return found;
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
		   
		   return false;
	}
	
	public static ArrayList<YapBusiness> getBusinessesQuery(String form, String business_name, String city, String state, String type)
	{
		   ArrayList <YapBusiness> yapBusiness = new ArrayList <YapBusiness>();
		   ResultSet rs = null;
		   Connection conn = null;
		   Statement stmt = null;
		   try
		   {
		      Class.forName("com.mysql.jdbc.Driver");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      stmt = conn.createStatement();
		      
		      String sql = "";
		      if(form.equals("strict"))
		      {
		    	  /*
			      sql = "SELECT * FROM business"+ "WHERE name LIKE '%" + business_name + "%'" +
			    		  "AND city LIKE '%" + city + "%'" +
			    		  "AND state LIKE '%" + state + "%'" +
			    		  "AND type LIKE '%" + type + "%'" +
			    		  ";";
			      */
		    	  
		    	  String query = "SELECT * FROM business";
		    	  
		    	  int param = 0;
		    	  
		    	  if((business_name.trim()!="")||(business_name != null)||(!business_name.equals("*")))
		    	  {
		    		  param = param + 1;
		    		  query = query + " WHERE name LIKE '%" + business_name + "%'";
		    	  }
		    	  
		    	  if((city.trim()!="")||(city != null)||(!city.equals("*")))
		    	  {
		    		  if(param == 0)
		    		  {
		    			  param = param + 1;
		    			  query = query + " WHERE city LIKE '%" + city + "%'";
		    		  }
		    		  else
		    		  {
		    			  param = param + 1;
		    			  query = query + "AND city LIKE '%" + city + "%'";
		    		  }
		    	  }
		    	  
		    	  if((state.trim()!="")||(state != null)||(!state.equals("*")))
		    	  {
		    		  if(param == 0)
		    		  {
		    			  param = param + 1;
		    			  query = query + " WHERE state LIKE '%" + state + "%'";
		    		  }
		    		  else
		    		  {
		    			  param = param + 1;
		    			  query = query + "AND state LIKE '%" + state + "%'";
		    		  }
		    	  }
		    	  
		    	  if((type.trim()!="")||(type != null)||(!type.equals("*")))
		    	  {
		    		  if(param == 0)
		    		  {
		    			  param = param + 1;
		    			  query = query + " WHERE type LIKE '%" + type + "%'";
		    		  }
		    		  else
		    		  {
		    			  param = param + 1;
		    			  query = query + "AND type LIKE '%" + type + "%'";
		    		  }
		    	  }
		    	  
		    	  sql = query+";";
		      }
		      else if(form.equals("lenient"))
		      {
		    	  String query = "SELECT * FROM business";
		    	  
		    	  int param = 0;
		    	  
		    	  if((business_name.trim()!="")||(business_name != null)||(!business_name.equals("*")))
		    	  {
		    		  param = param + 1;
		    		  query = query + " WHERE name LIKE '%" + business_name + "%'";
		    	  }
		    	  
		    	  if((city.trim()!="")||(city != null)||(!city.equals("*")))
		    	  {
		    		  if(param == 0)
		    		  {
		    			  param = param + 1;
		    			  query = query + " WHERE city LIKE '%" + city + "%'";
		    		  }
		    		  else
		    		  {
		    			  param = param + 1;
		    			  query = query + "OR city LIKE '%" + city + "%'";
		    		  }
		    	  }
		    	  
		    	  if((state.trim()!="")||(state != null)||(!state.equals("*")))
		    	  {
		    		  if(param == 0)
		    		  {
		    			  param = param + 1;
		    			  query = query + " WHERE state LIKE '%" + state + "%'";
		    		  }
		    		  else
		    		  {
		    			  param = param + 1;
		    			  query = query + "OR state LIKE '%" + state + "%'";
		    		  }
		    	  }
		    	  
		    	  if((type.trim()!="")||(type != null)||(!type.equals("*")))
		    	  {
		    		  if(param == 0)
		    		  {
		    			  param = param + 1;
		    			  query = query + " WHERE type LIKE '%" + type + "%'";
		    		  }
		    		  else
		    		  {
		    			  param = param + 1;
		    			  query = query + "OR type LIKE '%" + type + "%'";
		    		  }
		    	  }
		    	  
		    	  sql = query+";";		    	 
		      }
		      
		      rs = stmt.executeQuery(sql);
		   
		      while (rs.next()) 
		      {
			      int bid = rs.getInt("business_id");
				  String bname = rs.getString("name");
				  String bcity = rs.getString("city");
				  String bstate = rs.getString("state");
				  String blat = rs.getString("latitude");
				  String blon = rs.getString("longitude");
				  String btype = rs.getString("type");
				  String bneighborhoods = rs.getString("neighborhoods");
				  
			      YapBusiness yBusiness = new YapBusiness(Integer.toString(bid), bname, bcity, bstate, Double.parseDouble(blat), Double.parseDouble(blon), btype, bneighborhoods);		      
			      yapBusiness.add(yBusiness);
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
		   
		   return yapBusiness;
	}

}
