package Selector;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class SelectDemo2 {
    @Test//服务端
    public void serverDemo() throws IOException {
        ServerSocketChannel socketChannel =ServerSocketChannel.open();
        socketChannel.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        socketChannel.bind(new InetSocketAddress(8080));
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (selector.select()>0){
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while(iterator.hasNext()){
                //获取已经就绪的操作
                SelectionKey key = iterator.next();
                if (key.isAcceptable()){
                    //获取链接
                    SocketChannel accept = socketChannel.accept();
                    accept.configureBlocking(false);
                    accept.register(selector,SelectionKey.OP_READ);
                }else if (key.isReadable()){
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int length = 0;
                   while ((length = channel.read(byteBuffer))>0){
                       byteBuffer.flip();
                       System.out.println(new String(byteBuffer.array(),0,length));
                       byteBuffer.clear();
                   }
                }
            }
            iterator.remove();
        }
    }

    public static void main(String[] args) throws Exception{//轮询客户端
        SocketChannel socketChannel = SocketChannel
                .open(new InetSocketAddress("127.0.0.1",8080));
        socketChannel.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String str = scanner.next();
            buffer.put((new Date().toString()+"-->"+str).getBytes());
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }

    }






    //客户端
    @Test
    public void clientDemo() throws IOException {
        SocketChannel socketChannel = SocketChannel
                .open(new InetSocketAddress("127.0.0.1",8080));
        socketChannel.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(new Date().toString().getBytes());
        buffer.flip();
        socketChannel.write(buffer);
        buffer.clear();
    }
}
