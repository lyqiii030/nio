package vchat.client;

//客户端

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

/**

 */
public class ChatClient {
    public static void main(String[] args) throws IOException {
//        new ChatClient().startClient();
    }


    /**
     连接服务器
     向服务器发送消息
     接收服务器端响应数据
     */
    public void startClient(String name) throws IOException {
        SocketChannel socketChannel =
                SocketChannel.open(new InetSocketAddress("127.0.0.1",8000));
        Selector selector = Selector.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        //创建线程
        new Thread(new ClientThread(selector)).start();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String msg = scanner.nextLine();
            if (msg.length()>0){
                socketChannel.write(Charset.forName("UTF-8").encode(name+":"+msg));
            }

        }
    }
}
