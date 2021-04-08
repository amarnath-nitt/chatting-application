package VideoChattingApp;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;

import javax.swing.border.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.net.*;
import java.io.*;

public class Client implements ActionListener {
    
	JPanel p1;
	JTextField t1;
	JButton b1;
	static JPanel a1;
	static JFrame f1 = new JFrame();
	
	static Box vertical = Box.createVerticalBox();
	
	//Socket;
	static Socket skt ;
	static DataInputStream din;
	static DataOutputStream dout;
	
	
	boolean typing;
	
	public Client(){
		
		// Panel;
		f1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		p1 = new JPanel();
		p1.setLayout(null);
		p1.setBackground(new Color(7, 94,84));
		p1.setBounds(0,0, 450, 50);
		f1.add(p1);
			
		// Arrow Icon;
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("VideoChattingApp/Icons/3.png"));
		Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel l1= new JLabel(i3);
		l1.setBounds(5,10, 30,30);
		p1.add(l1);
		
		l1.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent obj) {
				System.exit(0);
			}
		});
		
		// DP Image;
		ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("VideoChattingApp/Icons/AMARNATH_DP.jpg"));
		Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		JLabel l2= new JLabel(i6);
		l2.setBounds(40,0, 50,50);
		p1.add(l2);
		
		// Name Label;
		JLabel l3 = new JLabel("AMARNATH");
		l3.setBounds(100, 0, 120, 30);
		l3.setFont(new Font("Tahoma", Font.BOLD, 19));
		l3.setForeground(Color.WHITE);
		p1.add(l3);
		
		// Status Label;
		JLabel l4 = new JLabel("Active Now");
		l4.setBounds(105, 30, 100, 20);
		l4.setFont(new Font("Tahoma", Font.PLAIN, 10));
		l4.setForeground(Color.WHITE);
		p1.add(l4);
		
		Timer t = new Timer(1, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!typing) {
					l4.setText("Active Now");
				}
			}
		});
		
		t.setInitialDelay(2000); // 2 msec delay;
		
		// Video Call Icon;
		ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("VideoChattingApp/Icons/video.png"));
		Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i9 = new ImageIcon(i8);
		JLabel l5= new JLabel(i9);
		l5.setBounds(320,10, 30,30);
		p1.add(l5);
		
		
		// Phone Call Icon;
		ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("VideoChattingApp/Icons/phone.png"));
		Image i11 = i10.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i12 = new ImageIcon(i11);
		JLabel l6= new JLabel(i12);
		l6.setBounds(370,10, 30,30);
		p1.add(l6);
		
		// Option Icon;
		ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("VideoChattingApp/Icons/3icon.png"));
		Image i14 = i13.getImage().getScaledInstance(13, 20, Image.SCALE_DEFAULT);
		ImageIcon i15 = new ImageIcon(i14);
		JLabel l7= new JLabel(i15);
		l7.setBounds(420,13, 13,20);
		p1.add(l7);
		
		// Text Area Field;
		a1 = new JPanel();
		a1.setBounds(2, 55, 445, 500);
		a1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		f1.add(a1);
		
		// Text Field;
		t1 = new JTextField();
		t1.setBounds(5, 560, 350, 40);
		t1.setFont(new Font("san_serif", Font.PLAIN, 16));
		f1.add(t1);
		
		t1.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				l4.setText("typing...");
				
				t.stop();
				
				typing =true;
			}
			@Override
			public void keyReleased(KeyEvent e) {
				typing = false;
				if(!t.isRunning()) {
					t.start();
				}
			}
			
		});
		
		// Send Button;
		b1 = new JButton("Send");
		b1.setBounds(357, 560, 90, 40);
		b1.setBackground(new Color(7,94,84));
		b1.setForeground(Color.WHITE);
		b1.addActionListener(this);
		f1.add(b1);
		
		
		//getContentPane().setBackground(Color.BLACK);
		f1.setBounds(800, 50, 450, 600);
		f1.setLayout(null);
		f1.setUndecorated(true);
		
		//It should be in last; 
		f1.setVisible(true);	
	}
	
	public void sendTextToFile(String message) throws IOException{
		try(FileWriter file1 = new FileWriter("Chat.txt");
				PrintWriter print1 = new PrintWriter(new BufferedWriter(file1));){
				print1.println("AMARNATH: "+ message);
		}
		catch(Exception e) {
			
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			  String text = t1.getText(); // To get text from text field;  // To add text in text area;
			  sendTextToFile(text);	
			  JPanel p2 = formatLabel(text);
			  a1.setLayout(new BorderLayout());
			  
			  JPanel right = new JPanel(new BorderLayout());
			  right.add(p2, BorderLayout.LINE_END);
			  vertical.add(right);
			  vertical.add(Box.createVerticalStrut(15));
			  
			  a1.add(vertical, BorderLayout.PAGE_START);
			  dout.writeUTF(text);
			  
			  t1.setText("");
		} 
		catch(IOException e1) {
			e1.printStackTrace();
		}   
	}
	
	public static JPanel formatLabel(String text) {
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
        
        JLabel l1 = new JLabel("<html><p style = \"width : 150px\">"+text+"</p></html>");
        l1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        l1.setBackground(new Color(37, 211, 102));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(15,15,15,50));
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));
        
        p3.add(l1);
        p3.add(l2);
        return p3;
	}
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		new Client().f1.setVisible(true);
		// To print input and output;
		
		try {
			
			skt = new Socket("127.0.0.1", 6001);
			din = new DataInputStream(skt.getInputStream());
			dout = new DataOutputStream(skt.getOutputStream());
			
			String msgInput="";
			while(true) {
                a1.setLayout(new BorderLayout());
	            msgInput = din.readUTF();
            	JPanel p2 = formatLabel(msgInput);
                JPanel left = new JPanel(new BorderLayout());
                left.add(p2, BorderLayout.LINE_START);
                
                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);
                f1.validate();
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

}
