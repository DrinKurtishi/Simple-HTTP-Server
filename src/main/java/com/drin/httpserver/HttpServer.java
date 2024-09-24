package com.drin.httpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.drin.httpserver.config.Configuration;

import com.drin.httpserver.config.ConfigurationManager;

public class HttpServer {

    public static void main(String[] args) {
        System.out.println("Server Starting...");
        
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        System.out.println("Using port: " + conf.getPort());
        System.out.println("Using webroot: " + conf.getWebroot());

        try {
            ServerSocket serverSocket = new ServerSocket(conf.getPort());
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            
            //writing
            String html = "<html><head><title>Simple java http server</title></head><body><h1>This page was served using my own server</h1></body></html>";

            final String CRLF = "\n\r"; //13, 10

            String response = 
                              "HTTP/1.1 200 OK" + CRLF +
                              "Content-length: " + html.getBytes().length + CRLF + 
                              CRLF +
                              html +
                              CRLF + CRLF;

            outputStream.write(response.getBytes());

            inputStream.close();
            outputStream.close();
            serverSocket.close();
            socket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
