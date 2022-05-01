
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.*;


public class Server {

    private final String name;
    private final String address;
    private final int port;

    private static final Charset charset = Charset.forName("ISO-8859-2");
    private ByteBuffer inBuf = ByteBuffer.allocate(1024);
    private StringBuffer sb = new StringBuffer();

    private Map<String, List<SocketChannel>> topics = new HashMap<>();
    private Map<String, String> news = new HashMap<>();


    public static void main(String[] args) {
        new Server("Server", "localhost", 6660);
    }

    public Server(String name, String address, int port) {
        this.name = name.toUpperCase(Locale.ROOT);
        this.address = address;
        this.port = port;

        try {
            setServer();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setServer() throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.socket().bind(new InetSocketAddress(address, port));

        serverChannel.configureBlocking(false);

        Selector selector = Selector.open();

        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println(name + ": waiting ... ");


        while (true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();

            Iterator<SelectionKey> iter = keys.iterator();

            while (iter.hasNext()) {
                SelectionKey key = iter.next();

                iter.remove();

                if (key.isAcceptable()) {

                    SocketChannel cc = serverChannel.accept();

                    System.out.println("Server: got connection from " + cc.getRemoteAddress());

                    cc.configureBlocking(false);

                    cc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

                    continue;
                }

                if (key.isReadable()) {  // któryś z kanałów gotowy do czytania

                    // Uzyskanie kanału na którym czekają dane do odczytania
                    SocketChannel cc = (SocketChannel) key.channel();

                    handleMessage(cc);

                    // obsługa zleceń klienta
                    // ...
                    continue;
                }
                if (key.isWritable()) {  // któryś z kanałów gotowy do pisania

                    // Uzyskanie kanału
                    //SocketChannel cc = (SocketChannel) key.channel();

                    // pisanie do kanału
                    // ...
                    continue;
                }

            }
        }
    }


    private void handleMessage(SocketChannel sc) throws IOException {
        if (!sc.isOpen())
            return;

        System.out.println(name + ": reading from " + sc.getLocalAddress());

        sb.setLength(0);
        inBuf.clear();

        try {
            readLoop:
            while (true) {
                int n = sc.read(inBuf);
                if (n > 0) {
                    inBuf.flip();
                    CharBuffer cbuf = charset.decode(inBuf);
                    while (cbuf.hasRemaining()) {
                        char c = cbuf.get();
                        if (c == '\r' || c == '\n') break readLoop;
                        else {
                            sb.append(c);
                        }
                    }
                }
            }

            String response = sb.toString();
            System.out.println(name + ": got message - " + sb);

            String[] parts = response.split(",");

            String cmd = parts[0];
            String topic = parts[1];
            String message = parts.length == 2 ? "" : parts[2];


            switch (cmd) {
                case "add" -> {
                    if (topics.containsKey(topic)) {
                        sendMessage(sc, "err,Topic is already on the server");
                    } else {
                        topics.put(topic, new ArrayList<>());
                        news.put(topic, message);

                        sendMessage(sc, "addack,Successfully loaded topic on the server");
                    }
                }
                case "update" -> {
                    if (topics.containsKey(topic)) {
                        if (news.containsKey(topic)) {
                            news.put(topic, news.get(topic) + message);
                        }
                        sendMessage(sc, "updateack,Updated topic on the server");
                    } else {
                        sendMessage(sc, "err,Topic isn't on the server");
                    }
                }
                case "remove" -> {
                    if (topics.containsKey(topic)) {
                        news.remove(topic);
                        for (SocketChannel t : topics.get(topic)) {
                            try {
                                sendMessage(t, "errA,Admin removed topic from the list");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        topics.remove(topic);
                        sendMessage(sc, "removeack,Removed topic from the server");
                    } else {
                        sendMessage(sc, "err,The topic is not on the server");
                    }
                }
                case "subscribe" -> {
                    if(topics.containsKey(topic)) {
                        if (topics.get(topic).contains(sc)) {
                            sendMessage(sc, "err,Unsubscribe topic in order to subscribe to another");
                        } else if (topics.containsKey(topic)) {
                            topics.get(topic).add(sc);

                            sendMessage(sc, "subscribeack," + news.get(topic));
                        }
                    } else {
                        sendMessage(sc, "err,Entered topic doesn't exist");
                    }
                }
                case "refresh" -> {
                    if(topics.containsKey(topic)) {
                        if (topics.get(topic).contains(sc)) {
                            sendMessage(sc, "refreshack," + news.get(topic));
                        }
                    } else {
                        sendMessage(sc, "err,Subscribe topic first to refresh topic's content");
                    }

                }
                case "unsubscribe" -> {
                    topics.get(topic).remove(sc);
                    sendMessage(sc, "unsubscribeack,Unsubscribed to the topic " + topic);
                }
            }

        } catch (Exception exc) {
            exc.printStackTrace();

            sc.close();
            sc.socket().close();

        }

    }

    public void sendMessage(SocketChannel sc, String message) throws IOException {
        sc.write(charset.encode(CharBuffer.wrap(message)));
        System.out.println(name + ": sending " + message);
    }

}
