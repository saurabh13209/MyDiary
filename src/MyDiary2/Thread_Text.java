package MyDiary2;

import java.awt.Color;
import java.util.Map;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Thread_Text implements Runnable{
	String Text = " ";
	Map<String, String> font_size;
	Map<String, Color> font_color ;
	Map<String, String> font_style ;
	StyledDocument sd;



	public Thread_Text(StyledDocument sd, String text, Map<String, String> font_size, Map<String, Color> font_color, Map<String, String> font_style){
		this.Text = text;
		this.font_size = font_size;
		this.font_color = font_color;
		this.font_style = font_style;
		this.sd = sd;


	}

	@Override
	public void run() {
		
		SimpleAttributeSet norma1 = new SimpleAttributeSet();
		
		int i=0;
		while (i<Text.length()){
			try{
			StyleConstants.setFontSize(norma1, Integer.valueOf(String.valueOf(font_size.get(String.valueOf(i)))));
			StyleConstants.setFontFamily(norma1, String.valueOf(font_style.get(String.valueOf(i))));
			StyleConstants.setForeground(norma1, new Color(Integer.parseInt(Integer.toString(font_color.get(String.valueOf(i)).getRGB()))));
			
				sd.insertString(i, Text.substring(i, i+1), norma1);
			} catch (Exception e) {
			}
			
			/*
			// Old statement:
			//sd.setCharacterAttributes(i, i+1, norma1, true);
			*/
			
			i++;
		}

	}

	public void set_all_text(){

	}

}
