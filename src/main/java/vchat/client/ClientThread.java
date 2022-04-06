package vchat.client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class ClientThread implements Runnable{
    private Selector selector;
    public ClientThread(Selector selector){
        this.selector=selector;
    }
    @Override
    public void run() {
        try{
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
                    if (selectionKey.isReadable()){
                        readOperator(selector,selectionKey);
                    }
                }
            }
        }catch (Exception e){

        }
    }

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
            System.out.println(message);
        }
    }
}
