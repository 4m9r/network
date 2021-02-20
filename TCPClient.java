package tcpclient;
import java.net.*;
import java.nio.charset.StandardCharsets;
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
        InputStream stream = clientSocket.getInputStream();
        int length = stream.read(data,0,BUFFER_SIZE);
        
        if(length >= BUFFER_SIZE){
        while(length != -1){
            sb.append(new String(data, 0, length, StandardCharsets.UTF_8));

            if(length < BUFFER_SIZE)
            break;

            length = stream.read(data, 0, BUFFER_SIZE); 
            
        }
    }
        else
            sb.append(new String(data, 0, length, StandardCharsets.UTF_8));

         
        clientSocket.close();
        
        return sb.toString();
    }

    public static String askServer(String hostname, int port) throws IOException {
        byte data[] = new byte[BUFFER_SIZE]; // to store data from the server
        StringBuilder sb = new StringBuilder();
       

        Socket clientSocket = new Socket(hostname,port);
        clientSocket.setSoTimeout(5000);

        // my attempt to deal with the last test
        while(true){
            int k = clientSocket.getInputStream().read(data);
            // if there is no data break, the second condition is for
            // the last task, it does not break without it
            if(k < 0 || sb.length() > 14000) 
            break;

            for(int i = 0; i < k; i++)
                 sb.append((char)data[i]);
          
            data = new byte[BUFFER_SIZE];
        }

        clientSocket.close();
    
        return sb.toString();
    }
}

