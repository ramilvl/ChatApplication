package chatting.application;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.border.EmptyBorder;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements ActionListener{
    JTextField text;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame frame = new JFrame();
    static DataOutputStream dos;
    Server()
    {     
        frame.setLayout(null);
        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(7,94,84));
        panel1.setBounds(0,0,450,70);
        panel1.setLayout(null);
        frame.add(panel1);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/goBack.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2); 
        JLabel goBack = new JLabel(i3);
        goBack.setBounds(5,20,25,25);
        panel1.add(goBack);
        
        goBack.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae)
                {
                    System.exit(0);
                }
        });
        
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/administrator.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5); 
        JLabel profilePhoto = new JLabel(i6);
        profilePhoto.setBounds(40,10,50,50);
        panel1.add(profilePhoto);
        
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8); 
        JLabel video = new JLabel(i9);
        video.setBounds(300,20,30,30);
        panel1.add(video);
        
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11); 
        JLabel phone = new JLabel(i12);
        phone.setBounds(360,20,35,30);
        panel1.add(phone);
        
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/more.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14); 
        JLabel threePoint = new JLabel(i15);
        threePoint.setBounds(420,20,10,25);
        panel1.add(threePoint);
        
        JLabel name = new JLabel("Saleh");
        name.setBounds(110,15,100,18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        panel1.add(name);
        
        JLabel status = new JLabel("Active Now");
        status.setBounds(110,35,100,18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        panel1.add(status);
        
        a1=new JPanel();
        a1.setBounds(5,75,440,570);
        frame.add(a1);
        
        text = new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        frame.add(text);
        
        JButton send = new JButton("Send");
        send.setBounds(320,655,120,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        frame.add(send);
        
        frame.setSize(450,700);
        frame.setLocation(200,50);
        frame.setUndecorated(true);
       
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        try {
            String out= text.getText();
            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2,BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical,BorderLayout.PAGE_START);
            dos.writeUTF(out);
            text.setText("");

            frame.repaint();
            frame.invalidate();
            frame.validate();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public static JPanel formatLabel(String out)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JLabel output = new JLabel("<html><p style =\"width:150px\">"+out+"</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);
        
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel("12:00");
        time.setText(sdf.format(calendar.getTime()));
        
        panel.add(time);
        return panel;
    }
    
    public static void main(String[] args) {
        new Server();
        
        try{
            ServerSocket skt = new ServerSocket(6001);
            while(true)
            {
                Socket s= skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
                
                while(true)
                {
                    String message = din.readUTF();
                    JPanel panel = formatLabel(message);
                    
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    frame.validate();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
