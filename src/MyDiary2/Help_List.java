package MyDiary2;

public class Help_List {
	String ques;
	String ans;
	public Help_List(String question,String Answer){
		this.ques = question;
		this.ans = Answer;
		
	}
	public String toString(){
		return "<HTML>" +"&nbsp &nbsp &nbsp &nbsp Ques > &nbsp &nbsp "+ "  " +ques+"&nbsp &nbsp ?"+"<br><br>" ;
	}
	public String getAns(){
		return ans;
		
	}
	public String show_panel_question(){
		return ques+" ?";
	}
}
