/**
 *
 * @author Amihi Atias && Miki Tubul
 * 
 * EX 5 Chat
 * 
 */
package Server;

import java.io.*;
import java.net.*;
import java.util.Arrays;

//*Variables of Server*//
public class Server implements Runnable {
    int serverPort = 45000; //* The port that this server is listening on*//
    ServerSocket serverSocket = null;  //*Server socket that will listen for incoming connections*//
    boolean isStopped = false;
   public WorkerRunnable arr[] = new WorkerRunnable[50];
    public int count = 0;

    //*constructor that receives a port.*//
    public Server(int port) {
        this.serverPort = port;
    }

    public void run() {
     //* whats happening on the run method.*//
     //*   try to open a server Socket*//
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            MainServerAction.textArea.append("Something went wrong with the socket.\n" + e.getMessage() + "\n");
        }
        while (!isStopped()) {
            Socket clientSocket = null;  //8 socket created by accept*//

            try {
                clientSocket = this.serverSocket.accept(); //* wait for a client to connect*//

            } catch (IOException e) {
                if (isStopped()) {
                    MainServerAction.textArea.append("IOException: Server Stopped.\n");
                    return;
                }
                throw new RuntimeException(
                        "Error accepting client connection", e);    //Acception failed
            }
            /**Client was accepted**/
            MainServerAction.textArea.append("Server accepts the client's connection\n");
            /** Client information **/              
            InetAddress addr = clientSocket.getInetAddress();
            MainServerAction.textArea.append("Server: New Connection from: (" + addr.getHostAddress() + "): " + addr.getHostName() + " on port: " + clientSocket.getPort() + "\n");

            if (count < 50) { //** max amount -> 50 users.**//
            	arr[count] = new WorkerRunnable(this, clientSocket); //**creating a socket for every client and adding to an Array**//
                new Thread(arr[count++]).start(); //*Starting thread**//
            }else{ //**server is full.**//
            	MainServerAction.textArea.append("server is full, no more connections.");
            }
        }
        //**server stopping.**/
        MainServerAction.textArea.append("Server Stopped.\n");
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    /*stopping server.*/
    public synchronized void stop() {
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error shutting down server", e);
        }
    }
   
    //*updates the online list of users.*//
    public void updateList() throws IOException {
    	   String ans[] = new String[count];
           for (int i = 0; i < count && (!arr[i].name.equals("empty")); i++) {
               ans[i] = arr[i].name;
           }
        for (int i = 0; i < count; i++) {
            PrintWriter update = new PrintWriter(arr[i].clientSocket.getOutputStream(), true);
            update.println("<list>" + Arrays.toString(ans));
        }
    }
}
		
class WorkerRunnable implements Runnable {
	//*Variables of WorkerRunnable.*//
    protected String name = "empty";
    protected Socket clientSocket = null;
  //*  protected String textArea = null;*//
    protected Server server;

 //*Constructor of WorkerRunnable.*//
 //*gets ServerS and a client socket.*//
    public WorkerRunnable(Server server, Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    public void run() { //*what the thread(WorkerRunnable) does on run.*//
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
            String msg;
            while ((msg = in.readLine()) != null) {
               // if (msg.equals("hamblushk!!")) { // if the server gets the msg "hamblushk!!" it breaks out of the run method.
               //     break; 
               //}
                if (msg.startsWith("%%%!")) { //getting the name from client and updates every user.
                    name = msg.substring(4);
                    server.updateList();
                    for (int i = 0; i < server.count; i++) {
                        if (!name.equals(server.arr[i].name)) {
                        	PrintWriter outF = new PrintWriter(server.arr[i].clientSocket.getOutputStream(), true);
                        	outF.println(name + " is now connected");
                        }
                    }
                    continue;
                }
                //**Private message to a user.**//
                if (msg.startsWith("<!")) {
                    String take = msg.split("!>")[0].substring(2);
                    for (int i = 0; i < server.count; i++) {
                        if (take.equals(server.arr[i].name)) {
                        	PrintWriter outF = new PrintWriter(server.arr[i].clientSocket.getOutputStream(), true);
                        	outF.println(name + "(Private Message): " + msg.split(">")[1]);
                        }

                    }//**msg also goes to the **//
                } else {
                    for (int i = 0; i < server.count; i++) {
                    	PrintWriter outF = new PrintWriter(server.arr[i].clientSocket.getOutputStream(), true);
                    	outF.println(name + ": " + msg);
                    }
                }
            }
            //**closing everything and informing the other users.**//
            MainServerAction.textArea.append("(" + name + ") left.\n");
            //**remove client from arr**//
            for (int i = 0; i < server.count; i++) {
                if (server.arr[i].name.equals(name)) {
                    while (server.arr[i + 1] != null) {
                        server.arr[i] = server.arr[i + 1];
                        i++;
                    }
                    break;
                }
            }
            server.count--;
            server.updateList(); //** update arr to users.**//
            for (int i = 0; i < server.count; i++) {
                PrintWriter send = new PrintWriter(server.arr[i].clientSocket.getOutputStream(), true);
                send.println(name + " left");
            }

            out.close();
            in.close();
            clientSocket.close();
            //** close done!**//
        } catch (IOException e) {
            System.err.println("Error " + e.getMessage());
        }
    }
}
