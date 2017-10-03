package database_connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import util.YapBusiness;
import util.YapReview;

public class ReviewsTable 
{
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/yap";
			
	static final String USER = "root";
	static final String PASS = "naseem";
	
	public static boolean addReview(String user_name, String review_text, int business_id, int rating, String dateInput)
	{
		ReviewsTable.deleteReview(Integer.toString(business_id), user_name);
		UsersTable.deleteUser(user_name, "guest");
		
		String dateIn = dateInput;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dateStr = null;
		try {
			dateStr = formatter.parse(dateInput);
		} catch (java.text.ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		java.sql.Date dateDB = new java.sql.Date(dateStr.getTime());
		
		if(getReview(Integer.toString(business_id),user_name) == null)
	      {
	    	  //Add USER_NAME and generate new USER_ID.
	    	  UsersTable.addUser(user_name, "guest@mysite.com", "guest");
	      }
		
		   int rs=0;
		   Connection conn = null;
		   Statement stmt = null;
		   try
		   {
		      Class.forName("com.mysql.jdbc.Driver");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      stmt = conn.createStatement();
		      
		      String sql = null;
		      
		      if(getReview(Integer.toString(business_id),user_name) == null)
		      {		    	  
		    	  PreparedStatement stmt1=
		    			  conn.prepareStatement("INSERT INTO reviews VALUES (default,(SELECT user_id FROM users WHERE user_name=?),?,?,?,?,?,0,0,0)");
		    	  
		    	  stmt1.setString(1, user_name);
		    	  stmt1.setString(2, user_name);
		    	  stmt1.setInt(3, business_id);
		    	  stmt1.setString(4, review_text);
		    	  stmt1.setInt(5, rating);
		    	  stmt1.setDate(6, dateDB);
		    	  		    	  		    	  
		    	  System.out.println(stmt1);
			      
		    	  rs = stmt1.executeUpdate();			      
		      }
		      
		      /* THIS "CORRECT" METHOD HAS BEEN REMOVED IN FAVOR OF HACKED ADDITION FROM APP OF ANY USER
		      if(getReview(Integer.toString(business_id),user_name) == null)
		      {
			      sql = "INSERT INTO reviews VALUES (default, (SELECT user_id FROM users WHERE user_name='" +
			    		  user_name + "'), '"+
			    		  user_name + "', " +
			    		  business_id + ", \"" +
			    		  review_text + "\"," +
			    		  rating + ", " +
			    		  date +
			    		  ", 0,0,0);";
			      
			      rs = stmt.executeUpdate(sql);			      			     
			      
		      }*/
		      else
		      {
		    	  updateReview(user_name, review_text, business_id, rating, dateInput.toString());
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
		   
		   if(rs==1)
	       {
	    	  return true;
	       }
		   else
		   {
			   return false;
		   }
		}
	
	public static boolean updateReview(String user_name, String review_text, int business_id, int rating, String date)
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
			if(deleteReview(Integer.toString(business_id), user_name))
			{
				if(addReview(user_name, review_text,  business_id, rating, date))
				{
					rs = 1;
				}
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
		
		if(rs==1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static double getAvgRating(String business_id)
	{
		ArrayList <YapReview> yapReviews = getReviews(business_id);
		if(yapReviews.size()>0)
		{
			double val =0;
			
			for(int u=0; u<yapReviews.size(); u++)
			{
				val = val + yapReviews.get(u).getRating();
			}
			 val = val/yapReviews.size();
			 val = Math.round(val * 2) / 2.0;		
			return val;
		}
		else
		{
			return 1;
		}
	}
	
	public static ArrayList<YapReview> searchReviews(String key)
	{
		   ArrayList <YapReview> yapReviews = new ArrayList <YapReview>();
		   ResultSet rs = null;
		   Connection conn = null;
		   Statement stmt = null;
		   try
		   {
		      Class.forName("com.mysql.jdbc.Driver");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      stmt = conn.createStatement();
		      
		      String sql;		     
		      sql = "SELECT * FROM reviews WHERE review LIKE '%"+key+"%';";
		    		  
		      rs = stmt.executeQuery(sql);
		      
		      while (rs.next())
		      {
		    	  int rid = rs.getInt("review_id");
		    	  int uid = rs.getInt("user_id");			      
				  String uname = rs.getString("user_name");
				  int bid = rs.getInt("business_id");
				  String review = rs.getString("review");
				  int rating = rs.getInt("rating");
				  String date = rs.getString("date");
				  int use = rs.getInt("useful");
				  int funny = rs.getInt("funny");
				  int cool = rs.getInt("cool");
				  
			      YapReview yReview = new YapReview(rid, Integer.toString(bid), rating, review, date, Integer.toString(uid), uname, use, cool, funny);		      
			      yapReviews.add(yReview);
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
		   
		   return yapReviews;
	}
	
	public static ArrayList<YapReview> getReviews(String business_id)
	{
		   ArrayList <YapReview> yapReviews = new ArrayList <YapReview>();
		   ResultSet rs = null;
		   Connection conn = null;
		   Statement stmt = null;
		   try
		   {
		      Class.forName("com.mysql.jdbc.Driver");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      stmt = conn.createStatement();
		      
		      String sql;		     
		      sql = "SELECT * FROM reviews WHERE business_id="+business_id+";";
		    		  
		      rs = stmt.executeQuery(sql);
		      
		      while (rs.next())
		      {
		    	  int rid = rs.getInt("review_id");
		    	  int uid = rs.getInt("user_id");			      
				  String uname = rs.getString("user_name");
				  int bid = rs.getInt("business_id");
				  String review = rs.getString("review");
				  int rating = rs.getInt("rating");
				  String date = rs.getString("date");
				  int use = rs.getInt("useful");
				  int funny = rs.getInt("funny");
				  int cool = rs.getInt("cool");
				  
			      YapReview yReview = new YapReview(rid, Integer.toString(bid), rating, review, date, Integer.toString(uid), uname, use, cool, funny);		      
			      yapReviews.add(yReview);
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
		   
		   return yapReviews;
	}
	
	public static ArrayList<YapReview> getReviewsBy(String user_name)
	{
		ArrayList <YapReview> yapReviews = new ArrayList <YapReview>();
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			
			String sql;		     
			sql = "SELECT * FROM reviews WHERE user_id=(SELECT user_id FROM users WHERE user_name='"+user_name+"');";					
			
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
			{
				int rid = rs.getInt("review_id");
				int uid = rs.getInt("user_id");			      
				String uname = rs.getString("user_name");
				int bid = rs.getInt("business_id");
				String review = rs.getString("review");
				int rating = rs.getInt("rating");
				String date = rs.getString("date");
				int use = rs.getInt("useful");
				int funny = rs.getInt("funny");
				int cool = rs.getInt("cool");
				
				YapReview yReview = new YapReview(rid, Integer.toString(bid), rating, review, date, Integer.toString(uid), uname, use, cool, funny);		      
				yapReviews.add(yReview);
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
		
		return yapReviews;
	}
	
	public static YapReview getReview(String business_id, String user_name)
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
		      sql = "SELECT * FROM reviews WHERE business_id="+business_id+" AND user_id=(SELECT user_id FROM users WHERE user_name='"+ user_name +"');";
		      
		      rs = stmt.executeQuery(sql);		      
		      
		      while (rs.next())
		      {
		    	  int rid = rs.getInt("review_id");
		    	  int uid = rs.getInt("user_id");			      
				  String uname = rs.getString("user_name");
				  int bid = rs.getInt("business_id");
				  String review = rs.getString("review");
				  int rating = rs.getInt("rating");
				  String date = rs.getString("date");
				  int use = rs.getInt("useful");
				  int funny = rs.getInt("funny");
				  int cool = rs.getInt("cool");
				  
			      YapReview yReview = new YapReview(rid, Integer.toString(bid), rating, review, date, Integer.toString(uid), uname, use, cool, funny);	
			      return yReview;
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
		   
		   return null;
	}
	
	public static boolean deleteReview(String business_id, String user_name)
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
		      sql = "DELETE FROM reviews WHERE user_id=(SELECT user_id FROM users WHERE user_name='" + user_name + "') AND business_id='" + business_id + "';";		    		  
		    		  
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

	public static int getMaxReviews()
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
	      sql = "select "+
	    		    "distinct g.user_id,"+
	    		    "g.cnt "+
	    		"from"+
	    		    "("+
	    		    "select "+
	    		        "distinct user_id,"+
	    		        "count(user_id) cnt "+
	    		    "from "+
	    		        "reviews "+
	    		    "group by "+ 
	    		        "user_id "+
	    		    ") g "+
	    		"inner join"+
	    		    "("+
	    		    "select "+
	    		        "max(s.cnt) max_cnt "+
	    		    "from"+
	    		        "("+
	    		        " select "+
	    		            "distinct user_id,"+
	    		            " count(user_id) cnt "+
	    		        "from "+
	    		            "reviews "+
	    		        "group by "+
	    		            "user_id"+
	    		        ") s "+
	    		    ") m "+
	    		"on "+
	    		    "m.max_cnt = g.cnt;";
	      
	      rs = stmt.executeQuery(sql);		      
	      int max_count = 0;
	      
	      while (rs.next())
	      {
	    	  max_count = rs.getInt("cnt");
	    	  int uid = rs.getInt("user_id");
	   	  }
	      
	      stmt.close();
	      conn.close();
	      
	      return max_count;
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
	  		
	   return 0;
	}

	public static int getCountOfReviewsBy(int uid) 
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
		      sql = "SELECT count(user_id) FROM reviews WHERE user_id = "+uid+";";
		      
		      rs = stmt.executeQuery(sql);		      
		      int max_count = 0;
		      
		      while (rs.next())
		      {
		    	  max_count = rs.getInt("count(user_id)");		    	  
		   	  }
		      
		      stmt.close();
		      conn.close();
		      
		      return max_count;
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
		
		return 0;
	}
	
}
