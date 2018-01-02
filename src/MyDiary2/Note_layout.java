package MyDiary2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;



public class Note_layout 
{
	DefaultListModel<Topic_List_Handler> model =new DefaultListModel();
	JList<Topic_List_Handler> list = new JList<>();
	
	String style ;
	String set_font;
	String set_color;
	
	JPanel left = new JPanel();
	JPanel right = new JPanel();
	JPanel mid = new JPanel();
	JPanel diary  = new JPanel();
	JPanel text_design = new JPanel();


	JLabel font = new JLabel("                              Font : ");
	JLabel Style = new JLabel("               Style : ");
	JLabel text_clr = new JLabel("                     Text Color : ");
	JLabel space = new JLabel("                                     ");

	JComboBox<String> font_len = new JComboBox();
	JComboBox<String> style_ch = new JComboBox();
	JComboBox<String> color_ch  =new JComboBox();

	
	JScrollPane scroll_tabel;

	JButton save = new JButton("Save (Save and then close)");
	JButton nclose = new JButton("Save (Without Close)" );
	JButton back = new JButton("Back");
	JButton create_new = new JButton("Create new Thought");
	
	JButton change = new JButton("Re-Name");
	JLabel top = new JLabel("     Topic  : ");
	JTextField topic;
	JTextPane text_ar = new JTextPane();
	JScrollPane scroll = new JScrollPane(text_ar,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	/* scroll ------------- Text_Ar
	 * scroll_table ------- List
	 */
	
	public Note_layout(String c_font,String style,String color )
	{     
		this.set_font = c_font;
		this.style = style;
		this.set_color = color;
		
		
		
		list.setModel(model);    
		model.removeAllElements();
		list.setBackground(new bg_clr().list_clr);
		
		scroll_tabel=new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
		scroll_tabel.setPreferredSize(new Dimension(150,450));

		
		scroll.setPreferredSize(new Dimension(680,420));
		scroll_tabel.setBackground(new bg_clr().list_clr);
		text_ar.setBackground(new bg_clr().change_text_clr);
		text_ar.setBorder(new EmptyBorder(40,100,1,90));
		topic = new JTextField(30);
		topic.setForeground(Color.red);
		topic.setBackground(new bg_clr().unchange_text_clr);
		topic.setFont(new Font("Century Gothic",Font.BOLD,14));
		top.setFont(new Font("consolas",Font.BOLD,19));
		
		scroll_tabel.setForeground(Color.RED);

		left.setBackground(new bg_clr().body_clr);
		left.add(scroll_tabel);
		change.setEnabled(false);
		
		right.setLayout(new MigLayout());
		right.add(top, "split");
		right.add(topic, "split");
		right.add(change, "wrap");
		right.add(scroll);
		right.setBackground(new bg_clr().body_clr);
		mid.add(left);
		mid.add(right);
		mid.setBackground(new bg_clr().body_clr);

		// ADDING TEXT STYLE AND FONT IN COMBOBOXES... : 
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] fonts = e.getAllFonts(); // Get the fonts
		for (Font f : fonts) {

			style_ch.addItem(f.getFontName());
		}
		// ADDING SIZE OF FONT : 
		for (int i=4;i<80;i=i+4){
			font_len.addItem(String.valueOf(i));
		}

		String[] colorpool ={"black","blue","red","magenta","green","orange","cyan","white","yellow","pink"};
		
		// ADDING COLOR TO FONT HERE : 
		for (String clr:colorpool){
			color_ch.addItem(clr.toUpperCase());
		}
		style_ch.setSelectedItem(style);
		font_len.setSelectedItem(set_font);
		color_ch.setSelectedItem(set_color);
		text_design.setLayout(new MigLayout());
		text_design.add(font);
		text_design.add(font_len);
		text_design.add(Style);
		text_design.add(style_ch);
		text_design.add(text_clr);
		text_design.add(color_ch);
		text_design.add(space);
		text_design.setBackground(Color.PINK);
		text_ar.setFont(new Font(style,Font.PLAIN,Integer.parseInt(set_font)));
		text_ar.setForeground(new bg_clr(set_color).get_clr());

		diary.setLayout(new MigLayout());
		diary.setBackground(new bg_clr().body_clr);
		diary.add(text_design,"wrap");
		diary.add(mid,"wrap");
		
		
		diary.add(save,"split");
		diary.add(new JLabel("      "),"split");
		diary.add(nclose, "split");
		diary.add(new JLabel("      "),"split");
		diary.add(back,"split");
		diary.add(new JLabel("                                                                          "),"split");
		diary.add(create_new);
		diary.setBackground(new bg_clr().body_clr);





	

		


	}
	public JPanel get_scroll()
	{
		return diary;

	}

}