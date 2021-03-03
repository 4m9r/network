package tcpclient;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
import java.io.*;

public class TCPClient {
    private static int BUFFER_SIZE = 1024;
    
    public static String askServer(String hostname, int port, String ToServer) throws IOException {
        byte data[] = new byte[BUFFER_SIZE]; // to store data from the server
        byte userInput [] = ToServer.getBytes(StandardCharsets.UTF_8);
        
        

        Socket clientSocket = new Socket(hostname, port);
        clientSocket.setSoTimeout(5000);
        

        clientSocket.getOutputStream().write(userInput);

        StringBuilder sb = new StringBuilder();
        int length = -1;
        try{
            while((length = clientSocket.getInputStream().read(data, 0, BUFFER_SIZE)) != -1 && sb.length() < 14000)
            sb.append(new String(data, 0, length, StandardCharsets.UTF_8));
            
    }
    catch(SocketTimeoutException e) {}
         
        clientSocket.close();
        
        return sb.toString();
    }

    public static String askServer(String hostname, int port) throws IOException {
        byte data[] = new byte[BUFFER_SIZE]; // to store data from the server
        StringBuilder sb = new StringBuilder();
       

        Socket clientSocket = new Socket(hostname,port);
        clientSocket.setSoTimeout(5000);

        try{
            while(true){
                int length = clientSocket.getInputStream().read(data);
                 // if there is no data break, the second condition is for
                // the last task, it does not break without it
                if(length < 0 || sb.length() > 14000) 
                    break;

                sb.append(new String(data, 0, length, StandardCharsets.UTF_8));
          
            }
        }
        catch (SocketTimeoutException e){}

        clientSocket.close();
    
        return sb.toString();
    }
}

