package tcpclient;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

public class TCPClient {
    private static int BUFFER_SIZE = 12000;
    
    public static String askServer(String hostname, int port, String ToServer) throws IOException {
        byte data[] = new byte[BUFFER_SIZE]; // to store data from the server
        byte userInput [] = ToServer.getBytes(StandardCharsets.UTF_8);

        Socket clientSocket = new Socket(hostname,port);
        clientSocket.setSoTimeout(5000);

        clientSocket.getOutputStream().write(userInput); // send byte to the socket
        int fromServerLength = clientSocket.getInputStream().read(data); // recive data from the socket 
        
        
        String s = new String(data,0,fromServerLength,StandardCharsets.UTF_8);
        clientSocket.close();
        System.out.println(s.length());
        
        return s;
    }

    public static String askServer(String hostname, int port) throws IOException {
        byte data[] = new byte[BUFFER_SIZE]; // to store data from the server

        Socket clientSocket = new Socket(hostname,port);
        clientSocket.setSoTimeout(5000);
        
        int fromServerLength = clientSocket.getInputStream().read(data); // recive data from the socket 
        
        System.out.println(fromServerLength);

        String s = new String(data, 0, fromServerLength, StandardCharsets.UTF_8);
        clientSocket.close();
        
        return s;
    }
}

