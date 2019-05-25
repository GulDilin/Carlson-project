package server;

import CarlsonProject.WindowsArrayList;
import CarlsonProject.commands.Command;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ResponseThread extends Thread {

    private final InetSocketAddress clientAddress;
    private WindowsArrayList windowsArrayList;
    private ByteBuffer buffer;
    private DatagramChannel channel;
    private Command command;

    public ResponseThread(DatagramChannel channel,
                          InetSocketAddress clientAddress,
                          ByteBuffer buffer,
                          WindowsArrayList windowsArrayList ){
        super();
        this.windowsArrayList = windowsArrayList;
        this.channel = channel;
        this.clientAddress = clientAddress;
        this.buffer = buffer;
    }

    public void run() {
        byte[] request = buffer.array();

        try (ByteArrayInputStream bais = new ByteArrayInputStream(request);
             ObjectInputStream ois = new ObjectInputStream(bais);

             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos);

             ByteArrayOutputStream bao = new ByteArrayOutputStream();
             PrintStream printStream = new PrintStream(bao)) {

            command = (Command) ois.readObject();
            String response = "";

                System.out.println("Client command: " + command.toString());
                printStream.println();
                command.setOut(printStream);
                command.execute(windowsArrayList);
                printStream.println();

                response = bao.toString().trim();
                System.out.println(response);

            oos.writeObject(response);
            oos.flush();

            buffer.clear();
            buffer.put(baos.toByteArray());
            buffer.flip();

            channel.send(buffer, clientAddress);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
