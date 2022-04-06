package channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerChannelDemo {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 8808;
        ByteBuffer buffer = ByteBuffer.wrap("hello world".getBytes());
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(port));
        ssc.configureBlocking(false);
        while (true){
            System.out.println("端口监听....");
            SocketChannel sc = ssc.accept();
            if (sc == null) {
                System.out.println("null");
                Thread.sleep(2000);
            }else {
                System.out.println("捕获连接；"+sc.socket().getRemoteSocketAddress());
                buffer.rewind();
                sc.write(buffer);
                sc.close();
            }

        }


    }
}
