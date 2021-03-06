import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import tcpclient.TCPClient;

public class MyRunnable implements Runnable {
    Socket connectionSocket;
    final int BUFFER_SIZE = 1024;

    public MyRunnable(Socket connectionSocket){
        this.connectionSocket = connectionSocket;
    }

    public void run(){
        byte [] httpRequest = new byte[BUFFER_SIZE];
        StringBuilder sb = new StringBuilder();
        String hostName = null;
        int serverPort = 0;
        String toServer = null; 
            
        try{    
            int length = 0;
            length = connectionSocket.getInputStream().read(httpRequest);
            String httpReq = new String(httpRequest, 0, length, StandardCharsets.UTF_8);
            
            String split[] = httpReq.split("\r\n");
            String httpGet = split[0];
            String httpGetSplit[] = httpGet.split("[=&' '/]");

            for(int i =0; i < httpGetSplit.length; i++){
                if(httpGetSplit[i].equals("ask?hostname"))
                     hostName = httpGetSplit[i + 1];
                if(httpGetSplit[i].equals("port"))
                    serverPort = Integer.parseInt(httpGetSplit[i + 1]);
                 if(httpGetSplit[i].equals("string"))
                     toServer =URLDecoder.decode(httpGetSplit[i + 1], StandardCharsets.UTF_8) ;
            }
            
            String fromTheServer = null;

            if(hostName != null && serverPort != 0){
                sb.append("HTTP/1.1 200 OK\r\n").append("Content-Type: text/plain \r\n ")
                .append("Connection: close \r\n").append("\r\n");
                if(toServer == null){

                    fromTheServer = TCPClient.askServer(hostName, serverPort);
                    sb.append(fromTheServer); 
                }
                else{

                    fromTheServer = TCPClient.askServer(hostName, serverPort, toServer);
                    sb.append(fromTheServer);
                }
            }
            else
            sb.append("HTTP/1.1 400 Bad Request\r\n");

            byte [] dataFromTheServe =sb.toString().getBytes(StandardCharsets.UTF_8);
            connectionSocket.getOutputStream().write(dataFromTheServe);
   
            connectionSocket.close();
          
        } catch (IOException e) {}
    }



}