package vchat.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 1 创建selector选择器
 2 创建serverSocketChannel通道
 3 为channel通道绑定监听端口  通道设置为非阻塞模式
 4 吧channel注册到选择器上  循环 等待新链接接入
 5 根据就绪状态 调用对应方法 实现具体业务操作
 - 如果accept就绪状态
 - 如果可读状态
 */
//服务器
public class ChatServer {
    public static void main(String[] args) throws Exception {
        new ChatServer().startServer();
    }

    //服务器启动的方法
    public void startServer()throws  Exception{
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8000));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器已经正常启动了");
        while (true){
            int readChannels = selector.select();
            if (readChannels==0){
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                //移除set集合当前key
                iterator.remove();
                if (selectionKey.isAcceptable()){
                    acceptOperator(serverSocketChannel,selector);
                }
                if (selectionKey.isReadable()){
                    readOperator(selector,selectionKey);
                }
            }
        }
    }


    /**
     处理可读状态操作
     从selectionKey获取已经就绪的通道
     创建Buffer
     循环读取客户端消息
     将channel再次注册到选择器上面 监听可读的状态
     把客户端发送的消息 广播到其他客户端
     * @param selector
     * @param selectionKey
     */
    private void readOperator(Selector selector, SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int readLength = socketChannel.read(byteBuffer);
        String message = "";
        if (readLength>0){
            byteBuffer.flip();
            message += Charset.forName("UTF-8").decode(byteBuffer);
        }
        socketChannel.register(selector,SelectionKey.OP_READ);
        if (message.length()>0) {
            //广播给其他客户端
            System.out.println(message);
            castOthorClient(message,selector,socketChannel);
        }
    }


    /**
      广播到其他客户端
     获取所有接入的客户端
     循环向所有的channel广播消息 不需要向自己广播
     * @param message
     * @param selector
     * @param socketChannel
     */
    private void castOthorClient(String message, Selector selector, SocketChannel socketChannel) throws IOException {
        Set<SelectionKey> selectionKeySet = selector.keys();
        for (SelectionKey selectionKey:selectionKeySet){
            //获取里面每个channel
            Channel tarChannel = selectionKey.channel();
            if (tarChannel instanceof SocketChannel && tarChannel!=socketChannel){
                ((SocketChannel) tarChannel).write(Charset.forName("UTF-8").encode(message));
            }
        }
    }


    //处理接入状态操作
    /**
     接入状态 创建socketChannel
     socketChannel设置为非阻塞模式
     把channel注册到selector选择器上面 监听可读状态
     客户端 回复信息
     * @param serverSocketChannel
     * @param selector
     */
    private void acceptOperator(ServerSocketChannel serverSocketChannel, Selector selector) throws IOException {
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector,SelectionKey.OP_READ);
        socketChannel.write(Charset.forName("UTF-8")
                .encode("进入聊天室"));
    }
}
