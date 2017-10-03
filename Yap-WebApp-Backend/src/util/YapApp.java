package util;

public class YapApp 
{
	private String app_name;
	private int app_id;
	
	public YapApp(int app_id, String app_name)
	{			
		this.app_name = app_name;
		this.app_id = app_id;
	}
	
	public String getAppName()
	{
		return this.app_name;
	}

	public int getAppID()
	{
		return this.app_id;
	}
}
