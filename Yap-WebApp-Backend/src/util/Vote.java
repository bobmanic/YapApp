package util;

public class Vote 
{
	private String user_name;
	private int vote_id;
	private int review_id;
	private boolean funny= false;
	private boolean cool = false;
	private boolean useful =  false;
	
	public Vote(int vote_id, String user_name, int rid, boolean funny2, boolean cool2, boolean useful2)
	{			
		this.user_name = user_name;
		this.vote_id = vote_id;
		this.review_id = rid;		
	    
		this.funny = funny2;
	    this.cool = cool2;		
	    this.useful = useful2;
	}
	
	public String getUserName()
	{
		return this.user_name;
	}

	public int getVoteID()
	{
		return this.vote_id;
	}

	public int getReviewID()
	{
		return this.review_id;
	}

	public boolean getFunny()
	{
		return this.funny;
	}
	
	public boolean getCool()
	{
		return this.cool;
	}
	
	public boolean getUseful()
	{
		return this.useful;
	}
}
