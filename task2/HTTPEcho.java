import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;


public class HTTPEcho {
    public static void main( String[] args) throws IOException {
        final int BUFFER_SIZE = 1024;
        int port = Integer.parseInt(args[0]);
        ServerSocket welcomeSocket = new ServerSocket(port);

        while(true){
            Socket connectionSocket = welcomeSocket.accept();
            byte [] dataFromTheClient = new byte[BUFFER_SIZE];
            StringBuilder sb = new StringBuilder();

            // the header
            sb.append("HTTP/1.1 200 \r\n").append("Content-Type: text/plain \r\n ")
            .append("Connection: close \r\n").append("\r\n");

            int length = connectionSocket.getInputStream().read(dataFromTheClient);

            for(int i = 0; i < length; i++)
                sb.append((char)dataFromTheClient[i]); 
            
            byte [] dataFromTheServe = sb.toString().getBytes(StandardCharsets.UTF_8);
            connectionSocket.getOutputStream().write(dataFromTheServe);
            connectionSocket.close();

        }
    }
}

