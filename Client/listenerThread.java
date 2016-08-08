/**
 *
 * @author Amihi Atias && Miki Tubul
 * 
 * EX 5 Chat
 * 
 */
package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Arrays;
import java.util.StringTokenizer;
import javax.swing.JComboBox;

//*a thread that waits to get new incoming msgs.*//
public class listenerThread implements Runnable {
	//*Variables*//
    private final Socket socket;
    String[] arr;
    int count;
    String name;

   //*Constructors*//
    public listenerThread(Socket socket, String userName) {
        this.socket = socket;
        this.name = userName;
        count = 0;
        arr = new String[100];
    }

    @Override
    public void run() {
        try {
        	//*socket listener*//
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
            String inP;
            ///thread is listening to incoming msgs.*//
            while ((inP = in.readLine()) != null) {
                if (inP.contains("<list>")) { //* updating online user list.*//
                    count = 0;
                    arr = new String[50];
                    StringTokenizer st = new StringTokenizer(inP.substring(6), ",[] ");
                    while (st.hasMoreTokens()) {
                        arr[count++] = st.nextToken();
                    }//* setting inside the boxlist.*//
                    arr = Arrays.copyOfRange(arr, 0, count);
                    ClientsAction.peoplelist.setListData(arr); 
                    String[] PM = new String[51];
                    PM[0] = "All";
                    int countOther = 1;
                    for (int i = 0; i < count; i++) {
                        if (!arr[i].equals(name)) {
                            PM[countOther++] = arr[i];
                        }
                    }
                    PM = Arrays.copyOfRange(PM, 0, countOther);
                    ClientsAction.list.setModel(new JComboBox(PM).getModel()); 
                    continue;
                }
                //*if it wasnt a new list to update then its a message and its going to be appended on clients textArea*//
                ClientsAction.textArea.append(inP + "\n");
            }
        } catch (IOException ex) {
            ClientsAction.textArea.append("Connection closed " + ex.getMessage() + "\n");
        }
    }

}
