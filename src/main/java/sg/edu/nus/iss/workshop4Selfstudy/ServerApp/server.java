package sg.edu.nus.iss.workshop4Selfstudy.ServerApp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class server 
{
    public static void main( String[] args )
    {
        System.out.println( "ServerApp.main()" );
        Socket socket = null;
        InputStream is = null;
        OutputStream os = null;
        ServerSocket serverSocket = null;
        try {
            String serverPort = args[0];
            String cookieFile = args[1];
            System.out.println("" + serverPort + " " + cookieFile);

            //Create a server
            serverSocket = new ServerSocket(Integer.parseInt(serverPort));
            System.out.println("Cookie server started on " + serverPort);
            while (true) {
                System.out.println("Waiting for client...");
                //accept connection
                socket = serverSocket.accept();
                System.out.println("New client connected");
                ///get data client program as input in bytes
                is = socket.getInputStream();
                DataInputStream dis = new DataInputStream(is);
                
                //to send data back to the client
                os = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
                
                //Echoes back whatever the client sends
                while (true) {
                    System.out.println("Received command from client");
                    try {
                        String dataFromClient = dis.readUTF(); //readUTF method is used for reading strings that were written using writeUTF by the client, 
                        //ensuring that the data is correctly interpreted as a string.
                        
                        if (dataFromClient.equals("get-cookie")){
                            String cookieName = cookie.getRandomCookie(cookieFile); 
                            dos.writeUTF("cookie-text_" + cookieName); //server then sends back this cookie text to the client
                        } if (dataFromClient.equals("close")) { 
                            System.out.println("Client request to close connection");
                            dos.writeUTF("Goodbye");
                            socket.close();
                            break; // end the communication session
                        } else { //If the client sends any other string besides "get-cookie" or "close"
                            dos.writeUTF("Invalid command"); 
                        } 
                        dos.flush(); //it forces any buffered output bytes to be written out to the stream. 
                        //This ensures that the client receives the response immediately after the server sends it, 
                        //without waiting for more data to fill the buffer.

                    } catch (EOFException e) {
                        System.out.println("Client disconnected");
                        socket.close();
                        break;
                    }
                }
            } 
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        
    }
}
