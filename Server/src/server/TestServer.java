package server;

import client.Tunnel;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TestServer {
    public static void main(String[] args) throws IOException {
        int lPort = Integer.valueOf(args[0]);

        try (ServerSocket s = new ServerSocket(lPort)) {
            System.out.println("Server started");
            try (Socket incoming = s.accept()){
                InputStream is = incoming.getInputStream();
                OutputStream os = incoming.getOutputStream();

                try(Scanner in = new Scanner(is, "UTF-8")){
                    PrintWriter out = new PrintWriter(
                            new OutputStreamWriter(os, "UTF-8"), true);
                    out.println("Hello there!");
                    boolean done = false;
                    while (!done && in.hasNextLine()){
                        String line = in.nextLine();
                        out.println("Echo: " + line);
                    }
                }
            }
        }
    }
}
