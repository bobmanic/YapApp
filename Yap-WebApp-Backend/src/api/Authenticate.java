package api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import database_connectors.BusinessTable;
import database_connectors.DevelopersTable;
import database_connectors.ReviewsTable;
import util.YapBusiness;
import util.YapReview;

@SuppressWarnings("serial")
public class Authenticate extends HttpServlet
{
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{		
		String app_id = request.getParameterValues("app_id")[0];
		
		//MISSING APP ID
		if((app_id == null)||(app_id.trim() == ""))
		{
			JSONObject obj=new JSONObject();
			obj.put("error","missing app_id");			
			
			response.setContentType("application/json");			     
			PrintWriter out = response.getWriter();  
			out.print(obj);
			out.flush();
		}
		
		//AUTHENTICATING APP ID
		if(!DevelopersTable.authenticateAppID(app_id))
		{
			//INVALID APP ID
			JSONObject obj = new JSONObject();
			obj.put("authentication","failed");
			obj.put("details","app_id not found");
			  
			response.setContentType("application/json");			     
			PrintWriter out = response.getWriter();  
			out.print(obj);
			out.flush();
		}
		else
		{
			String requestType = request.getParameterValues("request_type")[0];			
			
			if(requestType.equals("search")) //Search Business
			{
				String search_type = request.getParameterValues("form")[0];
				String business_name = request.getParameterValues("keywords")[0];
				String city = request.getParameterValues("city")[0];
				String state = request.getParameterValues("state")[0];
				String type = request.getParameterValues("type")[0];
				
				if(checkParameters(search_type, business_name, city, state, type))
				{					
					ArrayList<YapBusiness> yapBusinesses = BusinessTable.getBusinessesQuery(search_type, business_name, city, state, type);
										
					response.setContentType("application/json");			     
					PrintWriter out = response.getWriter();
					out.print(generateBusinessJSON(yapBusinesses));
					out.flush();
				}
				else
				{
					JSONObject obj=new JSONObject();
					obj.put("error","incorrect query parameters");				
					  
					response.setContentType("application/json");			     
					PrintWriter out = response.getWriter();
					out.print(obj);
					out.flush();
				}

			}
			else if(requestType.equals("reviews"))
			{				
				String business_id = request.getParameterValues("business_id")[0];
				
				ArrayList<YapReview> yapReviews = ReviewsTable.getReviews(business_id);
								
				response.setContentType("application/json");			     
				PrintWriter out = response.getWriter();
				out.print(generateReviewsJSON(yapReviews));
				out.flush();
			}
			else
			{
				JSONObject obj=new JSONObject();
				obj.put("error","incorrect request type");				
				  
				response.setContentType("application/json");			     
				PrintWriter out = response.getWriter();
				out.print(obj);
				out.flush();
			}
		}		
	}

	private boolean checkParameters(String search_type, String business_name, String city, String state, String type) 
	{
		if((search_type.equals("lenient"))||(search_type.equals("strict")))
		{}
		else
		{			
			return false;
		}
		
		if((type.equals("restaurant"))||(type.equals("cafe"))||(type.equals("hotel"))||(type.equals("bar"))||(type.equals("shop")))
		{}
		else
		{			
			return false;
		}
		
		return true;
	}

	@SuppressWarnings("unchecked")
	private JSONObject generateBusinessJSON(ArrayList<YapBusiness> yapBusinesses) 
	{
		JSONObject obj = new JSONObject();		
		JSONArray jsonArray = new JSONArray();
		for(int k=0; k<yapBusinesses.size(); k++)
		{
			YapBusiness yBusiness = yapBusinesses.get(k);
			
			JSONObject obj1 = new JSONObject();		
			obj1.put("business_id",yBusiness.getBusinessID());
			obj1.put("business_name",yBusiness.getBusinessName());
			obj1.put("city",yBusiness.getCity());
			obj1.put("state",yBusiness.getState());
			obj1.put("latitude",yBusiness.getLatitude());
			obj1.put("longitude",yBusiness.getLongitude());
			obj1.put("type",yBusiness.getBusinessType());
			obj1.put("neighbourhood",yBusiness.getNeighbourhoods().toString().replaceAll("\\[|\\]", ""));
			obj1.put("avg.rating",ReviewsTable.getAvgRating(yBusiness.getBusinessID()));
			
			jsonArray.add(obj1);
		}
		
		obj.put("businesses", jsonArray);
		
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject generateReviewsJSON(ArrayList<YapReview> yapReviews) 
	{
		JSONObject obj = new JSONObject();
		
		JSONArray jsonArray = new JSONArray();
		for(int k=0; k<yapReviews.size(); k++)
		{
			YapReview yReview = yapReviews.get(k);			
			jsonArray.add(yReview.getReviewJSON());			
		}
		
		obj.put("reviews", jsonArray);
		
		return obj;
	}
	
	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws IOException
	{
		String appid = request.getParameter("app_id");
		
		//MISSING APP ID
		if((appid == null)||(appid.trim() == ""))
		{
			JSONObject obj=new JSONObject();
			obj.put("error","missing app_id");			
			
			response.setContentType("application/json");			     
			PrintWriter out = response.getWriter();  
			out.print(obj);
			out.flush();
		}
		
		//AUTHENTICATING APP ID
		if(!DevelopersTable.authenticateAppID(appid))
		{
			//INVALID APP ID
			JSONObject obj = new JSONObject();
			obj.put("authentication","failed");
			obj.put("details","app_id not found");
			  
			response.setContentType("application/json");			     
			PrintWriter out = response.getWriter();  
			out.print(obj);
			out.flush();
		}
		else
		{
			System.out.println("Request: " + request);
			
			String requesttype = request.getParameter("request_type");
			if(requesttype.equalsIgnoreCase("post_review"))
			{
				System.out.println("In Post Review");
				
				String reviewtext = request.getParameter("review");
		        String username = request.getParameter("user_name");
		        int businessid = Integer.parseInt(request.getParameter("business_id"));
		        int rating = Integer.parseInt(request.getParameter("rating"));
		        String date = request.getParameter("date");
		        
		        if(ReviewsTable.addReview(username, reviewtext, businessid, rating, date))
				{
		        	System.out.println("Add Review Success!!");
		        	
					ArrayList<YapReview> yapReviews = ReviewsTable.getReviews(Integer.toString(businessid));
					
					response.setContentType("application/json");
					PrintWriter out = response.getWriter();
					out.print(generateReviewsJSON(yapReviews));
					out.flush();
				}
		        else
		        {
		        	System.out.println("Add Review Failure.");
		        	System.out.println("Review :" + reviewtext + ", username :" + username + ", bid :" + businessid + 
		        			", rating:" + rating + ", date :"+ date);
		        }
			}
		}
	}
	
}
