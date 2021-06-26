package javaNetwork;

import java.net.*;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import javax.swing.*;
import java.awt.*;
public class Client extends JFrame
{
    // initialize socket and input output streams
    private Socket socket = null;
    BufferedReader br;
    PrintWriter out;

    JLabel heading=new JLabel("Client Area");
    JTextArea messageArea=new JTextArea();
   
   
    JTextField messageInput=new JTextField();
    Font font=new Font("Roboto",Font.PLAIN,20);
    
   

  
    // constructor to put ip address and port
    public Client(String address, int port)
    {
        // establish a connection
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");
  
            // takes input from terminal
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());
            createGui();
            addEvent();
            startReading();
         
               
        }catch(Exception e)
        {
        	e.printStackTrace();
        }
        
    }
    public void createGui()
    {
    	
    	this.setTitle("Client Messenger");
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
						if(e.getKeyCode()==10)
						{
							String contentSend=messageInput.getText();
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
  					JOptionPane.showMessageDialog(this,"Server Terminated");
  					messageInput.setEnabled(false);
  					
  					socket.close();
  					break;
  				}
  			
  				messageArea.append("Server:"+msg+"\n");
  				
  				
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
        Client client = new Client("127.0.0.1", 5004);
    }
}