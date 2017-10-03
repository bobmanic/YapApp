package database_connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import util.Vote;
import util.YapBusiness;
import util.YapReview;

public class VotesTable 
{
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	//static final String DB_URL = "jdbc:mysql://sql.cs.usfca.edu:3306/user38";
	static final String DB_URL = "jdbc:mysql://localhost:3306/yap";
		
	//static final String USER = "user38";//"root";
	//static final String PASS = "user38";//"sandog2267139";
	static final String USER = "root";
	static final String PASS = "naseem";//"sandog2267139";
	
	public static boolean addVote(String user_name, String review_id, boolean funny, boolean cool, boolean useful)
	{
		if(!VotesTable.authenticateVoter(user_name, review_id))
	    {		
			   int rs=0;
			   Connection conn = null;
			   Statement stmt = null;
			   try
			   {
			      Class.forName("com.mysql.jdbc.Driver");
			      conn = DriverManager.getConnection(DB_URL,USER,PASS);
			      stmt = conn.createStatement();
			      
			      String sql = null;
			      		      
			      sql = "INSERT INTO votes VALUES (default, '"+
			    		  user_name + "', '" +
			    		  review_id + "', '" +
			    		  String.valueOf(funny) + "', '" +
			    		  String.valueOf(cool) + "', '" +
			    		  String.valueOf(useful) +
			    		  "');";
			      
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
		return false;
	}
	
	public static Vote getVote(String review_id, String user_name)
	{				
		   ResultSet rs = null;
		   Connection conn = null;
		   Statement stmt = null;
		   Vote yVote = null;
		   try
		   {
		      Class.forName("com.mysql.jdbc.Driver");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      stmt = conn.createStatement();
		      
		      String sql;		     
		      sql = "SELECT * FROM votes WHERE review_id = '"+review_id+"' AND user_name = '"+ user_name +"';";
		    		  
		      rs = stmt.executeQuery(sql);		     

		      if(rs.next())
		      {
		    	  int vid = rs.getInt("vote_id");
		    	  String uname = rs.getString("user_name");
		    	  int rid = rs.getInt("review_id");				  
				  boolean useful = rs.getBoolean("useful");
				  boolean funny = rs.getBoolean("funny");
				  boolean cool = rs.getBoolean("cool");
				  
			      yVote = new Vote(vid, user_name, rid, funny, cool, useful);		
		      }
		      else
		      {
		    	  yVote = null;
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
		   
		   return yVote;
	}
	
	public static boolean deleteVote(String review_id, String user_name)
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
		      sql = "DELETE FROM votes WHERE review_id='" + review_id + "' AND user_name='" + user_name + "';";		    		  
		    		  
		      rs = stmt.executeUpdate(sql);
		      
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
		   if(rs>0)
		   {
			   return true;
		   }
		   else
		   {
			   return false;
		   }		   
	}
	
	public static boolean updateVote(String review_id, String user_name, String type)
	{
		//if(!VotesTable.authenticateVoter(user_name, review_id))
	    //{
			boolean funny;
			boolean cool;
			boolean useful;
			
			int rs=0;
			Connection conn = null;
			Statement stmt = null;
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				stmt = conn.createStatement();
				
				String sql = null;
				Vote oldvote = getVote(review_id, user_name);
				
				if(oldvote == null)
				{
					funny = false;
					cool = false;
					useful = false;
				}
				else
				{
					funny = oldvote.getFunny();
					cool = oldvote.getCool();
					useful = oldvote.getUseful();				
				}
				
				if(type.equalsIgnoreCase("funny"))
				{
					sql = "UPDATE reviews SET funny = funny + 1 WHERE review_id="+ review_id +";";
					funny = true;
					
					rs = stmt.executeUpdate(sql);					
				}
				else if(type.equalsIgnoreCase("cool"))
				{
					sql = "UPDATE reviews SET cool = cool + 1 WHERE review_id="+ review_id +";";
					cool = true;
					
					rs = stmt.executeUpdate(sql);
					
					//sql = sql + "UPDATE votes SET cool = true WHERE review_id="+ review_id +";";
					//rs = stmt.executeUpdate(sql);
				}
				else if(type.equalsIgnoreCase("useful"))
				{
					sql = "UPDATE reviews SET useful = useful + 1 WHERE review_id="+ review_id +";";
					useful = true;
					
					rs = stmt.executeUpdate(sql);
					
					//sql = sql + "UPDATE votes SET useful = true WHERE review_id="+ review_id +";";
					//rs = stmt.executeUpdate(sql);
				}
				
				//rs = stmt.executeUpdate(sql);
				
				if(deleteVote(review_id, user_name))
				{				
					if(addVote(user_name, review_id, funny, cool, useful))
					{
						rs = 1;
					}
				}			
				
				stmt.close();
				conn.close();
				
				VotesTable.incrementVote( review_id, type);
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
			
			if(rs>0)
			{
				return true;
			}
			else
			{
				return false;
			}
		//}
		
		//return false;
	}
	
	public static boolean incrementVote(String review_id, String type)
	{		
		int rs=0;
		Connection conn = null;
		Statement stmt = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			
			String sql = null;			
			
			if(type.equalsIgnoreCase("funny"))
			{
				sql = "UPDATE votes SET funny = true WHERE review_id="+ review_id +";";
				rs = stmt.executeUpdate(sql);
			}
			else if(type.equalsIgnoreCase("cool"))
			{
				sql = "UPDATE votes SET cool = true WHERE review_id="+ review_id +";";
				rs = stmt.executeUpdate(sql);
			}
			else if(type.equalsIgnoreCase("useful"))
			{
				sql = "UPDATE votes SET useful = true WHERE review_id="+ review_id +";";
				rs = stmt.executeUpdate(sql);
			}												
			
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
		
		if(rs>0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean authenticateVoter(String user_name, String review_id) 
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
	      sql = "SELECT * FROM votes WHERE user_name='"+
	    		  user_name +"' and "+
	    		  "review_id='"+review_id+"'"+
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
