package MyDiary2;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class welcome {
	JButton diary = new JButton("My Diary");
	JButton note = new JButton("My Thoughts");
	JButton faq = new JButton("F.A.Q");
	 JButton about = new JButton("Help");
	JButton help = new JButton("Setting");
	 JButton exit = new JButton("Exit");
	JLabel own = new JLabel("      Saurabh Production. All Right Reserved 2017-2018");
	
	JPanel main = new JPanel();
	JPanel option_panel = new JPanel();
	public welcome(){
		option_panel.setLayout(new GridLayout(3,2,40,40));
		
		option_panel.setBackground(new bg_clr().body_clr);
		diary.setMargin(new Insets(25,180,25,170));
		
		option_panel.add(diary);
		option_panel.add(note);
		option_panel.add(help);
		option_panel.add(faq);
		option_panel.add(about);
		option_panel.add(exit);
		JPanel owner = new JPanel();
		own.setFont(new Font("consolas",Font.CENTER_BASELINE,25));
		owner.setBackground(new bg_clr().body_clr);
		own.setBorder(new EmptyBorder(350,1,1,1));
		owner.add(own);
		
		main.setLayout(new MigLayout());
		main.add(option_panel, "wrap");
		main.add(owner);
		main.setBackground(new bg_clr().body_clr);
	}
	public JPanel get_panel(){
		return main;
	}
}
