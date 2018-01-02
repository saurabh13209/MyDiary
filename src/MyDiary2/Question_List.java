package MyDiary2;

import java.util.HashMap;
import java.util.Map;

public class Question_List {
	Map<String,String> quest_list = new HashMap<String,String>();
	
	public Question_List(){
		quest_list.put("Why to do 'diary entry'", "Since in whole day you met a lot of people and its very important to know that what youn are actually doing in the whole day..\nMoreover Sometime you need something which help you to give a therapy for bad days, event so it is the best place where you can share your feeling and get relaxed.");
		quest_list.put("What 'MyDiary' Button do","It is a button which will open your diary for diary entry");
		quest_list.put("What 'My Thoughts' button do", "My Thoughts is feature where you can write about any thing like event, Feeling , Moment etc...");
		quest_list.put("Why previous day entry cannot be edited", "Since till now you can't manage to go back in time and change it according to you, Diary Enrty is also like describing a day which cannot be changed as that day passed ,so keeping this thing in mind we have decided this thing");
		
	}
	public Map<String,String> getList(){
		return quest_list;
		
	}

}
