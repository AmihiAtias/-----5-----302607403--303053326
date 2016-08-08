
/**
 *
 * @author Amihi Atias && Miki Tubul
 * 
 * EX 5 Chat
 * 
 */





package Client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ClientsAction extends javax.swing.JFrame {
		/**Variables.**/
	   private javax.swing.JButton btnClear;
	    private javax.swing.JLabel online;
	    private javax.swing.JButton btnConnect;
	    private javax.swing.JButton btnDisconnect;
	    private javax.swing.JLabel lblAdress;
	    private javax.swing.JTextField textIP;
	    private javax.swing.JLabel Name;
	    private javax.swing.JTextField textName;
	    public static javax.swing.JTextArea textArea;
	    private javax.swing.JScrollPane jScrollPane1;
	    private javax.swing.JScrollPane jScrollPane2;
	    public static javax.swing.JComboBox list;
	    public static javax.swing.JList peoplelist;
	    private javax.swing.JTextField msg;
	    private javax.swing.JButton btnSend;
	    
	    Socket socket = null; //Socket 
	    PrintWriter out = null;    //out put msgs
	    BufferedReader in = null;  //input msgs


	    /**Constructor.**/
    public ClientsAction() {
        startEverything(); 
    }

    @SuppressWarnings("unchecked")
    //**innitlize everything.**// 
    private void startEverything() {
    	
        btnConnect = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        peoplelist = new javax.swing.JList();
        btnDisconnect = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        lblAdress = new javax.swing.JLabel();
        Name = new javax.swing.JLabel();
        online = new javax.swing.JLabel();
        list = new javax.swing.JComboBox();
        btnSend = new javax.swing.JButton();
        msg = new javax.swing.JTextField();
        textIP = new javax.swing.JTextField();
        textName = new javax.swing.JTextField();
        btnClear = new javax.swing.JButton();
        
      /**closing everything in case "x" button was clicked**/
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				onDisconnect(null);
				System.exit(EXIT_ON_CLOSE);
			}
		});
        
       /**when clicked connect it calls onConnect method**/
        btnConnect.setText("Connect");
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
            	onConnect(e);
            }
        });
        /**list of people**/
        peoplelist.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "" };
            public int getSize() {
                return strings.length;
            }
            public Object getElementAt(int i) {
                return strings[i];
            }
        }
    );
    peoplelist.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
    jScrollPane1.setViewportView(peoplelist);
    
    /**calls the method "onDisconnnect" and starts closing everything.**/
    btnDisconnect.setText("Disconnect");
    btnDisconnect.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            onDisconnect(e);
        }
    });
    textArea.setColumns(20);
    textArea.setRows(5);
    jScrollPane2.setViewportView(textArea);
    lblAdress.setText("Adress:");
    Name.setText("Name:");
    online.setText("Online people:");

    list.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            listActionPerformed(e);
        }
    });
    /**sends an output msg**/
    btnSend.setText("Send");
    btnSend.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            sendMessage(e);
        }
    });
    //**sends a msg when a user presses enter.**//
    msg.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent e) {
        	  if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) { 
                  sendMessage(null);
              }
        }
    });

    textIP.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            IPTextActionPerformed(e);
        }
    });
    /**clears main screen of the clients*/
    btnClear.setText("Clear");
    btnClear.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
        	textArea.setText("");
        }
    });
/* GUI layout*/
    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    layout.setHorizontalGroup(
    	layout.createParallelGroup(Alignment.LEADING)
    		.addGroup(layout.createSequentialGroup()
    			.addContainerGap()
    			.addGroup(layout.createParallelGroup(Alignment.LEADING)
    				.addGroup(layout.createSequentialGroup()
    					.addComponent(list, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
    					.addPreferredGap(ComponentPlacement.RELATED)
    					.addComponent(msg, 420, 420, 420)
    					.addPreferredGap(ComponentPlacement.RELATED)
    					.addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
    				.addGroup(layout.createSequentialGroup()
    					.addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 483, GroupLayout.PREFERRED_SIZE)
    					.addGap(1)
    					.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
    					.addGap(0, 41, Short.MAX_VALUE))
    				.addGroup(layout.createSequentialGroup()
    					.addGap(39)
    					.addGroup(layout.createParallelGroup(Alignment.LEADING)
    						.addComponent(lblAdress, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
    						.addComponent(textIP, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
    						.addComponent(textName, 185, 185, 185)
    						.addComponent(Name, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
    					.addGroup(layout.createParallelGroup(Alignment.LEADING)
    						.addGroup(layout.createSequentialGroup()
    							.addPreferredGap(ComponentPlacement.RELATED, 144, Short.MAX_VALUE)
    							.addGroup(layout.createParallelGroup(Alignment.TRAILING)
    								.addComponent(btnConnect, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
    								.addComponent(btnDisconnect, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE))
    							.addGap(18)
    							.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE))
    						.addGroup(layout.createSequentialGroup()
    							.addGap(271)
    							.addComponent(online, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)))))
    			.addContainerGap())
    );
    layout.setVerticalGroup(
    	layout.createParallelGroup(Alignment.LEADING)
    		.addGroup(layout.createSequentialGroup()
    			.addGroup(layout.createParallelGroup(Alignment.LEADING)
    				.addGroup(layout.createSequentialGroup()
    					.addGap(15)
    					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
    						.addComponent(btnConnect)
    						.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)))
    				.addGroup(layout.createSequentialGroup()
    					.addGap(6)
    					.addComponent(Name)
    					.addPreferredGap(ComponentPlacement.UNRELATED)
    					.addComponent(textName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
    			.addGroup(layout.createParallelGroup(Alignment.LEADING)
    				.addGroup(layout.createSequentialGroup()
    					.addPreferredGap(ComponentPlacement.UNRELATED)
    					.addGroup(layout.createParallelGroup(Alignment.LEADING)
    						.addGroup(layout.createSequentialGroup()
    							.addComponent(lblAdress)
    							.addPreferredGap(ComponentPlacement.RELATED)
    							.addComponent(textIP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
    						.addComponent(btnDisconnect)))
    				.addGroup(layout.createSequentialGroup()
    					.addGap(36)
    					.addComponent(online, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)))
    			.addPreferredGap(ComponentPlacement.RELATED)
    			.addGroup(layout.createParallelGroup(Alignment.LEADING)
    				.addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
    				.addGroup(layout.createSequentialGroup()
    					.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 273, GroupLayout.PREFERRED_SIZE)
    					.addGap(0, 24, Short.MAX_VALUE)))
    			.addPreferredGap(ComponentPlacement.RELATED)
    			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
    				.addComponent(btnSend)
    				.addComponent(list, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
    				.addComponent(msg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
    			.addGap(20))
    );
    getContentPane().setLayout(layout);
    textArea.setEditable(false);
    btnDisconnect.setEnabled(false);
    setResizable(false);
    pack();
    }

    private void onDisconnect(java.awt.event.ActionEvent ee) {
        /**user disconnects the server.**/
        if (socket != null) {
            try {
                out.println("hamblushk!!"); /**informing the server that the user leaves.
                /**closing progresses.**/
                out.close();
                in.close();
                socket.close();
                socket = null;
                textArea.append("Disconnected\n");
                btnDisconnect.setEnabled(false);
                textIP.setEditable(true);
                textName.setEditable(true);
                btnConnect.setEnabled(true);
            } catch (IOException e) {
                Logger.getLogger(ClientsAction.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private void IPTextActionPerformed(java.awt.event.ActionEvent e) {
    }
    private void listActionPerformed(java.awt.event.ActionEvent e) {
    }
 /** when "connect" button is pressed this is what happens.**/
    private void onConnect(java.awt.event.ActionEvent e) {
        if (socket == null && !textIP.getText().equals("") && !textName.getText().equals("")) {
            try {
                socket = new Socket(textIP.getText(), 45000);   //*create socket (user nad client)*//
                out = new PrintWriter(socket.getOutputStream(), true);  //*opens streams*//
                in = new BufferedReader(new InputStreamReader( socket.getInputStream()));  
                out.println("%%%!" + textName.getText()); //*sending the name of the new user right on connection.*//
                
                textIP.setEditable(false);
                textName.setEditable(false);
                btnConnect.setEnabled(false);
                btnDisconnect.setEnabled(true);
                textArea.append("Connected!! \n");
               /**Open listenThread and start it.*/
                listenerThread listen = new listenerThread(socket, textName.getText());
                new Thread(listen).start(); 
            	}	catch (IOException ee) {
            			textArea.append("Couldn't get I/O for "
                        + "the connection to this host\n" + ee.getMessage() + "\n");
            }
        } else {
        	
            textArea.append("Please fill in the required info.\n");
        }
    }//* send output streams*//
    private void sendMessage(java.awt.event.ActionEvent e) {
        if (!msg.getText().equals("")) { //No msg.
            try {
                //*creating streams and sending msg.*//
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                if (list.getSelectedItem() == "All") { //*sending a message to everybody*//
                    out.println(msg.getText());
                } else { //*private msg.*//
                    out.println("<!" + list.getSelectedItem() + "!>" + msg.getText()); //adding the name in this format - <name>msg
                }
            } catch (IOException ex) {
                textArea.append("Something went wrong... might not be connected.\n");
            }
        }
        msg.setText(""); /**clear textmsg for incoming new messages.*/
    }
    public static void main(String args[]) {
       //starts frame.
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientsAction().setVisible(true);
            }
        });
    }
}
