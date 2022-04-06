package Selector;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 消息边界问题：
    问题： 假如一个buffer的allocate大小为4 但是输入两个中文 也就是六个字节 那么第一个字将会是完整的
 第二个字则是乱码的问题
 解决方案：
  1 固定消息长度 数据包大小一样 服务器按预定长度读取 缺点是浪费带宽
  2 按分隔符拆分 缺点是 效率低
  3 TLV格式 分别表示Type Length Value 类型长度已知的情况下 就可以方便获取消息大小 分配合适的buffer
 缺点是buffer需要提前分配 如果内容大 则影响server吞吐量
 */

public class AnalysisStepsDemo {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        SelectionKey sscKey = ssc.register(selector, 0, null);
        /**
         处理事件 selectorKey包含了所有事件
         */
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));
        while (true){
            /**
             select方法 没有事件发生 线程阻塞 有事件才会恢复运行
               select在事件未处理时 他不会阻塞 事件发生后要么处理要么取消 不然会当作一直标记为未处理则一直循环
             */
            selector.select();
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            /**
             处理key时 要从selectorKey删除 否则再次调用会出问题
             */
            iter.remove();
            while (iter.hasNext()){
                SelectionKey key = iter.next();
                if (key.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    SelectionKey scKey = sc.register(selector,0,null);
                    /**
                     selector发生事件之后 向selectKeys集合加入key 但是不会删除 需要使用remove()删除
                     */
                    scKey.interestOps(SelectionKey.OP_READ);
                }else if (key.isReadable()){
                    try {
                        SocketChannel channel = (SocketChannel) key.channel();//拿到触发事件的channel
                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        int read = channel.read(buffer);
                        /**
                         如果是正常断开 read的返回值时-1
                            正常断开会触发一次读事件
                         */
                        if (read==-1){
                            key.cancel();
                        }else {
                          split(buffer);
                          if (buffer.limit()==buffer.position()){
                              ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
                              buffer.flip();
                              newBuffer.put(buffer);//把刚刚那个buffer的16字节写入进去了
                              key.attach(newBuffer);//替换旧的buffer
                              /**
                               ByteBuffer戴奥分配：因为ByteBuffer不可以被多个channel共同使用因此需要为每个channel维护一个独立的ByteBuffer
                                    1 先分配一个较小的buffer 如果不够在扩容到两倍 然后将原来的拷贝至扩容后的buffer 缺点是拷贝耗性能
                                    2 用多个数组组成的buffer 一个不够 把多个内容写入新的数组 与之前的区别的是消息存储不连续解析复杂 但可以避免性能损耗
                               */
                          }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        key.cancel();
                        /**
                         因为客户端断开了 因此需要将key取消  从selectorKey集合中真正删除
                         */
                    }
                }
            }
        }

    }
    private static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            if (source.get(i) == '\n') {
                int length = i+1-source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                System.out.println(target);
            }
        }
    }
}
