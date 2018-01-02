package MyDiary2;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Save_me {
	
	JTextPane text_ar;
	String head;
	String base;
	boolean close;
	
	/* teat_ar -> JTextPane
	 * head -> Topic 
	 * base -> 'note' or the base you are saving */
	
	public Save_me(JFrame frame,JTextPane text_area,String head,String base){
		this.close=true;
		this.text_ar = text_area;
		this.head = head;
		this.base = base;
		
		main_in(frame);
		
	}

	
	public Save_me(JFrame frame,JTextPane text_area,String head,String base,boolean close){
		this.close=close;
		this.text_ar = text_area;
		this.head = head;
		this.base = base;
		main_in(frame);
	}
	
	FileInputStream file_input ;
	ObjectInputStream object_inp;

	Map<String,Map> returned;
	
	private  void database_recall() throws Exception{
		object_inp =  new ObjectInputStream(new FileInputStream("data.txt"));
		returned = (Map<String,Map>)(object_inp.readObject());
		object_inp.close();
	}
	
	public void main_in(JFrame frame){
		

		/***********************************************************************
		 ** In Saving firstly I have created a skeleton of DataBase         **
		 **	Like font_clr , font_siz ,font_sty                             **
		 **	and Then I have put all the values of the database except     **
		 ** The date or matter i am working                               **
		 **	then simply add all the things in the data.txt                **
		 **	and work will be done                                         **
		 **	know that this is quite lengthy but i will take care of this   **
		 **																    **
		 ** *******************************************************************/

		Map<String,Color> font_clr = new HashMap<String,Color>();
		Map<String, Integer> font_siz = new HashMap<String,Integer>();
		Map<String , String > font_sty = new HashMap<String,String>();
		Map<String,String> font_text = new HashMap<String,String>();

		Map<String,Map> semi_main = new HashMap<String,Map>();

		StyledDocument sd = (StyledDocument) text_ar.getDocument();
		String tr;
		try 
		{
			tr = sd.getText(0,sd.getLength());
			for (int i=0;i<tr.length();i++)
			{
				int f = StyleConstants.getFontSize(sd.getCharacterElement(i).getAttributes());
				String s = StyleConstants.getFontFamily(sd.getCharacterElement(i).getAttributes());
				Color c = StyleConstants.getForeground(sd.getCharacterElement(i).getAttributes());
				font_clr.put(String.valueOf(i), c);
				font_siz.put(String.valueOf(i), f);
				font_sty.put(String.valueOf(i), s);

			}

			font_text.put("Main Text", sd.getText(0, sd.getLength()));

			semi_main.put("Font_Size",font_siz);
			semi_main.put("Font_color",font_clr);
			semi_main.put("Font_style",font_sty);
			semi_main.put("Main Text",font_text);

			database_recall();
			Map<String,Map> allready_font = new HashMap<String,Map>();
			allready_font = returned.get("Font");
			Map<String,Map> allready_main = new HashMap<String,Map>();
			allready_main = returned.get("Main");
			Map<String,Map> allready_note = new HashMap<String,Map>();
			allready_note = returned.get("Note");

			Map<String,Map> got_dat = (Map<String,Map>)returned.get(base);
			//stan_date(new Date().toString())
			if (got_dat.containsKey(head)){
				got_dat.remove(head);
			}
			got_dat.put(head, semi_main);
			Map<String,Map> main_map = new HashMap<String,Map>();

			switch (base){
			case "Font":
				main_map.put("Main", allready_main);
				main_map.put("Note", allready_note);
				break;
			case "Main":
				main_map.put("Font", allready_font);
				main_map.put("Note", allready_note);
				break;
			case "Note":
				main_map.put("Font", allready_font);
				main_map.put("Main", allready_main);
				break;
			}
			
			main_map.put(base, got_dat);
			FileOutputStream fil  = new FileOutputStream("data.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fil);
			oos.writeObject(main_map);

			oos.flush();
			oos.close();
			
			fil.close();
			
			if (close == true){
				
				frame.dispose();
			}
			

		}catch(Exception e){

		}
		
	}
}
