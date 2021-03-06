import java.net.*;
import java.io.*;



public class CouncHTTPAsk {
    public static void main( String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        ServerSocket welcomeSocket = new ServerSocket(port);

        while(true){
            Socket connectionSocket = welcomeSocket.accept();
            Runnable rn = new MyRunnable(connectionSocket);
            new Thread(rn).start();    
        }
             
    }
}

