package MyDiary2;

import java.awt.Color;
import java.lang.reflect.Field;
	
public class bg_clr {
	String text ;
    Color color;
    Field field;
    
    Color setting_frame = new Color(90,175,250);
    Color head_clr = new Color(255,51,51);
	Color body_clr = new Color(255,204,204);
	Color unchange_text_clr = new Color(255,255,140);
	Color change_text_clr = new Color(255,255,220);
	Color about_txt_clr = new Color(255,178,102);
	Color list_clr = new Color(150,235,255);
	Color list_text = Color.BLUE.brighter();
	Color list_sel_text = Color.red.darker().brighter();
	
	public bg_clr(){
		
	}
	public bg_clr(String clr){
		this.text = clr;
		try {
			field = Class.forName("java.awt.Color").getField(text.toLowerCase());
			color = (Color)field.get(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Color get_clr(){
		return color;
	}

}
