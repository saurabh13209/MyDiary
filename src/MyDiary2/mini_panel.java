package MyDiary2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class mini_panel {
	JPanel panel = new JPanel();
	
	JLabel head_label = new JLabel("              For This Post ");
	JLabel new_label = new JLabel("Create New Thought : ");
	JTextField new_field = new JTextField();
	JLabel old_label = new JLabel("Select from Old one : ");
	DefaultListModel<mini_handling> model = new DefaultListModel<mini_handling>();
	JList<mini_handling> list = new JList();
	JButton next = new JButton("Next");
	JButton back = new JButton("Back");
	
	
	public mini_panel(){
		list.setModel(model);
		model.removeAllElements();
		JScrollPane scroll_list = new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll_list.setPreferredSize(new Dimension(500,300));
		panel.setLayout(new MigLayout());
		panel.setBackground(new bg_clr().body_clr);
		head_label.setFont(new Font("consolas",Font.BOLD,21));
		panel.add(head_label,"wrap");
		panel.add(new JLabel("    "),"wrap");
		panel.add(new_label,"wrap");
		new_field.setPreferredSize(new Dimension(500, 15));
		panel.add(new_field,"wrap");
		panel.add(new JLabel(" "),"wrap");
		panel.add(old_label, "wrap");
		panel.add(scroll_list,"wrap");
		panel.add(new JLabel("   "),"wrap");
		panel.add(new JLabel("                                       "),"split");
		panel.add(next,"split");
		panel.add(back);
		
	}
	
	public JPanel panel(){
		return panel;
	}

}
