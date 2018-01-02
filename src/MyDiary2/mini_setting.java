package MyDiary2;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class mini_setting {
	JPanel panel = new JPanel();
	JLabel head = new JLabel("             Defalut");
	JLabel text = new JLabel();
	
	DefaultListModel<set_handling> model = new DefaultListModel<set_handling>();
	JList<set_handling> list = new JList<set_handling>();
	JButton next = new JButton("Next");;
	JButton back = new JButton("Back");
	
	public mini_setting(){
		panel.setLayout(new MigLayout());
		panel.setBackground(new bg_clr().unchange_text_clr);
		
		head.setFont(new Font("consolas",Font.PLAIN,25));
		text.setText(" Here you can set a default Topic from Saved Topics..");
		text.setFont(new Font("Comic Sans MS",Font.BOLD,17));
		list.setPreferredSize(new Dimension(420,800));
		list.setModel(model);
		
		panel.add(head, "wrap");
		panel.add(new JLabel(" "), "wrap");
		panel.add(text,"wrap");
		panel.add(new JLabel(" "),"wrap");
		panel.add(new JLabel("      "),"split");
		panel.add(list, "wrap");
		panel.add(new JLabel(" "), "wrap");
		panel.add(new JLabel("                                                "), "split");
		panel.add(next, "split");
		panel.add(back);
		
	
	}
	public JPanel get_panel(){
		return panel;
		
	}
	

}
