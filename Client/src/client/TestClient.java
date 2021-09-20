package client;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TestClient {
    public static void main(String[] args) throws IOException {
        try (Socket s = new Socket()) {
            int lPort = Integer.parseInt(args[0]);
            Tunnel tunnel = new Tunnel("helios.se.ifmo.ru",
                    "s264449",
                    "cfv571",
                    2222,
                    "localhost",
                    lPort,
                    lPort);
            tunnel.connect();
            s.connect(new InetSocketAddress("localhost",lPort), 500);
            System.out.println("Connect server");
            try (OutputStream os = s.getOutputStream();
                 InputStream is = s.getInputStream();
                 Scanner in = new Scanner(System.in, "UTF-8");
                 Scanner ins = new Scanner(is, "UTF-8");
                 PrintWriter out = new PrintWriter(
                         new OutputStreamWriter(os, StandardCharsets.UTF_8), true)) {
                while (ins.hasNextLine()) {
                    System.out.println(ins.nextLine());
                    if (in.hasNextLine())
                        out.println(in.nextLine());
                }
            }
        } catch (InterruptedIOException ex) {
            System.out.println("End Timeout");
        }
    }
}
