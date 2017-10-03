package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database_connectors.BusinessTable;
import database_connectors.DevelopersTable;
import database_connectors.ReviewsTable;
import util.YapBusiness;

@SuppressWarnings("serial")
public class AllBusiness extends HttpServlet
{	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		ArrayList<YapBusiness> allYapBusiness = BusinessTable.getAllBusinesses();
		
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
		for(int x=0; x<allYapBusiness.size(); x++)
		{
			temp = list_item_template;
			
			String imagelink = "&[imgurl]&";
			temp = temp.replace(imagelink, "http://s3-media2.fl.yelpcdn.com/assets/srv0/yelp_styleguide/904d3ac00f48/assets/img/default_avatars/business_60_square.png");

			String itemtitle = "&[list-item-title]&";
			String title = "<h3><a href=\"" + "/Yap/AllReviews?business_id=" + allYapBusiness.get(x).getBusinessID()+"\">"+ allYapBusiness.get(x).getBusinessName() +"</a><h3>";
			temp = temp.replace(itemtitle, title);

			String itemsubtitle = "&[list-item-subtitle]&";
			temp = temp.replace(itemsubtitle, allYapBusiness.get(x).getBusinessType());

			String starrating = "&[X-Star]&";
			String ratingstring = "";
			
			if(ReviewsTable.getAvgRating(allYapBusiness.get(x).getBusinessID()) == 1)
			{
				ratingstring = "<i class=\"star-img stars_1\" title=\"1 star rating\">"+
						"<img alt=\"1 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						"</i>";
			}
			if(ReviewsTable.getAvgRating(allYapBusiness.get(x).getBusinessID()) == 1.5)
			{
				ratingstring = "<i class=\"star-img stars_1_half\" title=\"1.5 star rating\">"+
						"<img alt=\"1.5 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						"</i>";
			}
			if(ReviewsTable.getAvgRating(allYapBusiness.get(x).getBusinessID()) == 2)
			{
				ratingstring = "<i class=\"star-img stars_2\" title=\"2 star rating\">"+
						"<img alt=\"2 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						"</i>";
			}
			if(ReviewsTable.getAvgRating(allYapBusiness.get(x).getBusinessID()) == 2.5)
			{
			ratingstring = "<i class=\"star-img stars_2_half\" title=\"2.5 star rating\">"+
						   "<img alt=\"2.5 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						   "</i>";
			}
			if(ReviewsTable.getAvgRating(allYapBusiness.get(x).getBusinessID()) == 3)
			{
				ratingstring = "<i class=\"star-img stars_3\" title=\"3 star rating\">"+
						"<img alt=\"3 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						"</i>";
			}
			if(ReviewsTable.getAvgRating(allYapBusiness.get(x).getBusinessID()) == 3.5)
			{
				ratingstring = "<i class=\"star-img stars_3_half\" title=\"3.5 star rating\">"+
						"<img alt=\"3.5 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						"</i>";
			}
			if(ReviewsTable.getAvgRating(allYapBusiness.get(x).getBusinessID()) == 4)
			{
				ratingstring = "<i class=\"star-img stars_4\" title=\"4 star rating\">"+
						"<img alt=\"4 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						"</i>";
			}
			if(ReviewsTable.getAvgRating(allYapBusiness.get(x).getBusinessID()) == 4.5)
			{
				ratingstring = "<i class=\"star-img stars_4_half\" title=\"4.5 star rating\">"+
						"<img alt=\"4.5 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						"</i>";
			}
			if(ReviewsTable.getAvgRating(allYapBusiness.get(x).getBusinessID()) == 5)
			{
				ratingstring = "<i class=\"star-img stars_5\" title=\"5 star rating\">"+
						"<img alt=\"5 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						"</i>";
			}
			
			temp = temp.replace(starrating, ratingstring);
			
			String smalldata = "&[small-data]&";
			temp = temp.replace(smalldata, allYapBusiness.get(x).getBusinessHeader());			
			
			String content = "&[rate-review-feedback]&";
			temp = temp.replace(content, " ");			
			
			generatedcontent = generatedcontent + temp;
		}
		
		//
		
		String allbiz = "";
        fName = "/AllBusinessTemplate.txt";
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
        
        allbiz = allbiz.replace("$business-registration$", "/Yap/BusinessRegistration.html");        
        allbiz = allbiz.replace("$all-business-page$", "/Yap/AllBusiness");        
        
        allbiz = allbiz.replace("generatedcontent", generatedcontent);
		//
		
		response.setContentType("text/html");
		PrintWriter outx = response.getWriter();
		outx.println(allbiz);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException	
	{		
		ArrayList<YapBusiness> allYapBusiness = BusinessTable.getAllBusinesses();
				
		String generatedcontent = "";
		String list_item_template = "";
		
		//ServletContext cntxt = request.getServletContext();
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
		for(int x=0; x<allYapBusiness.size(); x++)
		{
			temp = list_item_template;
			
			String imagelink = "&[imgurl]&";
			temp = temp.replace(imagelink, "http://s3-media2.fl.yelpcdn.com/assets/srv0/yelp_styleguide/904d3ac00f48/assets/img/default_avatars/business_60_square.png");

			String itemtitle = "&[list-item-title]&";
			String title = "<h3><a href=\"" + "/Yap/AllReviews?business_id=" + allYapBusiness.get(x).getBusinessID()+"\">"+ allYapBusiness.get(x).getBusinessName() +"</a><h3>";
			temp = temp.replace(itemtitle, title);

			String itemsubtitle = "&[list-item-subtitle]&";
			temp = temp.replace(itemsubtitle, allYapBusiness.get(x).getBusinessType());

			String starrating = "&[X-Star]&";
			String ratingstring = "";
			
			if(ReviewsTable.getAvgRating(allYapBusiness.get(x).getBusinessID()) == 1)
			{
				ratingstring = "<i class=\"star-img stars_1\" title=\"1 star rating\">"+
						"<img alt=\"1 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						"</i>";
			}
			if(ReviewsTable.getAvgRating(allYapBusiness.get(x).getBusinessID()) == 1.5)
			{
				ratingstring = "<i class=\"star-img stars_1_half\" title=\"1.5 star rating\">"+
						"<img alt=\"1.5 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						"</i>";
			}
			if(ReviewsTable.getAvgRating(allYapBusiness.get(x).getBusinessID()) == 2)
			{
				ratingstring = "<i class=\"star-img stars_2\" title=\"2 star rating\">"+
						"<img alt=\"2 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						"</i>";
			}
			if(ReviewsTable.getAvgRating(allYapBusiness.get(x).getBusinessID()) == 2.5)
			{
			ratingstring = "<i class=\"star-img stars_2_half\" title=\"2.5 star rating\">"+
						   "<img alt=\"2.5 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						   "</i>";
			}
			if(ReviewsTable.getAvgRating(allYapBusiness.get(x).getBusinessID()) == 3)
			{
				ratingstring = "<i class=\"star-img stars_3\" title=\"3 star rating\">"+
						"<img alt=\"3 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						"</i>";
			}
			if(ReviewsTable.getAvgRating(allYapBusiness.get(x).getBusinessID()) == 3.5)
			{
				ratingstring = "<i class=\"star-img stars_3_half\" title=\"3.5 star rating\">"+
						"<img alt=\"3.5 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						"</i>";
			}
			if(ReviewsTable.getAvgRating(allYapBusiness.get(x).getBusinessID()) == 4)
			{
				ratingstring = "<i class=\"star-img stars_4\" title=\"4 star rating\">"+
						"<img alt=\"4 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						"</i>";
			}
			if(ReviewsTable.getAvgRating(allYapBusiness.get(x).getBusinessID()) == 4.5)
			{
				ratingstring = "<i class=\"star-img stars_4_half\" title=\"4.5 star rating\">"+
						"<img alt=\"4.5 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						"</i>";
			}
			if(ReviewsTable.getAvgRating(allYapBusiness.get(x).getBusinessID()) == 5)
			{
				ratingstring = "<i class=\"star-img stars_5\" title=\"5 star rating\">"+
						"<img alt=\"5 star rating\" class=\"offscreen\" height=\"303\" src=\"//s3-media4.fl.yelpcdn.com/assets/srv0/yelp_styleguide/c2252a4cd43e/assets/img/stars/stars_map.png\" width=\"84\">"+
						"</i>";
			}
			
			temp = temp.replace(starrating, ratingstring);
			
			String smalldata = "&[small-data]&";
			temp = temp.replace(smalldata, allYapBusiness.get(x).getBusinessHeader());			
			
			String content = "&[rate-review-feedback]&";
			temp = temp.replace(content, " ");			
			
			generatedcontent = generatedcontent + temp;
		}
		
		//
		
		System.out.println("Context = "+ cntxt);
		
		
		String allbiz = "";
        fName = "/AllBusinessTemplate.txt";
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
                
        allbiz = allbiz.replace("$business-registration$", "/Yap/BusinessRegistration.html");
        allbiz = allbiz.replace("$business-registration$", "/Yap/BusinessRegistration.html");
      
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
        
        allbiz = allbiz.replace("generatedcontent", generatedcontent);
		//
		
		response.setContentType("text/html");
		PrintWriter outx = response.getWriter();
		outx.println(allbiz);
		
	}	
	
}
