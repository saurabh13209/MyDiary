package MyDiary2;

import java.awt.CheckboxMenuItem;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.text.Format.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.StyledDocument;

import org.w3c.dom.Document;

public class system_tray{
	Map<String,Map> returned;
	Map<String,Map> returne;
	
	public void database_recall() throws Exception{
		ObjectInputStream object_inp =  new ObjectInputStream(new FileInputStream("data.txt"));
		returne = (Map<String,Map>)(object_inp.readObject());
		returned = (Map<String,Map>)(returne.get("Note"));
		object_inp.close();
	}

	static TrayIcon TrayIcon;
	JFrame frame;
	
	public system_tray(JFrame frame, JPanel main_panel){
		show(frame,main_panel);
	}

	public void show(JFrame frame2,JPanel main_panel){
		if (!SystemTray.isSupported()){
			System.exit(0);
		}
		final PopupMenu popup = new PopupMenu();
		
		TrayIcon = new TrayIcon(creatIcon("/MyDiary2/ic.png","icon"));
		final SystemTray tray= SystemTray.getSystemTray();
		
		MenuItem show_me = new MenuItem("Show Me");
		MenuItem set = new MenuItem("Change Default");
		MenuItem default_set = new MenuItem("Default Saved");
		default_set.setFont(new Font("Consolas",Font.BOLD,20));
		MenuItem post = new MenuItem("For This Post");
		MenuItem Exit = new MenuItem("Exit");
		popup.add(show_me);
		popup.addSeparator();
		popup.add(set);
		popup.addSeparator();
		popup.add(default_set);
		popup.addSeparator();
		popup.add(post);
		popup.addSeparator();
		popup.add(Exit);

		try {
			database_recall();
			Map<String,String> font_me = (Map<String,String>)returne.get("Font");
			String d_topic = font_me.get("Default Topic");
			System.out.println(d_topic);
			default_set.setLabel(d_topic);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		Exit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
				
			}
			
		});
		show_me.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame2.setVisible(true);
				
			}
			
		});
		set.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mini_setting new_panel = new mini_setting();
				JPanel panel = new_panel.get_panel();
				JFrame mini_frame = new JFrame();
				mini_frame.add(panel);
				mini_frame.setVisible(true);
				mini_frame.setSize(500,350);
				mini_frame.setResizable(false);	
				
				try {
					database_recall();
					
					for (String item:returned.keySet()){
						new_panel.model.addElement(new set_handling(item));
					}
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				new_panel.next.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						try {
							database_recall();

							Map<String,String> font_me = (Map<String,String>)returne.get("Font");
							String d_topic = font_me.get("Default Topic");
							font_me.remove("Default Topic");
							font_me.put("Default Topic", new_panel.list.getSelectedValue().toString().trim());
							default_set.setLabel( new_panel.list.getSelectedValue().toString().trim());
							returne.remove("Font");
							returne.put("Font", font_me);
							
							ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.txt"));
							oos.writeObject(returne);
							oos.flush();
							oos.close();
							
							mini_frame.dispose();
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
				});
				
				new_panel.back.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						mini_frame.dispose();
						
					}
					
				});
			}
			
		});
		post.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				mini_panel new_panel = new mini_panel();
				JPanel panel = new_panel.panel();
				JFrame mini_frame = new JFrame();
				mini_frame.add(panel);
				mini_frame.setVisible(true);
				mini_frame.setSize(500,350);
				mini_frame.setResizable(false);
				
				try {
					database_recall();
					
					for (String item:returned.keySet()){
						new_panel.model.addElement(new mini_handling(item));
					}
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				new_panel.new_field.addMouseListener(new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent arg0) {
						new_panel.list.setEnabled(false);
						new_panel.new_field.setEnabled(true);
						
						
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
				});
				
				new_panel.list.addMouseListener(new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent e) {
						new_panel.list.setEnabled(true);
						new_panel.new_field.setEnabled(false);
						
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
				});
				
				new_panel.back.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						mini_frame.dispose();
						
					}
					
				});
				
				new_panel.next.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						String got;
						
						if (new_panel.new_field.isEnabled()){
							got= new_panel.new_field.getText().toString().trim();
							main_save(frame2,main_panel,got,true);
						}
						else{
							got= new_panel.list.getSelectedValue().toString().trim();
							main_save(frame2,main_panel,got,false);
						}
						
						System.out.println(got);
						mini_frame.dispose();
					}
					
				});
				
			}
			
		});
		
		TrayIcon.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent msc) {
				if (msc.getButton() == MouseEvent.BUTTON1){	
					try {
						database_recall();
						Map<String,String> font_me = (Map<String,String>)returne.get("Font");
						String d_topic = font_me.get("Default Topic");
						
						if (returned.keySet().contains(d_topic)){

							main_save(frame2,main_panel,d_topic,false);
						}else{

							main_save(frame2,main_panel,d_topic,true);
						}
						
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					
					
				}
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		
		TrayIcon.setPopupMenu(popup);
		try{
			tray.add(TrayIcon);
		}catch(Exception e){
			
		}
		
		
		
	}
	
	private void main_save(JFrame frame2, JPanel main_panel, String topic,boolean is_new){
		try {
			database_recall();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!is_new){
		Map<String,Map> inner = (Map<String,Map>)returned.get(topic);
		Map<String,String>text = (Map<String,String>)inner.get("Main Text");
		String main_text = (String)text.get("Main Text");
		int total_num=0;
		for (int i=0;i<main_text.length();i++){
			total_num++;
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////////
		String main_he = main_text;
		try{
			main_he = main_text+"\n"+(new Date().toString())+"\n"+String.valueOf(Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor))+"\n"+"---------------------------------------------";
		}catch(Exception ed){}
		
		
		text.remove("Main Text");
		text.put("Main Text", main_he);
		
		Map<String,Integer> font_size = inner.get("Font_Size");
		Map<String,String> font_sty = inner.get("Font_style");
		Map<String,Color> font_clr = inner.get("Font_color");
		
		
		// font and all:
		Map<String,String> font_get = (Map<String, String>) returne.get("Font");
		String clr= font_get.get("Color");
		String sty = font_get.get("Style");
		String siz = font_get.get("Size");
		
		//
		
		while (total_num<main_he.length()){
			font_size.put(String.valueOf(total_num), Integer.valueOf(siz));
			font_sty.put(String.valueOf(total_num), sty);
			font_clr.put(String.valueOf(total_num), new bg_clr(clr).get_clr());
			total_num++;
			
		}
		inner.remove("Font_Size");
		inner.remove("Font_color");
		inner.remove("Font_style");
		returned.remove(topic);
		
		inner.put("Font_Size", font_size);
		inner.put("Font_style", font_sty);
		inner.put("Font_color", font_clr);
		returned.put(topic, inner);
	
		}
		else{
			
			try {
				Map<String,String> font_get = (Map<String, String>) returne.get("Font");
				String clr= font_get.get("Color");
				String sty = font_get.get("Style");
				String siz = font_get.get("Size");
				
				Map<String,String> font_main = new HashMap<String,String>();
				Map<String,Integer> font_size = new HashMap<String,Integer>();
				Map<String,String> font_sty = new HashMap<String,String>();
				Map<String,Color> font_clr = new HashMap<String,Color>();
				
				String text = String.valueOf(Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor));
				
				font_main.put("Main Text", text);
				
				for (int i=0;i<text.length();i++){
					font_size.put(String.valueOf(i), Integer.valueOf(siz));
					font_sty.put(String.valueOf(i), sty);
					font_clr.put(String.valueOf(i), new bg_clr(clr).get_clr());
					
				}
				
				Map<String,Map> outter =  new HashMap<String,Map>();
				outter.put("Main Text", font_main);
				outter.put("Font_Size", font_size);
				outter.put("Font_color", font_clr);
				outter.put("Font_style", font_sty);
				
				returned.put(topic, outter);
				
				
				
			} catch (Exception e) {
				
			}
			
			
		}
		
		try {
			ObjectInputStream oip = new ObjectInputStream(new FileInputStream("data.txt"));
			Map<String,Map> ret = (Map<String,Map>)(oip.readObject());
			ret.remove("Note");
			ret.put("Note",returned);
			System.out.println(ret);
			oip.close();
			
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.txt"));
			oos.writeObject(ret);
			oos.flush();
			oos.close();
			JOptionPane.showOptionDialog(frame2, "Your data has been saved .\nIf you want to see it click RIGHT click over it and then press 'show me'", "Saved", 2	, 1,null, null, 1);
			
			
		} catch (Exception e) {
		}
	}
	protected static Image creatIcon(String path , String desc){
		URL imageURL = system_tray.class.getResource(path);
		return  (new ImageIcon(imageURL,desc).getImage());
		
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
	
	
}
