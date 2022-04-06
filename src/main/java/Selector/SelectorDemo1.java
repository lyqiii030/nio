package Selector;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SelectorDemo1 {
    public static void main(String[] args) throws Exception{
        Selector selector = Selector.open();
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.configureBlocking(false);
        //绑定连接
        socketChannel.bind(new InetSocketAddress(9999));
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //查询已经就绪通道操作
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while(iterator.hasNext()){
            SelectionKey key = iterator.next();
            //判断key就绪状态操作
            if (key.isAcceptable()){

            }else if(key.isConnectable()){

            }else if (key.isReadable()){

            }else if (key.isWritable()){

            }
            iterator.remove();
        }

    }
}
