package BlockDemo;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) throws Exception{
        ByteBuffer buffer = ByteBuffer.allocate(16);
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(8080));
        List<SocketChannel> channels = new ArrayList();
        while (true){
            SocketChannel sc = ssc.accept();//建立与客户端连接 channel连接通道
            if (sc!=null){
                sc.configureBlocking(false);
                channels.add(sc);
            }
            for (SocketChannel channel : channels){
                int read = channel.read(buffer);
               if (read>0){
                   buffer.flip();
                   System.out.println(buffer.get());
                   buffer.clear();
               }
            }
        }
    }
}
