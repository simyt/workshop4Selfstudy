package sg.edu.nus.iss.workshop4Selfstudy.ClientApp;

import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class client {
    public static void main (String[] args) {
        //System.out.println("ClientApp.main()");
        String[] connInfo = args[0].split(":");
        System.out.println(connInfo[0] + " " + connInfo[1]);

        try {
            while (true) {
                Socket sock = new Socket(connInfo[0], Integer.parseInt(connInfo[1]));
                InputStream is = sock.getInputStream();
                DataInputStream dis = new DataInputStream(is);

                OutputStream os = sock.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
                
                Console cons = System.console();

                String input = cons.readLine("Send client command to the server: ");
                dos.writeUTF(input);
                dos. flush(); //forces any buffered output bytes to be written out immediately, ensuring that the server receives the command without delay.
                
                String response = dis.readUTF();
                if (response.contains("cookie-text_")) {
                    String[] arrRes = response.split("_");
                    System.out.println("Cookie from server: " + arrRes[1]);
                } else {
                    System.out.println(response);
                }
                is.close();
                os.close();
                sock.close();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
