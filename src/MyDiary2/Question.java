package MyDiary2;

import java.awt.Dimension; 
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class Question {

	JPanel ques_panel = new JPanel();
	JPanel show_panel = new JPanel();

	JPanel head_panel  =new JPanel();
	JPanel mid = new JPanel();
	
	JButton back  =new JButton("Back");

	DefaultListModel<Help_List> model = new DefaultListModel();
	JList<Help_List> list = new JList();


	JLabel set_head = new JLabel("<HTML>Frequently Asked Question<br><br>");


	public Question(){

		ques_panel.setLayout(new MigLayout());

		set_head.setBorder(new EmptyBorder(1,230,1,200));
		set_head.setFont(new Font("consolas",Font.BOLD,30));

		head_panel.add(set_head);
		head_panel.setBackground(new bg_clr().body_clr);


		mid.setLayout(new MigLayout());
		list.setPreferredSize(new Dimension(650,350));
		mid.add(list);
		list.setBackground(new bg_clr().unchange_text_clr);


		ques_panel.setBackground(new bg_clr().body_clr);
		ques_panel.add(head_panel, "wrap");
		ques_panel.add(new JLabel("                                          "),"split");
		ques_panel.add(mid, "wrap");
		ques_panel.add(new JLabel("  "), "wrap");
		ques_panel.add(back);


		list.setModel(model);

		Question_List ques_lst  =new Question_List();
		ArrayList<String> question = new ArrayList<String>(ques_lst.getList().keySet());
		for (int i=0;i<question.size();i++){
			model.addElement(new Help_List(question.get(i),String.valueOf(ques_lst.getList().get(question.get(i)))));
		}

		list.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_ENTER){
					show_panel_help();

				}

			}

			private void show_panel_help() {
				JLabel head = new JLabel(list.getSelectedValue().show_panel_question());
				head.setFont(new Font("consolas",Font.BOLD,18));

				String ans_r = list.getSelectedValue().getAns().toString().trim();
				String main_ans = "<HTML>";

				for (int i=0;i<ans_r.length();i++){
					main_ans = main_ans+ans_r.charAt(i);
					if ((i%75==0 )&& (i!=0) && (String.valueOf(ans_r.charAt(i)).equals(" "))){
						main_ans = main_ans+"<br>";
					}
					else if ((i%75==0 )&& (i!=0)){
						main_ans = main_ans+"-"+"<br>";
					}
				}

				JLabel ans = new JLabel(main_ans);
				ans.setFont(new Font("consolas",Font.BOLD,12));

				show_panel.setLayout(new MigLayout());
				show_panel.add(head,"wrap");
				show_panel.add(new JLabel("     "), "wrap");
				show_panel.add(ans);
				JOptionPane.showMessageDialog(ques_panel, show_panel);
				show_panel.removeAll();
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {

			}

			@Override
			public void keyTyped(KeyEvent arg0) {

			}

		});
		
		
	
	}

	public JPanel get_panel(){
		return ques_panel;

	}

}
