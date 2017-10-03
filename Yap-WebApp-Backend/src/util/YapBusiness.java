package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.simple.JSONObject;

public class YapBusiness implements Comparable<YapBusiness>
{	
	private String businessID;
	private String name;
	private String city;
	private String state;
	private double latitude;
	private double longitude;
	private String type;
	private ArrayList<String> neighbours;
	
	public YapBusiness(String businessId, String name, String city, String state, double latitude, double longitude, String type)
	{	
		this.businessID = businessId;
		this.name = name;
		this.city = city;
		this.state = state;
		this.latitude = latitude;
		this.longitude = longitude;
		this.type = type;
		neighbours = new ArrayList<String>();
	}
	
	public YapBusiness(String businessId, String name, String city, String state, double latitude, double longitude, String type, String neighborhoods)
	{
		this.businessID = businessId;
		this.name = name;
		this.city = city;
		this.state = state;
		this.latitude = latitude;
		this.longitude = longitude;
		this.type = type;
		
		neighbours = new ArrayList<String>();
		String []totalNeighbours = neighborhoods.split(",");
		for(String neighbour1:totalNeighbours)
		{
			neighbours.add(neighbour1);
		}
		Collections.sort(neighbours);
	}
	
	/**
	 * Compares the business name first. Sort by business ID if Business names are same.
	 * @return integer.
	 */
	public int compareTo(YapBusiness yBusiness)
	{
		if(businessID.equals(yBusiness.businessID))
		{
			return 0;
		}
		int compare = name.compareTo(yBusiness.name);
		if(compare == 0)
		{
			return businessID.compareTo(yBusiness.businessID);
		}
		else
		{
			return compare;
		}
	}	

	public String getBusinessType()
	{
		return type;
	}
	
	public String getBusinessID()
	{
		return businessID;
	}
	
	public String getBusinessName()
	{
		return name;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public String getState()
	{
		return state;
	}
	
	public double getLatitude()
	{
		return latitude;
	}
	
	public double getLongitude()
	{
		return longitude;
	}
		
	public ArrayList<String> getNeighbourhoods()
	{
		return neighbours;
	}
	
	public String getNeighbourhoodsCSV()
	{
		String neighbourhoodString = "";
		Collections.sort(neighbours);
		
		for(int r = 0; r<neighbours.size(); r++)
		{
			if(neighbours.size()>1)
			{
				if(r==0)
				{
					neighbourhoodString = neighbourhoodString + neighbours.get(r).trim();
				}
				else if(r>0)
				{
					neighbourhoodString = neighbourhoodString + "," + neighbours.get(r).trim();
				}
			}			
			else
			{
				neighbourhoodString = neighbours.get(r).trim();
			}
		}
		
		if(neighbourhoodString.substring(neighbourhoodString.length()).equalsIgnoreCase(","))
		{
			neighbourhoodString = neighbourhoodString.substring(0,neighbourhoodString.length()-1);
		}
		return neighbourhoodString;
	}
	
	/**
	 * Build Business Header in required format and return it.
	 * @return String.
	 */
	public String getBusinessHeader()
	{	
		StringBuilder businessHeader = new StringBuilder();
		
		/*businessHeader.append(this.getBusinessName());
		businessHeader.append(" - ");*/
		businessHeader.append(this.getCity());
		businessHeader.append(", ");
		businessHeader.append(this.getState());
		businessHeader.append(" (");
		businessHeader.append(this.getLatitude());
		businessHeader.append(", ");		
		businessHeader.append(this.getLongitude());
		businessHeader.append(") (");		
		businessHeader.append(this.getNeighbourhoods().toString().replaceAll("\\[|\\]", ""));
		businessHeader.append(")\n");
		
		return businessHeader.toString();
	}
	
	/**
	 * Build Business Header in JSON format and return it.
	 * @return String.
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getBusinessJSON()
	{	
		JSONObject obj=new JSONObject();		
		obj.put("business_name",this.getBusinessName());
		obj.put("city",this.getCity());
		obj.put("state",this.getState());
		obj.put("latitude",this.getLatitude());
		obj.put("longitude",this.getLongitude());
		obj.put("type",this.getBusinessType());
		obj.put("neighbourhood",this.getNeighbourhoods().toString().replaceAll("\\[|\\]", ""));
		
		return obj;
	}
}

/**
 * ComparatorBID for sorting the businesses in YapBusiness class
 *  as per alphabetical order of respective BusinessIDs.
 */
class ComparatorBID implements Comparator<YapBusiness>
{
	public int compare(YapBusiness YBusiness1, YapBusiness YBusiness2)
	{
		if(YBusiness1.getBusinessID().equals(YBusiness2.getBusinessID()))
		{
			return 0;
		}
		else
		{
			return YBusiness1.getBusinessID().compareTo(YBusiness2.getBusinessID());
		}
	}
}
