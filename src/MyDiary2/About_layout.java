package MyDiary2;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class About_layout {
	
	JPanel about_panel = new JPanel();
	JLabel about_head = new JLabel();
	JTextArea about_text = new JTextArea(18,62);
	JScrollPane about_scroll = new JScrollPane(about_text,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	JButton about_back = new JButton("Back");
	public About_layout(String txt){
		
		about_panel.setLayout(new MigLayout());
		about_panel.setBackground(new bg_clr().body_clr);
		about_panel.add(about_head, "wrap");
		about_panel.add(new JLabel("                                   "),"split");
		about_panel.add(about_scroll, "wrap");
		about_panel.add(new JLabel("                                   "),"split");
	    about_panel.add(about_back);

		about_head.setText("Help");
		about_head.setBorder(new EmptyBorder(10,400,10,0));
		about_head.setFont(new Font("Blackadder ITC",Font.BOLD,40));
		
		about_text.setText(txt);
		about_text.setLineWrap(true);
		about_text.setEditable(false);
		about_text.setWrapStyleWord(true);
		about_text.setFont(new Font("consolas",Font.PLAIN,20));
		about_text.setAutoscrolls(true);
		about_text.setBorder(new EmptyBorder(1,20,1,1));
		about_text.setBackground(new bg_clr().unchange_text_clr);
	}
	
	public JPanel get_panel(){
		return about_panel;
	}

}
