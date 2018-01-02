package MyDiary2;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class head_layout {
	JLabel text = (new JLabel("MyDiary"));
	JPanel head = new JPanel();
	public head_layout(){
		head.setLayout(new MigLayout());
		
		head.add(text);
		text.setBorder(new EmptyBorder(10,350,20,450));
		text.setFont(new Font("consolas",Font.ITALIC,35));
		Color bg = new bg_clr().head_clr;
		head.setBackground(bg);
	
	}
	
	public JPanel get_panel(){
		return head;
	}

}
