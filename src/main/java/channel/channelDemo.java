package channel;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class channelDemo {

    public static void main(String[] args) throws Exception {
        RandomAccessFile aFile = new RandomAccessFile("D:\\李禹麒Java项目\\nio\\1.txt","rw");
        FileChannel channel = aFile.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(1024);
        int read = channel.read(buf);
        while (read!=-1){
            System.out.println(read);
            buf.flip();
            while (buf.hasRemaining()){
                System.out.println((char) buf.get());
            }
            buf.clear();
            read = channel.read(buf);
        }
        aFile.close();
    }
}
