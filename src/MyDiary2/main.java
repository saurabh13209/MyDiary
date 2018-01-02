package MyDiary2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import net.miginfocom.swing.MigLayout;

public class main 
{

	JFrame frame = new JFrame("MyDiary");

	JPanel main_panel = new JPanel();
	JPanel stuf_panel = new JPanel();

	JPanel diary_panel = new JPanel();
	JPanel about_panel = new JPanel();


	FileOutputStream file_output ;
	ObjectOutputStream object_out;

	FileInputStream file_input ;
	ObjectInputStream object_inp;

	Map<String,Map> returned;


	public main()
	{
		main_panel.setLayout(new MigLayout());

		head_layout head_obj = new head_layout();
		main_panel.add(head_obj.get_panel(),"wrap");
		main_panel.setBackground(new bg_clr().body_clr);

		option_frame();
		stuf_panel.setBackground(new bg_clr().body_clr);
		main_panel.add(stuf_panel);
		frame.add(main_panel);

		frame.setDefaultCloseOperation(frame.HIDE_ON_CLOSE);
		frame.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/8, dim.height/4-175);
		frame.setSize(900,700 );
		frame.setResizable(false);
	
		try {
			check_dataBase();
		} catch (Exception e) {
		}

		all_system_tray();

	}

	private void all_system_tray() {
		
		new system_tray(frame,stuf_panel);
	}

	public void database_recall() throws Exception{
		object_inp =  new ObjectInputStream(new FileInputStream("data.txt"));
		returned = (Map<String,Map>)(object_inp.readObject());
		object_inp.close();
	}

	public void option_frame()
	{
		stuf_panel.removeAll();
		welcome option_obj = new welcome();
		stuf_panel.add(option_obj.get_panel());
		stuf_panel.revalidate();
		stuf_panel.repaint();

		option_btn(option_obj.about);
		option_btn(option_obj.note);
		option_btn(option_obj.diary);
		option_btn(option_obj.help);
		option_btn(option_obj.exit);
		option_btn(option_obj.faq);
	}

	public void diary_frame() throws Exception
	{
		stuf_panel.removeAll();
		stuf_panel.revalidate();
		stuf_panel.repaint();

		database_recall();

		Map<String,String> font_get = (Map<String, String>) returned.get("Font");
		diary diary_obj = new diary(font_get.get("Size"),font_get.get("Style"),font_get.get("Color"));

		stuf_panel.add(diary_obj.get_scroll());
		stuf_panel.revalidate();
		stuf_panel.repaint();


		diary_obj.list.requestFocus();

		diary(diary_obj.save,diary_obj);
		diary(diary_obj.back,diary_obj);

		try
		{
			diary_back_coding(diary_obj);

		}
		catch(Exception e)
		{

		}

	}

	public void note_frame() throws Exception{

		database_recall();
		Map<String,String> font_get = (Map<String, String>) returned.get("Font");
		Note_layout note = new Note_layout(font_get.get("Size"),font_get.get("Style"),font_get.get("Color"));


		stuf_panel.removeAll();
		stuf_panel.add(note.get_scroll());
		stuf_panel.revalidate();
		stuf_panel.repaint();

		database_recall();
		Map<String,Map> got_dat = (Map<String,Map>)(returned.get("Note"));
		ArrayList<String> main_lst  =new ArrayList<String>(got_dat.keySet());

		for (int i=0;i<main_lst.size();i++){
			note.model.addElement( (new Topic_List_Handler(main_lst.get(i))));	
		}
		note.text_ar.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {


			}

			@Override
			public void keyReleased(KeyEvent arg0) {

				StyledDocument sd = (StyledDocument) note.text_ar.getDocument();

				SimpleAttributeSet norma = new SimpleAttributeSet();
				StyleConstants.setFontFamily(norma,String.valueOf(note.style_ch.getSelectedItem()));
				StyleConstants.setForeground(norma, new bg_clr(String.valueOf(note.color_ch.getSelectedItem().toString())).get_clr());
				StyleConstants.setFontSize(norma, Integer.valueOf(String.valueOf(note.font_len.getSelectedItem())));
				sd.setCharacterAttributes(sd.getLength()-1, sd.getLength(), norma, true);
				note.text_ar.requestFocus();

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
		note.nclose.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String topic = note.topic.getText();
				new  Save_me(frame,note.text_ar,topic,"Note",false);
				try {
					database_recall();
				} catch (Exception e) {
				}
			}

		});


		note.list.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					database_recall();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				Map<String,Map> got =  (Map<String,Map>)(returned.get("Note"));
				try{
					
				Map<String,Map> got_dat = (Map<String,Map>)(got.get(note.list.getSelectedValue().toString().trim()));
				RawText_ShowText(got_dat,note.text_ar);
				note.topic.setText(String.valueOf(note.list.getSelectedValue()).trim());
				note.change.setEnabled(true);
				note.topic.setEditable(false);
				note.topic.setForeground(Color.RED);}catch(Exception ep){}
			


			}
		});

		note.change.addActionListener(new ActionListener(){
			String old_name;
			@Override
			public void actionPerformed(ActionEvent btn) {
				if (btn.getActionCommand()=="Re-Name"){
					this.old_name = note.topic.getText().trim();
					note.text_ar.setEditable(false);
					note.list.setEnabled(false);
					note.change.setText("Save Name");
					note.topic.setEditable(true);
					note.back.setEnabled(false);
					note.save.setEnabled(false);

				}
				if (btn.getActionCommand()=="Save Name"){
					note.text_ar.setEditable(true);
					note.list.setEnabled(true);
					note.change.setText("Re-Name");
					note.topic.setEditable(false);
					note.back.setEnabled(true);
					note.save.setEnabled(true);

					try {
						database_recall();
						Map<String,Map> got_dat= (Map<String,Map>)(returned.get("Note"));
						Map<String,Map>th = got_dat.get(old_name);
						got_dat.remove(old_name);
						got_dat.put(note.topic.getText().toString().trim(), th);
						returned.remove("Note");
						returned.put("Note", got_dat);
						ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.txt"));
						oos.writeObject(returned);
						oos.flush();
						oos.close();
						database_recall();
						note.model.clear();

						database_recall();
						Map<String,Map> got_dat1 = (Map<String,Map>)(returned.get("Note"));
						ArrayList<String> main_lst  =new ArrayList<String>(got_dat1.keySet());

						for (int i=0;i<main_lst.size();i++){
							note.model.addElement( (new Topic_List_Handler(main_lst.get(i))));	
						}

						ArrayList<String> lis = (ArrayList<String>)main_lst;
						note.list.setSelectedValue(note.topic.getText().toString().trim(), true);
						return;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


				}
			}

		});

		note.create_new.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent btn) {
				if (btn.getActionCommand()=="Create new Thought"){
					note.model.clear();

					try {
						database_recall();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Map<String,Map> got_dat1 = (Map<String,Map>)(returned.get("Note"));
					ArrayList<String> main_lst  =new ArrayList<String>(got_dat1.keySet());

					for (int i=0;i<main_lst.size();i++){
						note.model.addElement( (new Topic_List_Handler(main_lst.get(i))));	
					}
					
					note.topic.setText("");
					note.topic.setEditable(true);
					note.text_ar.setText("");
					note.change.setEnabled(false);
					
				}
				
			}
			
		});
		note.save.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String topic = note.topic.getText();
				new Save_me(frame,note.text_ar,topic,"Note");
			}

		});

		note.back.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				option_frame();

			}

		});
	}


	public void about_frame(){
		String txt = new All_Text().About_main_text;
		About_layout al = new About_layout(txt);
		stuf_panel.removeAll();
		stuf_panel.add(al.get_panel());
		stuf_panel.revalidate();
		stuf_panel.repaint();

		al.about_back.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				option_frame();

			}

		});
	}

	public void setting_frame(){
		stuf_panel.removeAll();
		Setting_layout s_l  = new Setting_layout();
		stuf_panel.add(s_l.get_panel());
		stuf_panel.revalidate();
		stuf_panel.repaint();
		s_l.back.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				option_frame();
			}

		});
	}
	private Boolean check_leap(Integer year){
		Boolean bool = false;
		for (int ori = 2017;ori<year;ori=ori+1){
			if ((ori%100 == 0)&&(ori%400==0)){    //Leap
				bool = true;
			}
			else if (ori%4 == 0){                // leap
				bool = true;
			}
			else{ 									// No Leap
				bool = false;
			}

		}
		return bool;
	}
	private void set_list(MyDiary2.diary obj) throws Exception 
	{
		database_recall();
		Map<String,Map> got_dat = returned.get("Main");

		ArrayList<String> sau =  new ArrayList<String>(got_dat.keySet());

		Map<Integer,String> all_sort = new HashMap<Integer,String>();

		Integer total;

		for (int j = 0;j<sau.size();j++){

			total= 0;

			String yr = sau.get(j).substring(11,15);
			Integer yer = Integer.parseInt(yr);

			String mnt = sau.get(j).substring(4,7);
			switch(mnt){
			case "Jan":
				total = total+31;
				break;
			case "Feb":
				total = total+59;
				if (check_leap(yer) == true){
					total = total+1;
				}
				break;
			case "Mar":
				total = total+90;
				if (check_leap(yer) == true){
					total = total+1;
				}
				break;
			case "Apr":
				total = total+120;
				if (check_leap(yer) == true){
					total = total+1;
				}
				break;
			case "May":
				total = total+151;
				if (check_leap(yer) == true){
					total = total+1;
				}
				break;
			case "Jun":
				total = total+181;
				if (check_leap(yer) == true){
					total = total+1;
				}
				break;
			case "Jul":
				total = total+212;
				if (check_leap(yer) == true){
					total = total+1;
				}
				break;
			case "Aug":
				total = total+243;
				if (check_leap(yer) == true){
					total = total+1;
				}
				break;
			case "Sep":
				total = total+273;
				if (check_leap(yer) == true){
					total = total+1;
				}
				break;
			case "Oct":
				total = total+304;
				if (check_leap(yer) == true){
					total = total+1;
				}
				break;
			case "Nov":
				total = total+334;
				if (check_leap(yer) == true){
					total = total+1;
				}
				break;
			case "Dec":
				total = total+365;
				if (check_leap(yer) == true){
					total = total+1;
				}
				break;


			}
			total = total + Integer.parseInt(String.valueOf(sau.get(j).substring(8,10)));
			all_sort.put(total, sau.get(j));
		}

		ArrayList<Integer> all_num = new ArrayList<Integer>(all_sort.keySet());

		int[] a = list_array(all_num);

		Arrays.sort(a);

		ArrayList<Integer> con = array_list(a);


		for (int i =0;i<con.size();i++){
			obj.model.addElement(new List_Handling(String.valueOf(all_sort.get(con.get(i)))));
		}
		if (got_dat.keySet().contains(stan_date(new Date().toString()).trim())){

			obj.list.setSelectedIndex(got_dat.keySet().size()-1);
			String val1 = obj.list.getSelectedValue().toString().trim();
			RawText_ShowText(got_dat.get(val1),obj.text_ar);


		}
		else{
			obj.model.addElement(new List_Handling(stan_date(new Date().toString())));
			obj.list.setSelectedIndex(sau.size());

		}
		obj.list.requestFocus();
	}
	private ArrayList<Integer> array_list(int[] a) {
		ArrayList<Integer> n = new ArrayList<Integer>();
		for (Integer m:a){
			n.add(m);
		}
		return n;
	}

	private int[] list_array(ArrayList<Integer> all_num) {
		int[] a = new int[all_num.size()];

		for (int i=0;i<all_num.size();i++){
			a[i] = all_num.get(i);
		}

		return a;
	}



	private void RawText_ShowText(Map<String, Map> send_mat,JTextPane text_ar)
	{
		Map<String,String> font_text = (Map<String,String>)send_mat.get("Main Text");
		String Text = font_text.get("Main Text");
		Map<String,String> font_size = (Map<String,String>)send_mat.get("Font_Size");
		Map<String,Color> font_color = (Map<String,Color>)send_mat.get("Font_color");
		Map<String,String> font_style = (Map<String,String>)send_mat.get("Font_style");
		StyledDocument sd = (StyledDocument)text_ar.getDocument();
		text_ar.setText("");

		// Speed Increase : 
		Thread thread = new Thread(new Thread_Text(sd,Text,font_size,font_color,font_style));
		thread.start();
		//


	}

	public void diary_back_coding(MyDiary2.diary obj) throws Exception
	{
		StyledDocument doc = (StyledDocument)obj.text_ar.getDocument();

		try {
			set_list(obj);
		} catch (Exception e2) {
		}
		// MAIN TEXT PANE LISTENER >>>> ALL VIEW TEXT>>> BEFORE EDIT BE CARE FUL>>>
		obj.text_ar.addKeyListener(new KeyListener()
		{

			@Override
			public void keyPressed(KeyEvent key) 
			{

				if(key.getKeyCode() == KeyEvent.VK_F1)
				{
					StyledDocument doc = (StyledDocument) obj.text_ar.getDocument();

					try
					{
						String txt = doc.getText(0, doc.getLength());
						if (txt.equals(""))
						{

							SimpleAttributeSet nor = new SimpleAttributeSet();
							StyleConstants.setFontFamily(nor, obj.style_ch.getSelectedItem().toString());
							StyleConstants.setFontSize(nor, Integer.valueOf((String) obj.font_len.getSelectedItem()));


							try 
							{
								bg_clr x = new  bg_clr(obj.color_ch.getSelectedItem().toString());
								Color clr = x.get_clr();
								StyleConstants.setForeground(nor, clr);
							} catch (Exception e) 
							{
							}
							txt = "\n"+rep_date()+"\n";
							doc.insertString(obj.text_ar.getCaretPosition(), rep_date(), nor);


						}
						else{
							try {

								SimpleAttributeSet nor = new SimpleAttributeSet();
								StyleConstants.setFontFamily(nor, obj.style_ch.getSelectedItem().toString());
								StyleConstants.setFontSize(nor, Integer.valueOf((String) obj.font_len.getSelectedItem()));

								StyleConstants.setForeground(nor, new bg_clr(obj.color_ch.getSelectedItem().toString()).get_clr());
								txt = "\n"+rep_date()+"\n";
								doc.insertString(obj.text_ar.getCaretPosition(), txt, nor);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}

				}
				else{
				}

			}

			@Override
			public void keyReleased(KeyEvent key) {


				StyledDocument sd = (StyledDocument) obj.text_ar.getDocument();

				SimpleAttributeSet norma = new SimpleAttributeSet();


				StyleConstants.setFontFamily(norma,String.valueOf(obj.style_ch.getSelectedItem()));
				StyleConstants.setForeground(norma, new bg_clr(String.valueOf(obj.color_ch.getSelectedItem().toString())).get_clr());
				StyleConstants.setFontSize(norma, Integer.valueOf(String.valueOf(obj.font_len.getSelectedItem())));
				sd.setCharacterAttributes(doc.getLength()-1, doc.getLength(), norma, true);
				obj.text_ar.requestFocus();

			}

			@Override
			public void keyTyped(KeyEvent key) {

			}

		});

		// SIDE LIST >>> ALL VIEW TAKEN >>> 
		// LIST CLICK AND SHWING MATTERIAL :

		obj.nclose.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {

				new Save_me(frame,obj.text_ar,stan_date(new Date().toString()),"Main",false);

				try {
					database_recall();
				} catch (Exception e) {
				}


			}

		});

		database_recall();
		Map<String,Map> got_dat = returned.get("Main");

		obj.list.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {



				Map<String,Map> got_dat = returned.get("Main");
				List_Handling val = obj.list.getSelectedValue();

				if (!(obj.list.getSelectedValue().toString().trim().equals(stan_date(new Date().toString()))))
				{
					RawText_ShowText(got_dat.get(val.toString().trim()),obj.text_ar);

					obj.text_ar.setEditable(false);
					obj.text_ar.setBackground(new bg_clr().unchange_text_clr);
					obj.save.setEnabled(false);

				}
				else
				{
					if (got_dat.containsKey(stan_date(new Date().toString()).trim())){

						RawText_ShowText(got_dat.get(val.toString().trim()),obj.text_ar);

					}
					else{
						obj.text_ar.setText("");
					}

					obj.text_ar.setEditable(true);
					obj.text_ar.setBackground(new bg_clr().change_text_clr);
					obj.save.setEnabled(true);
				}

			}

		});

	}

	private String rep_date(){

		int hrs = Integer.parseInt((String.valueOf(new Date().getHours())));
		int min = Integer.parseInt((String.valueOf(new Date().getMinutes())));
		int sec = Integer.parseInt((String.valueOf(new Date().getSeconds())));
		String noon = "A.M";

		if (hrs>12){
			noon = "P.M";
			hrs = hrs - 12;		
		}

		String f_min = ((String.valueOf(min).length()==1)?"0"+min:String.valueOf(min));
		String f_sec = ((String.valueOf(sec).length()==1)?"0"+sec:String.valueOf(sec));
		String main_date = hrs+":"+f_min+":"+f_sec+" "+noon+" ";

		return main_date;
	}

	public void option_btn(JButton btn)
	{
		btn.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent event) 
			{
				if (event.getActionCommand() == "My Diary")
				{
					try {
						database_recall();
						Map<String,String> got_dat = (Map<String,String>)(returned.get("Font"));
						String password = got_dat.get("Pasword");

						if (password.equals("")){
							diary_frame();
						}
						else{
							JPanel pass_panel = new JPanel();
							JLabel pass_main = new JLabel("Diary is Protected ");
							JLabel pass_ask = new JLabel("Enter password : ");
							JPasswordField pass_field = new JPasswordField(15);
							pass_panel.setLayout(new MigLayout());
							pass_panel.add(pass_main,"wrap");
							pass_panel.add(pass_ask);
							pass_panel.add(pass_field,"wrap");

							String pas = String.valueOf(JOptionPane.showConfirmDialog(null,pass_panel, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE));
							if (pas.equals("0")){
								if (String.valueOf(pass_field.getPassword()).equals(password)){
									diary_frame();
								}else{
									JOptionPane.showMessageDialog(frame, "Entered password is wrong \n\n If you have forgotten the password \nThen don't open it again or akant me bhat ke \ntani socho ki password ho kya sakta hai.....\nHi Hi Hi... ","Error", JOptionPane.ERROR_MESSAGE);
								}}


						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (event.getActionCommand() == "My Thoughts")
				{



					try {
						database_recall();
						Map<String,String> got_dat = (Map<String,String>)(returned.get("Font"));
						String password = got_dat.get("Pasword");

						if (password.equals("")){
							note_frame();
						}
						else{
							JPanel pass_panel = new JPanel();
							JLabel pass_main = new JLabel("'MyThoughts' is Protected ");
							JLabel pass_ask = new JLabel("Enter password : ");
							JPasswordField pass_field = new JPasswordField(15);
							pass_panel.setLayout(new MigLayout());
							pass_panel.add(pass_main,"wrap");
							pass_panel.add(pass_ask,"split");
							pass_panel.add(pass_field,"wrap");

							String pas = String.valueOf(JOptionPane.showConfirmDialog(null,pass_panel, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE));
							if (pas.equals("0")){
								if (String.valueOf(pass_field.getPassword()).equals(password)){
									note_frame();
								}else{
									JOptionPane.showMessageDialog(frame, "Entered password is wrong \n\n If you have forgotten the password \nThen don't open it again or akant me bhat ke \ntani socho ki password ho kya sakta hai.....\nHi Hi Hi... ","Error", JOptionPane.ERROR_MESSAGE);
								}}


						}
					} catch (Exception e) {
						e.printStackTrace();
					}



				}
				if (event.getActionCommand() == "F.A.Q")
				{
					Question faq = new Question();
					stuf_panel.removeAll();
					stuf_panel.add(faq.get_panel());
					stuf_panel.revalidate();
					stuf_panel.repaint();

					faq.back.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent arg0) {
							option_frame();

						}

					});



				}

				if (event.getActionCommand() == "Help")
				{
					about_frame();

				}
				if (event.getActionCommand() == "Setting")
				{
					setting_frame();


				}
				if (event.getActionCommand() == "Exit")
				{
					System.exit(0);


				}

			}

		});
	}
	public void diary(JButton btn,MyDiary2.diary obj)
	{
		btn.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent event) 
			{
				if (event.getActionCommand() == "Save (Save and then close)")
				{
					new Save_me(frame,obj.text_ar,stan_date(new Date().toString()),"Main");

				}


				if (event.getActionCommand() == "Back")
				{
					option_frame();

				}

			}
		});
	}

	private void check_dataBase() throws Exception
	{
		File fil = new File("data.txt");
		if(!fil.exists())
		{
			String font_sty = "Comic Sans MS";
			String font_siz = "20";
			String font_clr = "BLUE";

			Map<String,String> ch_sty= new HashMap<String,String>();

			ch_sty.put("Style", font_sty);
			ch_sty.put("Size",font_siz);
			ch_sty.put("Color", font_clr);
			ch_sty.put("Pasword", "");
			ch_sty.put("Default Topic", "Default Saved");

			Map<String,Map> all_font = new HashMap<String,Map>();
			all_font.put("Font", ch_sty);
			Map<String,Map> mat = new HashMap<String,Map>();
			all_font.put("Main", mat);
			Map<String,Map> note = new HashMap<String,Map>();
			
			Map<String,Map> inner = new HashMap<String,Map>();
			Map<String,String> text = new HashMap<String,String>();
			Map<String,String> sty = new HashMap<String,String>();
			Map<String,Integer> siz = new HashMap<String,Integer>();
			Map<String,Color> clr = new HashMap<String,Color>();
			text.put("Main Text", "");
			inner.put("Main Text", text);
			inner.put("Font_Size", siz);
			inner.put("Font_color", clr);
			inner.put("Font_style", sty);
			
			note.put("Default Saved", inner);
			all_font.put("Note", note);

			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.txt"));
			oos.writeObject(all_font);

			oos.flush();
			oos.close();
			database_recall();


		}
		else
		{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.txt"));

			Map<String,Map> returned = (Map<String,Map>)ois.readObject();
			Map<String,Map> got_dat = (Map<String,Map>)returned.get("Main_Matter");

			ois.close();

		}
	}

	public String stan_date(String dat){
		String data = dat.substring(0,10)+","+dat.substring(24,dat.length());
		return data;

	}
}
