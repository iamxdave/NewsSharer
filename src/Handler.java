import javax.swing.*;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Locale;

public class Handler {

    private final String name;
    private SocketChannel channel = null;
    private static final Charset charset = Charset.forName("ISO-8859-2");
    private ByteBuffer inBuf = ByteBuffer.allocateDirect(1024);
    private CharBuffer cbuf = null;

    private Window window;

    public Handler(String name, Window window) {
        this.name = name.toUpperCase(Locale.ROOT);
        this.window = window;
    }

    public void connect(String address, int port) {
        InetSocketAddress inet = new InetSocketAddress(address, port);
        try {
            channel = SocketChannel.open();
            channel.configureBlocking(false);

            channel.connect(inet);

            System.out.println(name + ": connecting to the server " + inet.getAddress());

             while (!channel.finishConnect()) {

             }
        } catch (UnknownHostException exc) {
            System.err.println("Unknown host on port" + port);
        } catch (Exception exc) {
            exc.printStackTrace();
        }

        System.out.println(name + ": connected to the server " + inet.getAddress());

    }

    public void read() {

        boolean exit = false;

        while (!exit) {
            inBuf.clear();
            int readBytes = 0;
            try {
                readBytes = channel.read(inBuf);
            } catch (IOException e) {
                exit = true;
            }

            if (readBytes == 0) {
                continue;
            } else if (readBytes == -1) {
                JOptionPane.showMessageDialog(new JFrame(), "Channel to server closed", "Error Message", JOptionPane.ERROR_MESSAGE);
                break;
            } else {
                inBuf.flip();
                cbuf = charset.decode(inBuf);

                String response = cbuf.toString();

                System.out.println(name + ": got message from server - " + response);
                cbuf.clear();

                String[] parts = response.split(",");
                String cmd = parts[0];
                String message = parts[1];

                switch (cmd) {
                    case "err" -> {
                        JOptionPane.showMessageDialog(new JFrame(), message, "Error Message", JOptionPane.ERROR_MESSAGE);
                        exit = true;
                    }
                    case "errA" -> {
                        JOptionPane.showMessageDialog(new JFrame(), message, "Error Message", JOptionPane.ERROR_MESSAGE);
                        window.getInputText().setText("");
                        window.getInputTopic().setText("");
                        exit = true;
                    }
                    case "addack", "updateack", "removeack", "unsubscribeack" -> {
                        JOptionPane.showMessageDialog(new JFrame(), message, "Information Message", JOptionPane.INFORMATION_MESSAGE);
                        exit = true;
                    }
                    case "subscribeack", "refreshack" -> {
                        window.getInputText().setText(message);
                        exit = true;
                    }
                    default -> {
                        JOptionPane.showMessageDialog(new JFrame(), "Unknown command", "Error Message", JOptionPane.ERROR_MESSAGE);
                        exit = true;
                    }
                }


            }
        }
    }

    public void write(String line) {
        cbuf = CharBuffer.wrap(line + "\n");
        try {
            channel.write(charset.encode(cbuf));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Connection to the server has been lost", "Error Message", JOptionPane.ERROR_MESSAGE);
            window.setVisible(false);
            window.dispose();
        }
        System.out.println(name + ": sending " + line);
    }
}
