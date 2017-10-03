package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database_connectors.DevelopersTable;
import database_connectors.ReviewsTable;
import util.YapReview;

@SuppressWarnings("serial")
public class AllReviews extends HttpServlet
{	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException	
	{			
		String business_id = request.getParameterValues("business_id")[0];		
		
		HttpSession session0 = request.getSession(false);
        String uname = "";
        if (session0!=null)
		{
        	uname = (String) session0.getAttribute("user_name");        	
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
		
		ArrayList<YapReview> yapReviews = ReviewsTable.getReviews(business_id);
		yapReviews.sort(null);
		
		String generatedcontent = "";
		String list_item_template = "";
		
		ServletContext cntxt = getServletContext();
		String fName = "/TemplateRow.txt";
		InputStream ins = cntxt.getResourceAsStream(fName);
		try {
			if (ins != null) {
				InputStreamReader isr = new InputStreamReader(ins);
				BufferedReader reader = new BufferedReader(isr);               
				String word = "";
				while ((word = reader.readLine()) != null) {                    
					list_item_template = list_item_template + word;
				}
			}
		}finally {
			ins.close();
		}
		
		String temp = "";		
		for(int x=0; x<yapReviews.size(); x++)
		{
			temp = list_item_template;
			
			String imagelink = "&[imgurl]&";
			temp = temp.replace(imagelink, "http://s3-media3.fl.yelpcdn.com/assets/srv0/yelp_styleguide/20983a63ea50/assets/img/default_avatars/user_60_square.png");
			
			String itemtitle = "&[list-item-title]&";
			temp = temp.replace(itemtitle, yapReviews.get(x).getUserName());
			
			String itemsubtitle = "&[list-item-subtitle]&";
			temp = temp.replace(itemsubtitle, yapReviews.get(x).getDate());
			
			String starrating = "&[X-Star]&";
			int rate = yapReviews.get(x).getRating(); 
			String ratingstring = "";
			
			if(rate==1)
			{
				ratingstring = "<i class=\"star-img stars_1\" title=\"1 star rating\">"+
						"<img alt=\"1 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						"</i>";
			}
			if(rate==2)
			{
				ratingstring = "<i class=\"star-img stars_2\" title=\"2 star rating\">"+
						"<img alt=\"2 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						"</i>";
			}
			if(rate==3)
			{
				ratingstring = "<i class=\"star-img stars_3\" title=\"3 star rating\">"+
						"<img alt=\"3 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						"</i>";
			}
			if(rate==4)
			{
				ratingstring = "<i class=\"star-img stars_4\" title=\"4 star rating\">"+
						"<img alt=\"4 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						"</i>";
			}
			if(rate==5)
			{
				ratingstring = "<i class=\"star-img stars_5\" title=\"5 star rating\">"+
						"<img alt=\"5 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						"</i>";
			}
			
			temp = temp.replace(starrating, ratingstring);
			
			String smalldata = "&[small-data]&";
			temp = temp.replace(smalldata, yapReviews.get(x).getReview());			
			
			String content = "&[rate-review-feedback]&";
			
			String review_fb = "";
	        fName = "/ReviewBarTemplate.txt";
	        ins = cntxt.getResourceAsStream(fName);
	        try {
	            if (ins != null) {
	                InputStreamReader isr = new InputStreamReader(ins);
	                BufferedReader reader = new BufferedReader(isr);               
	                String word = "";
	                while ((word = reader.readLine()) != null) {                    
	                	review_fb = review_fb + word;
	                }
	            }
	        }finally {
	        	ins.close();
	        }
	        
	        if(yapReviews.get(x).getUsefulCount()>0)
	        {
	        	review_fb = review_fb.replace("&[useful-count]&", Integer.toString(yapReviews.get(x).getUsefulCount()));
	        }
	        else
	        {
	        	review_fb = review_fb.replace("&[useful-count]&", "");
	        }
	        
	        if(yapReviews.get(x).getFunnyCount()>0)
	        {
	        	review_fb = review_fb.replace("&[funny-count]&", Integer.toString(yapReviews.get(x).getFunnyCount()));
	        }
	        else
	        {
	        	review_fb = review_fb.replace("&[funny-count]&", "");
	        }
	        
	        if(yapReviews.get(x).getCoolCount()>0)
	        {
	        	review_fb = review_fb.replace("&[cool-count]&", Integer.toString(yapReviews.get(x).getCoolCount()));	        
	        }
	        else
	        {
	        	review_fb = review_fb.replace("&[cool-count]&", "");
	        }
	        
	        review_fb = review_fb.replace("BID", Integer.toString(yapReviews.get(x).getReviewID()));
	        review_fb = review_fb.replace("UNAME",uname);
	        
			temp = temp.replace(content, review_fb);			
			
			generatedcontent = generatedcontent + temp;
		}
		
		String allbiz = "";
		fName = "/AllReviewsTemplate.txt";
		ins = cntxt.getResourceAsStream(fName);
		try {
			if (ins != null) {
				InputStreamReader isr = new InputStreamReader(ins);
				BufferedReader reader = new BufferedReader(isr);               
				String word = "";
				while ((word = reader.readLine()) != null) {                    
					allbiz = allbiz + word;
				}
			}
		}finally {
			ins.close();
		}
		
		HttpSession session = request.getSession(false);
		String username="";
		if (session!=null)
		{
			username = (String) session.getAttribute("user_name");
			allbiz = allbiz.replace("$user-name$", username);
			
			session.setAttribute("businessid", business_id);
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
		
		if(DevelopersTable.checkUser(username))
		{
			String devconsole = "<li>" +
									"<a href=\"/Yap/api/console\">Developer Console</a>"+
								"</li>"+
								"<li role=\"sperator\" class=\"divider\"></li>";
			
			allbiz = allbiz.replace("devconsole", devconsole);
		}
		else
		{
			String devconsole = "<li>" +
					"<a href=\"/Yap/DevAppRegistration.htmlr\">Register as Developer</a>"+
				"</li>"+
				"<li role=\"sperator\" class=\"divider\"></li>";
			
			allbiz = allbiz.replace("devconsole", devconsole);
		}
		
		allbiz = allbiz.replace("&[business-id]&", business_id);
		
		allbiz = allbiz.replace("$businesses$", "/Yap/AllBusiness");
		allbiz = allbiz.replace("$all-business-page$", "/Yap/AllBusiness");
		allbiz = allbiz.replace("$business-registration$", "/Yap/BuildAddReview");
		allbiz = allbiz.replace("Add New Business", "Add Review");		
		allbiz = allbiz.replace("generatedcontent", generatedcontent);		
		
		response.setContentType("text/html");
		PrintWriter outx = response.getWriter();
		outx.println(allbiz);		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException	
	{			
		String business_id = request.getParameterValues("business_id")[0];		
		
		HttpSession session0 = request.getSession(false);
        String uname = "";
        if (session0!=null)
		{
        	uname = (String) session0.getAttribute("user_name");        	
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
		
		ArrayList<YapReview> yapReviews = ReviewsTable.getReviews(business_id);
		
		String generatedcontent = "";
		String list_item_template = "";
				
		ServletContext cntxt = getServletContext();
        String fName = "/TemplateRow.txt";
        InputStream ins = cntxt.getResourceAsStream(fName);
        try {
            if (ins != null) {
                InputStreamReader isr = new InputStreamReader(ins);
                BufferedReader reader = new BufferedReader(isr);               
                String word = "";
                while ((word = reader.readLine()) != null) {                    
                    list_item_template = list_item_template + word;
                }
            }
        }finally {
        	ins.close();
        }		     
        
		String temp = "";		
		for(int x=0; x<yapReviews.size(); x++)
		{
			temp = list_item_template;
			
			String imagelink = "&[imgurl]&";
			temp = temp.replace(imagelink, "http://s3-media3.fl.yelpcdn.com/assets/srv0/yelp_styleguide/20983a63ea50/assets/img/default_avatars/user_60_square.png");

			String itemtitle = "&[list-item-title]&";
			temp = temp.replace(itemtitle, yapReviews.get(x).getUserName());

			String itemsubtitle = "&[list-item-subtitle]&";
			temp = temp.replace(itemsubtitle, yapReviews.get(x).getDate());

			String starrating = "&[X-Star]&";
			int rate = yapReviews.get(x).getRating(); 
			String ratingstring = "";
			
			if(rate==1)
			{
				ratingstring = "<i class=\"star-img stars_1\" title=\"1 star rating\">"+
									  "<img alt=\"1 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
									  "</i>";
			}
			if(rate==2)
			{
				ratingstring = "<i class=\"star-img stars_2\" title=\"2 star rating\">"+
						"<img alt=\"2 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						"</i>";
			}
			if(rate==3)
			{
				ratingstring = "<i class=\"star-img stars_3\" title=\"3 star rating\">"+
						"<img alt=\"3 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						"</i>";
			}
			if(rate==4)
			{
				ratingstring = "<i class=\"star-img stars_4\" title=\"4 star rating\">"+
						"<img alt=\"4 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						"</i>";
			}
			if(rate==5)
			{
				ratingstring = "<i class=\"star-img stars_5\" title=\"5 star rating\">"+
						"<img alt=\"5 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						"</i>";
			}
			
			temp = temp.replace(starrating, ratingstring);
			
			String smalldata = "&[small-data]&";
			temp = temp.replace(smalldata, yapReviews.get(x).getReview());			
			
			String content = "&[rate-review-feedback]&";
			
			String review_fb = "";
	        fName = "/ReviewBarTemplate.txt";
	        ins = cntxt.getResourceAsStream(fName);
	        try {
	            if (ins != null) {
	                InputStreamReader isr = new InputStreamReader(ins);
	                BufferedReader reader = new BufferedReader(isr);               
	                String word = "";
	                while ((word = reader.readLine()) != null) {                    
	                	review_fb = review_fb + word;
	                }
	            }
	        }finally {
	        	ins.close();
	        }
			
	        if(yapReviews.get(x).getUsefulCount()>0)
	        {
	        	review_fb = review_fb.replace("&[useful-count]&", Integer.toString(yapReviews.get(x).getUsefulCount()));
	        }
	        else
	        {
	        	review_fb = review_fb.replace("&[useful-count]&", "");
	        }
	        
	        if(yapReviews.get(x).getFunnyCount()>0)
	        {
	        	review_fb = review_fb.replace("&[funny-count]&", Integer.toString(yapReviews.get(x).getFunnyCount()));
	        }
	        else
	        {
	        	review_fb = review_fb.replace("&[funny-count]&", "");
	        }
	        
	        if(yapReviews.get(x).getCoolCount()>0)
	        {
	        	review_fb = review_fb.replace("&[cool-count]&", Integer.toString(yapReviews.get(x).getCoolCount()));	        
	        }
	        else
	        {
	        	review_fb = review_fb.replace("&[cool-count]&", "");
	        }
	        
	        review_fb = review_fb.replace("BID", Integer.toString(yapReviews.get(x).getReviewID()));
	        review_fb = review_fb.replace("UNAME&",uname);
	        
			temp = temp.replace(content, review_fb);			
			
			generatedcontent = generatedcontent + temp;
		}
		
		String allbiz = "";
        fName = "/AllReviewsTemplate.txt";
        ins = cntxt.getResourceAsStream(fName);
        try {
            if (ins != null) {
                InputStreamReader isr = new InputStreamReader(ins);
                BufferedReader reader = new BufferedReader(isr);               
                String word = "";
                while ((word = reader.readLine()) != null) {                    
                	allbiz = allbiz + word;
                }
            }
        }finally {
        	ins.close();
        }
		
		HttpSession session = request.getSession(false);
		String username = "";
        if (session!=null)
		{
        	username = (String) session.getAttribute("user_name");
        	allbiz = allbiz.replace("$user-name$", username);
        	
        	session.setAttribute("businessid", business_id);
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
        
        if(DevelopersTable.checkUser(username))
		{
			String devconsole = "<li>" +
									"<a href=\"/Yap/api/console\">Developer Console</a>"+
								"</li>"+
								"<li role=\"sperator\" class=\"divider\"></li>";
			
			allbiz = allbiz.replace("devconsole", devconsole);
		}
		else
		{
			String devconsole = "<li>" +
					"<a href=\"/Yap/DevAppRegistration.html\">Register as Developer</a>"+
				"</li>"+
				"<li role=\"sperator\" class=\"divider\"></li>";
			
			allbiz = allbiz.replace("devconsole", devconsole);
		}
        
        allbiz = allbiz.replace("&[business-id]&", business_id);
        
        allbiz = allbiz.replace("$businesses$", "/Yap/AllBusiness");
        allbiz = allbiz.replace("$business-registration$", "/Yap/BuildAddReview");
        allbiz = allbiz.replace("Add New Business", "Add Review");
        allbiz = allbiz.replace("generatedcontent", generatedcontent);		
		
		response.setContentType("text/html");
		PrintWriter outx = response.getWriter();
		outx.println(allbiz);		
	}
	
}
