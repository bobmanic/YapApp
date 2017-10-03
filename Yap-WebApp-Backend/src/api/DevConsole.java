package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database_connectors.DevelopersTable;
import util.YapApp;

@SuppressWarnings("serial")
public class DevConsole extends HttpServlet
{	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException	
	{		
		String user_name = "";
		HttpSession session = request.getSession(false);
        if (session!=null)
		{
        	user_name = (String) session.getAttribute("user_name");        	
		}
        else
        {        	        
        		 response.sendRedirect("/Yap/Landing.html");			
        }
		
		ArrayList<YapApp> allYapApps = DevelopersTable.getAllApps(user_name);
				
		String generatedcontent = "";
		String list_item_template = "";
		
		ServletContext cntxt = getServletContext();

        String fName = "/TemplateRow2.txt";
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
		for(int x=0; x<allYapApps.size(); x++)
		{
			temp = list_item_template;

			temp = temp.replace("&[app_id]&", Integer.toString(allYapApps.get(x).getAppID()));
			temp = temp.replace("&[user_name]&", user_name);
			
			String itemtitle = "&[list-item-title]&";
			String title = "<h3>"+ allYapApps.get(x).getAppName() + "[AppID: " + allYapApps.get(x).getAppID() + "]" +"<h3>";
			temp = temp.replace(itemtitle, title);											
			
			generatedcontent = generatedcontent + temp;
		}
				
		String allbiz = "";
        fName = "/DevConsole.txt";
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
         
        allbiz = allbiz.replace("$user-name$", user_name);
        
        allbiz = allbiz.replace("generatedcontent", generatedcontent);
		
		response.setContentType("text/html");
		PrintWriter outx = response.getWriter();
		outx.println(allbiz);
		
	}
	
	
}
