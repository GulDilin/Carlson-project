package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TestServer {
    public static void main(String[] args) throws IOException {
        int lPort = Integer.parseInt(args[0]);

        try (ServerSocket s = new ServerSocket(lPort)) {
            System.out.println("Server started");
            try (Socket incoming = s.accept()){
                InputStream is = incoming.getInputStream();
                OutputStream os = incoming.getOutputStream();

                try(Scanner in = new Scanner(is, "UTF-8")){
                    PrintWriter out = new PrintWriter(
                            new OutputStreamWriter(os, StandardCharsets.UTF_8), true);
                    out.println("Hello there!");
                    while (in.hasNextLine()){
                        String line = in.nextLine();
                        out.println("Echo: " + line);
                    }
                }
            }
        }
    }
}
