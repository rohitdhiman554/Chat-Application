package javaNetwork;
import java.net.*;
import java.io.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import javax.swing.*;
import java.awt.*;
public class Server extends JFrame
{
    //initialize socket and input stream
    private Socket          socket   = null;
    private ServerSocket    server   = null;
BufferedReader br;
PrintWriter out;
JLabel heading=new JLabel("Server Area");
JTextArea messageArea=new JTextArea();
JTextField messageInput=new JTextField();
Font font=new Font("Roboto",Font.PLAIN,20);
    // constructor with port
    public Server(int port)
    {
        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");
  
            System.out.println("Waiting for a client ...");
  
            socket = server.accept();
            System.out.println("Client accepted");
  br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
  out=new PrintWriter(socket.getOutputStream());
  create();
addEvent();
  startReading();
  startWriting();
           
  
            // reads message from client until "Over" is sent
          
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
        
    }
    public void create()
    {
    	this.setTitle("Server Area");
    	this.setSize(500,500);
    	this.setLocationRelativeTo(null);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	heading.setHorizontalAlignment(SwingConstants.CENTER);
    	heading.setFont(font);
    	messageArea.setFont(font);
    	messageInput.setFont(font);
        messageArea.setEditable(false);
    	this.setLayout(new BorderLayout());
    	this.add(heading,BorderLayout.NORTH);
    	JScrollPane js=new JScrollPane(messageArea);
    	this.add(js,BorderLayout.CENTER);
    	this.add(messageInput,BorderLayout.SOUTH); 
    	this.setVisible(true);
    	
    }
    public void addEvent()
    {
    	messageInput.addKeyListener(new KeyListener()
    {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode()==10)
			{
				String  contentSend=messageInput.getText();
				
				messageArea.append("Me:"+contentSend+"\n");
				out.println(contentSend);
				
				out.flush();
				messageInput.setText("");
				messageInput.requestFocus();
				
			}
			
		}
    		
    });
    }
 
public void startReading()
  {
	  
	  Runnable r1=()->
	  {
		 System.out.println("reader Started");
		 while(true)
		 {
			 try {
				String msg=br.readLine();
				if(msg.equals("exit"))
				{
					JOptionPane.showMessageDialog(this, "Client Terminated");
					messageInput.setEnabled(false);
					socket.close();
					break;
				}

				messageArea.append("Client:"+msg+"\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		 }
		  
         
	  };
	 new Thread(r1).start();
  }
  public void startWriting()
  {
	  System.out.println("Writer Started...");
	  Runnable r2=()->
	  {
		  while(true)
		  {
			  try
			  {
				  BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
				  String content=br.readLine();
				  out.println(content);
				  out.flush();
			  }catch(Exception e)
			  {
				  e.printStackTrace();
			  }
			  
		  }
	  };
		 new Thread(r2).start();

  }
    public static void main(String args[])
    {
        Server server = new Server(5004);
    }
}









