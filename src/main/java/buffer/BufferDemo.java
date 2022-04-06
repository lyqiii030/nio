package buffer;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

public class BufferDemo {
    @Test
    public void testBuffer() throws IOException {
        RandomAccessFile aFile =
                new RandomAccessFile("D:\\李禹麒Java项目\\nio\\1.txt","rw");
        FileChannel channel1 = aFile.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);//size
        int byteRead = channel1.read(buffer);
        while(byteRead!=-1){
            buffer.flip();
            while (buffer.hasRemaining()){
                System.out.println((char)buffer.get());
            }
            buffer.clear();
            byteRead = channel1.read(buffer);
        }
        System.out.println(channel1.size());
        aFile.close();

    }
    @Test
    public void testIntBuffer() throws Exception{
        IntBuffer buffer = IntBuffer.allocate(8);
        for (int i = 0; i < buffer.capacity(); i++) {
            int j =2*(i+1);
            buffer.put(j);
        }
        buffer.flip();//重置缓冲区
        while(buffer.hasRemaining()){
            int value = buffer.get();
            System.out.println(value+" ");
        }
    }
}
