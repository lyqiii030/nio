package asynchrousFile;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannel;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class AsynchrousFileChannelFuturre {
    @Test
    public void test()throws Exception{
        Path path = Paths.get("D:\\李禹麒Java项目\\nio\\1.txt");
        AsynchronousFileChannel fileChannel =
                AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Future<Integer> future = fileChannel.read(buffer, 0);
        while (future.isDone()!=true);
            buffer.flip();
//            while (buffer.remaining()>0){
//                System.out.println(buffer.get());
//            }
        byte[] data = new byte[buffer.limit()];
        buffer.get(data);
        System.out.println(new String(data));
            buffer.clear();
    }
    @Test
    public void test2()throws  Exception{
        Path path = Paths.get("D:\\李禹麒Java项目\\nio\\1.txt");
        AsynchronousFileChannel fileChannel =
                AsynchronousFileChannel.open(path,StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        fileChannel.read(buffer,
                0, buffer,
                new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("result:"+result);
                attachment.flip();
                byte[] data = new byte[attachment.limit()];
                attachment.get(data);
                System.out.println(new String(data));
                attachment.clear();
            }
            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
            }
        });
    }
    @Test
    public void test3()throws Exception{
        Path path = Paths.get("D:\\李禹麒Java项目\\nio\\1.txt");
        AsynchronousFileChannel fileChannel =
                AsynchronousFileChannel.open(path,StandardOpenOption.WRITE);
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.put("uiouio".getBytes());
        buffer.flip();
        Future<Integer> future = fileChannel.write(buffer, 0);//position从哪个位置开始写入
        while (future.isDone()!=true);
        buffer.clear();
        System.out.println("write over");
    }
    @Test
    public void test4()throws  Exception{
        Path path = Paths.get("D:\\李禹麒Java项目\\nio\\1.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path,StandardOpenOption.WRITE);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("lyq".getBytes());
        buffer.flip();
        fileChannel.write(buffer,
                0, buffer,
                new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("bytes written:"+result);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println("write failed");
            }
        });
    }
}
