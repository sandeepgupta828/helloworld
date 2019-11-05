package com.programs.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Objects;

public class SocketIO {

    public static class Server {
        ServerSocket serverSocket;
        Socket clientSocket;
        PrintWriter out;
        private BufferedReader in;

        public void startEchoServer(int port) throws IOException {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String greeting = in.readLine();
            System.out.println("server:"+ greeting);
            if ("hello server".equalsIgnoreCase(greeting)) {
                out.println("hello client");
            } else {
                out.println("unrecognised greeting");
            }
        }

        public void stopServer() throws IOException {
            if (Objects.nonNull(in)) in.close();
            if (Objects.nonNull(out)) out.close();
            if (Objects.nonNull(clientSocket)) clientSocket.close();
            if (Objects.nonNull(serverSocket)) serverSocket.close();
        }
    }

    public static class Client {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public void startConnection(String ip, int port) throws IOException {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }

        public String sendMessage(String msg) throws IOException {
            out.println(msg);
            String resp = in.readLine();
            return resp;
        }

        public void stopConnection() throws IOException {
            if (Objects.nonNull(in)) in.close();
            if (Objects.nonNull(out)) out.close();
            if (Objects.nonNull(clientSocket)) clientSocket.close();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final Server server = new Server();
        Client client = null;
        try {
            new Thread(() -> {
                try {
                    server.startEchoServer(114);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            Thread.sleep(1000);
            client = new Client();
            client.startConnection("127.0.0.1", 114);
            System.out.println("client:"+client.sendMessage("Hello Server"));
        } finally {
            server.stopServer();
            client.stopConnection();
        }
    }

}
