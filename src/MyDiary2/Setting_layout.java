package MyDiary2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import net.miginfocom.swing.MigLayout;

public class Setting_layout {

	JPanel pass_panel = new JPanel(); // Password Panel
	JPanel font_panel = new JPanel();
	JPanel button_panel = new JPanel();
	JPanel head_panel = new JPanel();
	JPanel mini_head = new JPanel();
	JPanel mini_pas = new JPanel();
	JPanel main_panel = new JPanel();

	JLabel set_head = new JLabel("Setting");
	JLabel set_mini_head = new JLabel("                Font Setting                          Password Setting ");
	JTextPane text_unsaved = new JTextPane();
	JTextPane text_saved = new JTextPane();
	JLabel main_set = new JLabel();
	JScrollPane scroll_unsaved = new JScrollPane(text_unsaved,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	JScrollPane scroll_saved = new JScrollPane(text_saved,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	


	JLabel sty_txt = new JLabel("                              Set Style :                     ");
	JComboBox text_sty = new JComboBox();
	JLabel font_txt = new JLabel("                              Set Font   :                     ");
	JComboBox text_font = new JComboBox();
	JLabel clr_txt = new JLabel("                              Set Color :                     ");
	JComboBox text_clr = new JComboBox();

	JLabel pass_text = new JLabel("<HTML>Dairy should be protected <br> Best way is to put password...");;


	JButton save = new JButton("Save Change");
	JButton back = new JButton("Back");


	public Setting_layout(){

		set_head.setBorder(new EmptyBorder(1,250,1,400));
		set_head.setFont(new Font("Blackadder ITC",Font.BOLD,40));
		head_panel.add(back);
		head_panel.add(set_head);

		head_panel.add(set_head);
		head_panel.setBackground(new bg_clr().body_clr);

		set_mini_head.setFont(new Font("consolas",Font.BOLD,20));
		mini_head.add(set_mini_head);
		mini_head.setBackground(new bg_clr().body_clr);



		
		for (int i =4 ;i<80;i=i+4){
			text_font.addItem(String.valueOf(i));
		}

		String[] colorpool ={"black","red","blue","cyan","green","magenta","orange","white","yellow","pink"};

		for (String clr:colorpool){
			text_clr.addItem(clr.toUpperCase());
		}

		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] fonts = e.getAllFonts(); // Get the fonts
		for (Font f : fonts) {

			text_sty.addItem(f.getFontName());
		}

		main_set.setText("<html>Diary Entry is very creative work And Every one want't to make it attractive <br> So here you can choose your favoroite Style font, colour, size and Then <br> you will not need to change repective things each time when you open your<br> diary... Just choose it and the rest of work  will be done by Us...<br><br> **Use Keyboard Up & Down button on ComboBox to see changes in Text Style...");
		main_set.setFont(new Font("consolas",Font.BOLD,13));

		text_unsaved.setPreferredSize(new Dimension(275,140));
		text_unsaved.setBackground(new bg_clr().change_text_clr);
		text_unsaved.setText("Hi!.. I am your sweet text....\n Please Make me pretty\nI am your current day text");
		text_unsaved.setEditable(false);
		text_saved.setText("Hi!.. I am your sweet text....\n Please Make me pretty\nI am your memories");
		text_saved.setEditable(false);
		text_saved.setBackground(new bg_clr().unchange_text_clr);
		text_saved.setPreferredSize(new Dimension(275,140));
		font_panel.setLayout(new MigLayout());
		font_panel.add(main_set, "wrap");
		font_panel.add(new JLabel("   "),"wrap");
		font_panel.add(sty_txt,"split");
		font_panel.add(text_sty,"wrap");
		font_panel.add(font_txt,"split");
		font_panel.add(text_font,"wrap");
		font_panel.add(clr_txt,"split");
		font_panel.add(text_clr,"split");
		font_panel.add(new JLabel("                   "));
		font_panel.add(save,"wrap");
		font_panel.add(new JLabel("   "),"wrap");
		font_panel.add(scroll_unsaved,"split");
		font_panel.add(scroll_saved);
		font_panel.setOpaque(true);
		font_panel.setBackground(new bg_clr().setting_frame);
		font_panel.setBorder(BorderFactory.createTitledBorder("Font Setting"));

		pass_text.setFont(new Font("consolas",Font.BOLD,13));
		pass_panel.setBorder(BorderFactory.createTitledBorder("Set Password"));
		pass_panel.setBackground(new bg_clr().setting_frame);
		pass_panel.setLayout(new MigLayout());
		pass_panel.add(pass_text,"wrap");
		try {
			set_password_panel();
		} catch (Exception e1) {
		}
		main_panel.setLayout(new MigLayout());
		main_panel.add(head_panel,"wrap");
		main_panel.add(mini_head,"wrap");
		main_panel.add(font_panel,"split");
		main_panel.add(pass_panel,"wrap");
		main_panel.setBackground(new bg_clr().body_clr);

		StyledDocument sd_us= text_unsaved.getStyledDocument();
		SimpleAttributeSet norma = new SimpleAttributeSet();
		StyleConstants.setFontSize(norma, Integer.valueOf(String.valueOf(text_font.getSelectedItem())));
		StyleConstants.setFontFamily(norma, String.valueOf(text_sty.getSelectedItem()));
		bg_clr x = new  bg_clr(text_clr.getSelectedItem().toString());
		Color clr = x.get_clr();
		StyleConstants.setForeground(norma, clr);
		
		sd_us.setCharacterAttributes(0, sd_us.getLength(), norma, true);
		
		StyledDocument sd_s= text_saved.getStyledDocument();
		SimpleAttributeSet norma1 = new SimpleAttributeSet();
		StyleConstants.setFontSize(norma1, Integer.valueOf(String.valueOf(text_font.getSelectedItem())));
		StyleConstants.setFontFamily(norma1, String.valueOf(text_sty.getSelectedItem()));
		bg_clr x1 = new  bg_clr(text_clr.getSelectedItem().toString());
		Color clr1 = x1.get_clr();
		StyleConstants.setForeground(norma1, clr1);
		
		sd_s.setCharacterAttributes(0, sd_s.getLength(), norma1, true);
		
		text_font.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {
				change_me c = new change_me();
			}
			
		});
		
		text_sty.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {
				change_me c = new change_me();
			}
			
		});
		
		text_clr.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {
				change_me c = new change_me();
			}
			
		});
		
		
		save.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				ObjectInputStream ois;
				try {
					ois = new ObjectInputStream(new FileInputStream("data.txt"));
					Map<String,Map> returned = (Map<String,Map>)ois.readObject();
					Map<String,String> got_dat = (Map<String,String>)(returned.get("Font"));
					String password = got_dat.get("Pasword");
					String style =  String.valueOf(text_sty.getSelectedItem());
					String clr = String.valueOf(text_clr.getSelectedItem());
					String siz = String.valueOf(text_font.getSelectedItem());
					
					Map<String,String> send_me = new HashMap<String,String>();
					send_me.put("Style", style);
					send_me.put("Color", clr);
					send_me.put("Size", siz);
					send_me.put("Pasword", password);
					send_me.put("ShowMe", got_dat.get("ShowMe"));
					
					returned.remove("Font");
					returned.put("Font", send_me);
					
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.txt"));
					oos.writeObject(returned);
					oos.flush();
					oos.close();
				
					
					JOptionPane.showMessageDialog(font_panel, "Font Style saved! Now You will find this font \nautomatically placed in your diary FontStyle...\n\n You can Change it anytime ..", "Saved!", JOptionPane.PLAIN_MESSAGE, null);
					
					
				} catch (Exception e) {
				}
				

				
			}
			
		});
		
		


		

	}
	
	public class change_me{
		public change_me(){
			StyledDocument sd_us= text_unsaved.getStyledDocument();
			SimpleAttributeSet norma = new SimpleAttributeSet();
			StyleConstants.setFontSize(norma, Integer.valueOf(String.valueOf(text_font.getSelectedItem())));
			StyleConstants.setFontFamily(norma, String.valueOf(text_sty.getSelectedItem()));
			bg_clr x = new  bg_clr(text_clr.getSelectedItem().toString());
			Color clr = x.get_clr();
			StyleConstants.setForeground(norma, clr);
			sd_us.setCharacterAttributes(0, sd_us.getLength(), norma, true);
			
			StyledDocument sd_s= text_saved.getStyledDocument();
			
			SimpleAttributeSet norma1 = new SimpleAttributeSet();
			StyleConstants.setFontSize(norma1, Integer.valueOf(String.valueOf(text_font.getSelectedItem())));
			StyleConstants.setFontFamily(norma1, String.valueOf(text_sty.getSelectedItem()));
			bg_clr x1 = new  bg_clr(text_clr.getSelectedItem().toString());
			Color clr1 = x1.get_clr();
			StyleConstants.setForeground(norma1, clr1);
			sd_s.setCharacterAttributes(0, sd_s.getLength(), norma1, true);
		}
		
	}
	
	
	
	
	private void set_password_panel() throws Exception {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.txt"));
		Map<String,Map> returned = (Map<String,Map>)ois.readObject();

		Map<String,String> got_dat = (Map<String,String>)(returned.get("Font"));
		String password = got_dat.get("Pasword");
		String style =  got_dat.get("Style");
		String clr = got_dat.get("Color");
		String siz = got_dat.get("Size");

		text_sty.setSelectedItem(style);
		text_clr.setSelectedItem(clr);
		text_font.setSelectedItem(siz);

		if (password.equals("")){
			mini_pas.setLayout(new MigLayout());
			no_password_panel(returned,siz,style,clr);
			ois.close();
			
		}else{
			mini_pas.setLayout(new MigLayout());
			password_panel(returned,siz,style,clr,password);
			ois.close();
			
		}
		mini_pas.setBackground(new bg_clr().setting_frame);
		pass_panel.add(mini_pas);
		pass_panel.revalidate();
		pass_panel.repaint();


	}
	private void no_password_panel(Map returned,String siz,String style,String clr){
		mini_pas.removeAll();
		JLabel text = new JLabel("<HTML>You Have not set your password<br> till  now.<br><br>Please set your password and make <br>your diary secure...");
		text.setFont(new Font("consolas",Font.BOLD,13));
		JLabel ask = new JLabel("Please Choose Your Password");
		ask.setFont(new Font("consolas",Font.BOLD,15));
		JPasswordField enter = new JPasswordField(20);
		JButton next = new JButton("Next");

		mini_pas.add(text,"wrap");
		mini_pas.add(new JLabel(" "),"wrap");
		mini_pas.add(ask,"wrap");
		mini_pas.add(new JLabel(" "),"wrap");
		mini_pas.add(enter,"wrap");
		mini_pas.add(new JLabel(" "),"wrap");
		mini_pas.add(new JLabel("                           "),"split");
		mini_pas.add(next,"wrap");
		mini_pas.add(new JLabel("<HTML><br><br>"));

		next.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if ((!enter.getPassword().equals(""))&&(enter.getText().length()>3)){
				Map<String,String> new_map = new HashMap<String,String>();

				new_map.put("Style", style);
				new_map.put("Color", clr);
				new_map.put("Size", siz);
				new_map.put("Pasword", String.valueOf(enter.getPassword()));
				returned.remove("Font");
				returned.put("Font", new_map);

				try {
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.txt"));
					oos.writeObject(returned);
					oos.flush();
					oos.close();
					JOptionPane.showMessageDialog(mini_pas, "You have successfully put Ultra security to your MYDiary!","Congratulation!",JOptionPane.PLAIN_MESSAGE,null);
					mini_pas.removeAll();
					
					try {
						set_password_panel();
					} catch (Exception e0) {
					}
					mini_pas.revalidate();
					mini_pas.repaint();
					
				} catch (IOException e1) {
				}


				}
				else{
					JOptionPane.showMessageDialog(mini_pas, "Sorry! Password is of incorrect formate..\n atleast 4 digit","Wrong",0,null);
				}
			}

		});
	}
	private void password_panel(Map returned,String siz,String style,String clr,String password){

		JLabel text = new JLabel("<HTML>Your Diary is very well protected <br><br>But we suggest you to periodically<br> change your password and make it<br> more secure.<br><br>Want to change your password?");
		text.setFont(new Font("consolas",Font.BOLD,13));
		JLabel ask = new JLabel("Please Enter Existing Password");
		ask.setFont(new Font("consolas",Font.BOLD,14));
		JPasswordField enter = new JPasswordField(20);
		JButton next = new JButton("Next");

		mini_pas.add(text,"wrap");
		mini_pas.add(new JLabel(" "),"wrap");
		mini_pas.add(ask,"wrap");
		mini_pas.add(new JLabel(" "),"wrap");
		mini_pas.add(enter,"wrap");
		mini_pas.add(new JLabel(" "),"wrap");
		mini_pas.add(new JLabel("                           "),"split");
		mini_pas.add(next,"wrap");
		mini_pas.add(new JLabel("<HTML><br><br>"));

		next.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (String.valueOf(enter.getPassword()).equals(password)){
					mini_pas.removeAll();

					JLabel text = new JLabel("Enter your new Password : ");
					text.setFont(new Font("consolas",Font.BOLD,14));
					JLabel ask = new JLabel("Re-Enter your new Password");
					ask.setFont(new Font("consolas",Font.BOLD,14));
					JPasswordField enter_i = new JPasswordField(20);
					JPasswordField enter_ii = new JPasswordField(20);
					JButton next = new JButton("Next");

					mini_pas.add(new JLabel("<HTML><br><br>"),"wrap");
					mini_pas.add(text,"wrap");
					mini_pas.add(new JLabel(" "),"wrap");
					mini_pas.add(enter_i,"wrap");
					mini_pas.add(new JLabel(" "),"wrap");
					mini_pas.add(new JLabel("<HTML><br>"),"wrap");
					mini_pas.add(ask,"wrap");
					mini_pas.add(new JLabel(" "),"wrap");
					mini_pas.add(enter_ii,"wrap");
					mini_pas.add(new JLabel("<HTML><br>"),"wrap");
					mini_pas.add(new JLabel("                           "),"split");
					mini_pas.add(next,"wrap");
					mini_pas.add(new JLabel("<HTML><br><br>"));
					mini_pas.revalidate();
					mini_pas.repaint();

					next.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent arg0) {
							if ((String.valueOf(enter_i.getPassword()).equals(String.valueOf(enter_ii.getPassword())))&&(enter_i.getText().length()>3)){
								Map<String,String> new_map = new HashMap<String,String>();

								new_map.put("Style", style);
								new_map.put("Color", clr);
								new_map.put("Size", siz);
								new_map.put("Pasword", String.valueOf(enter_i.getPassword()));
								returned.remove("Font_main_styling");
								returned.put("Font_main_styling", new_map);

								try {
									ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.txt"));
									oos.writeObject(returned);
									oos.flush();
									oos.close();
									JOptionPane.showMessageDialog(mini_pas, "You have successfully Changed Your password..","Congratulation!",JOptionPane.PLAIN_MESSAGE,null);
									mini_pas.removeAll();
									try {
										set_password_panel();
									} catch (Exception e) {
									}
									mini_pas.revalidate();
									mini_pas.repaint();
								} catch (IOException e1) {
								}

							}
							else{
								JOptionPane.showMessageDialog(mini_pas, "Somenthing is wrong !\ni>Atleast 4 digit\nii>Should match to each other.", "wrong", 0, null);
							}

						}

					});
				}
				else{
					JOptionPane.showMessageDialog(mini_pas, "Entered Password is Incorrect ! \n Please Retry...","Oops! Password is Incorrect", 0);
				}
			}

		});
	}
	
	public JPanel get_panel(){
		return main_panel;
	}


}
