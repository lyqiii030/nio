package channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelDemo {
    public static void main(String[] args) throws IOException {
        //创建socketChannel方式一
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("www.baidu.com",80));
        //方式二：
//        SocketChannel socketChannel2 = SocketChannel.open();
//        socketChannel2.connect(new InetSocketAddress("www.baidu.com",80));
        sc.configureBlocking(false);
        //读操作
        ByteBuffer buffer = ByteBuffer.allocate(16);
        sc.read(buffer);
        sc.close();
        System.out.println("read over");



    }
}
