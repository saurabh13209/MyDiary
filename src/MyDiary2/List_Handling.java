package MyDiary2;

public class List_Handling {
	
	private String Date;


	public List_Handling(String date)
	{
		this.setDate(date);

	}
	
	public String toString(){
		return "        "+Date;
		
	}
	
	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

}
