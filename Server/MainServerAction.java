 	/**
 *
 * @author Amihi Atias && Miki Tubul
 * 
 * EX 5 Chat
 * 
 */	
package Server;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.LayoutStyle.ComponentPlacement;

/**server start.**/
public class MainServerAction extends javax.swing.JFrame { 
	//** class variables.**//
    static Thread listener;
    private javax.swing.JButton Start;
    private javax.swing.JButton Stop;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTextArea textArea;

   /**constructor.**/
    public MainServerAction() {
        startEverything();  
    }  
     /**initialize constructor.**/
    private void startEverything() {
        Start = new javax.swing.JButton();
        Stop = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        /**closing everything in case "x" button was clicked**/
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
     		addWindowListener(new WindowAdapter() {
     			public void windowClosing(WindowEvent e){
     				listener.stop();
     				System.exit(EXIT_ON_CLOSE);
     			}
     		});
/**Starts server thread*/
        Start.setText("Start"); 
        Start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
            	listener.start();  
		        System.out.println("Server is waiting for connections");
		        textArea.append("Server is waiting for connections\n");
		        Start.setEnabled(false);
            }
        });
      /** stops the server and not allowing connections anymore.**/
        Stop.setText("Stop");
        Stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
            	textArea.append("Shutting down Server\n");
            	listener.stop(); /**Stop the server thread**/
            }
        });

        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane1.setViewportView(textArea);
        textArea.setEditable(false);
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE))
        				.addGroup(layout.createSequentialGroup()
        					.addGap(14)
        					.addComponent(Start, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(Stop, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(23)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(Start)
        				.addComponent(Stop))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
        			.addContainerGap())
        );
        getContentPane().setLayout(layout);
        pack();
    }
    
    //** main **//
    public static void main(String args[]) throws IOException {
        Server server = new Server(45000);
        listener = new Thread(server);
        
        /** run method, creates and sets visible**/
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainServerAction().setVisible(true);
            }
        });
    }
}
