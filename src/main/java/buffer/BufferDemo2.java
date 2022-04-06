package buffer;

import org.junit.Test;

import java.io.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class BufferDemo2 {
    static private final int start = 0;
    static private final int size = 1024;


    @Test//缓存区分片
    public  void test(){
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte)i);
        }
        //创建子缓存区
        buffer.position(3);
        buffer.limit(7);
        ByteBuffer slice = buffer.slice();
        //改变子缓冲区内容
        for (int i = 0; i < slice.capacity(); i++) {
            byte b = slice.get(i);
            b*=10;
            slice.put(i,b);
        }
        buffer.position(0);
        buffer.limit(buffer.capacity());
        while(buffer.remaining()>0){
            System.out.println(buffer.get());
        }

    }
    @Test//制度缓存区
    public void test02(){
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte)i);
        }
        //创建只读缓存区
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        for (int i = 0; i < buffer.capacity(); i++) {
            byte b = buffer.get(i);
            b*=10;
            buffer.put(i,b);
        }
        readOnlyBuffer.position(0);
        readOnlyBuffer.limit(buffer.capacity());
        while(readOnlyBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.get());
        }
    }
    @Test//直接缓冲区
    public void test3() throws Exception {
        String infile ="D:\\李禹麒Java项目\\nio\\1.txt";
        FileInputStream fin = new FileInputStream(infile);
        FileChannel finChannel = fin.getChannel();
        String outFile = "D:\\李禹麒Java项目\\nio\\5.txt";
        FileOutputStream fout = new FileOutputStream(outFile);
        FileChannel foutChannel = fout.getChannel();
        //创建直接缓冲区
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        while(true){
            buffer.clear();
            int r = finChannel.read(buffer);
            if (r==-1){
                break;
            }else {
                buffer.flip();
                foutChannel.write(buffer);
            }
        }
    }
    @Test//内存映射文件io操作
    public void test4 () throws Exception {
        RandomAccessFile raf = new RandomAccessFile("D:\\李禹麒Java项目\\nio\\5.txt","rw");
        FileChannel fc = raf.getChannel();
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE,start,size);
        mbb.put(0,(byte) 97);
        mbb.put(1023,(byte) 122);
        raf.close();
    }




}
